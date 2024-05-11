package com.wyd.redisapp.demos.web.cachetest.v3.extend.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.wyd.redisapp.demos.utils.AsyncUtil;
import com.wyd.redisapp.demos.web.cachetest.v3.extend.SynchronizeCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wyd.redisapp.demos.config.RedisClient.getKeyPrefix;

/**
 * @program: redis-app
 * @author: Stone
 * @create: 2024-05-10 14:44
 */
@Slf4j
@Component
public class RedisSynchronizeCache extends SynchronizeCache {

    // RedisClient 中没有 Stream 相关操作，此处使用 StringRedisTemplate 进行操作，为了保持 key 前缀的一致性，要从 RedisClient 中获取 KeyPrefix。
    private final String queueName = getKeyPrefix() + "double_cache_syn";

    private final StringRedisTemplate redisTemplate;

    private final Cache<String,Object> cache;

    public RedisSynchronizeCache(StringRedisTemplate redisTemplate, Cache<String,Object> cache) {
        this.cache = cache;
        this.redisTemplate = redisTemplate;
        // 存在则创建，否则不操作
        if (Boolean.FALSE.equals(redisTemplate.hasKey(queueName))) {
            redisTemplate.opsForStream().createGroup(queueName, ReadOffset.from("0") ,localIp);
        } else {
            // 是否已存在消费者组，存在则不创建，否则创建
            Set<String> groupNames = redisTemplate.opsForStream().groups(queueName)
                    .stream().map(StreamInfo.XInfoGroup::groupName).collect(Collectors.toSet());
            if (!groupNames.contains(localIp)) {
                redisTemplate.opsForStream().createGroup(queueName, ReadOffset.from("0") ,localIp);
            }
        }
    }

    @Override
    public void produceMessage(String localCacheId) {
        HashMap<String, String> map = new HashMap<>();
        map.put(localCacheId, localIp);
        redisTemplate.opsForStream().add(queueName, map);
    }

    @Override
    public void comsumeMessage() {
        while (true) {
            try {
                // 读消息
                List<MapRecord<String, Object, Object>> list = redisTemplate.opsForStream().read(
                        Consumer.from(localIp, "c1"),
                        // 阻塞等待，没有消息就一直阻塞着，减少资源的浪费
                        StreamReadOptions.empty().count(1).block(Duration.ofSeconds(0)),
                        StreamOffset.create(queueName, ReadOffset.lastConsumed())
                );
                // 判断是否有消息
                if (CollectionUtil.isEmpty(list)) {
                    continue;
                }
                // 解析消息
                MapRecord<String, Object, Object> record = list.get(0);
                Map.Entry<Object, Object> entry = record.getValue().entrySet().iterator().next();
                String ip = (String) entry.getValue();
                if (!localIp.equals(ip)) {
                    log.info("ip为{}的节点删除了本地key为 {} 对应的缓存", ip, entry.getKey());
                    cache.invalidate(entry.getKey());
                }
                // 确认消费消息
                redisTemplate.opsForStream().acknowledge(queueName, localIp, record.getId());
            } catch (Exception e) {
                log.error("redis消息消费异常，{}", e.getMessage());
                // 2024/5/10 处理 PendingList中的消息
                handlerPendingList();
            }
        }
    }

    private void handlerPendingList() {
        while (true) {
            try {
                // 获取pendingList中没有被消费的消息
                List<MapRecord<String, Object, Object>> records = redisTemplate.opsForStream().read(
                        Consumer.from(localIp, "c1"),
                        StreamReadOptions.empty().count(1),
                        StreamOffset.create(queueName, ReadOffset.from("0"))
                );
                if (CollectionUtil.isEmpty(records)) {
                    break;
                }
                // 消费消息
                MapRecord<String, Object, Object> record = records.get(0);
                Map.Entry<Object, Object> entry = record.getValue().entrySet().iterator().next();
                if (!localIp.equals(entry.getValue())) {
                    cache.invalidate(entry.getKey());
                }
                // 确认消息
                redisTemplate.opsForStream().acknowledge(queueName, localIp, record.getId());
            } catch (Exception e) {
                log.error(queueName + "队列中的本地缓存处理异常", e);
                AsyncUtil.sleep(20);
            }
        }
    }


}