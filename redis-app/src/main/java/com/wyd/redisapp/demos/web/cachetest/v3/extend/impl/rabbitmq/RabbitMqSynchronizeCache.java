package com.wyd.redisapp.demos.web.cachetest.v3.extend.impl.rabbitmq;

import com.github.benmanes.caffeine.cache.Cache;
import com.wyd.redisapp.demos.web.cachetest.v3.extend.SynchronizeCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @program: redis-app
 * @author: Stone
 * @create: 2024-05-11 21:55
 */
// TODO: 2024/5/11 注意不同启动时 Spring容器中应该只有一种子类在使用，所以要进行条件选择
@Slf4j
@Component
@ConditionalOnProperty(name = "double.cache.type", havingValue = "rabbitmq")
public class RabbitMqSynchronizeCache extends SynchronizeCache {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Cache<String,Object> cache;

    @Override
    public void produceMessage(String localCacheId) {
        CacheMessage message = new CacheMessage();
        message.setLocalIp(localIp);
        message.setLocalCacheId(localCacheId);
        rabbitTemplate.convertAndSend("double_cache.fanout", "", message);
    }

    @Override
    protected void comsumeMessage() {}

/*    @RabbitListener(queues = "#{T(com.wyd.redisapp.demos.web.cachetest.v3.extend.SynchronizeCache).localIp}")
    public void listenDirectQueue2(String msg){
        log.info("消费者接收到消息：{}", msg);
    }*/

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "#{T(com.wyd.redisapp.demos.web.cachetest.v3.extend.SynchronizeCache).localIp}"),
            exchange = @Exchange(name = "double_cache.fanout", type = ExchangeTypes.FANOUT)))
    public void listenDirectQueue3(CacheMessage message) {
        log.info("消费者接收到消息：{}", message);
        if (!localIp.equals(message.getLocalIp())) {
            cache.invalidate(message.getLocalCacheId());
        }
    }
}