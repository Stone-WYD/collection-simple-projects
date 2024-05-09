package com.wyd.redisapp.demos.web.cachetest.v2;

import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.wyd.redisapp.demos.config.RedisClient;
import com.wyd.redisapp.demos.utils.AsyncUtil;
import com.wyd.redisapp.demos.utils.JsonUtil;
import com.wyd.redisapp.demos.web.User;
import com.wyd.redisapp.demos.web.cachetest.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: redis-app
 * @author: Stone
 * @create: 2024-05-09 11:14
 */
@Slf4j
@RequestMapping("/testCacheV2")
@RestController
public class TwoCacheTestControllerV2 {

    Map<String, User> map = new ConcurrentHashMap<>();

    {
        User user = new User();
        user.setId("1");
        user.setAge(11);
        user.setName("小王");
        map.put("1", user);
    }

    @Cacheable(value = "user", cacheManager = "caffeineCacheManager", key = "#id")
    @RequestMapping("/getUserById/{id}")
    public User getUserById(@PathVariable("id") String id) {
        String key = CacheConstant.USER_V2 + id;
        String userFromRedisStr = RedisClient.getStr(key);
        if (StrUtil.isNotBlank(userFromRedisStr)) {
            User user = JsonUtil.toObj(userFromRedisStr, User.class);
            log.info("get data from redis");
            return user;
        }
        // Redis没有则查询 DB
        log.info("get data from database");
        // 模拟从数据库获取数据需要1s
        AsyncUtil.sleep(1, TimeUnit.SECONDS);
        User userFormDB = map.get(id);
        RedisClient.setStrWithExpire(key, JsonUtil.toStr(userFormDB), 120000L);
        return userFormDB;
    }

    @CachePut(value = "user", cacheManager = "caffeineCacheManager", key = "#user.id")
    @PostMapping("/updateById")
    public User updateUser(@RequestBody User user) {
        log.info("update user data");
        String key=CacheConstant.USER_V2 + user.getId();
        AsyncUtil.sleep(1, TimeUnit.SECONDS);
        map.put(user.getId(), user);
        //修改 Redis
        RedisClient.setStrWithExpire(key, JsonUtil.toStr(user), 120000L);
        return user;
    }
}