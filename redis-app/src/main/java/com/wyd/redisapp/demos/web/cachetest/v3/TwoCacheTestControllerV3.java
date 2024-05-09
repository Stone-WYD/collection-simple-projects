package com.wyd.redisapp.demos.web.cachetest.v3;

import cn.hutool.core.util.StrUtil;
import com.wyd.redisapp.demos.config.RedisClient;
import com.wyd.redisapp.demos.utils.AsyncUtil;
import com.wyd.redisapp.demos.utils.JsonUtil;
import com.wyd.redisapp.demos.web.User;
import com.wyd.redisapp.demos.web.cachetest.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: redis-app
 * @author: Stone
 * @create: 2024-05-09 11:14
 */
@Slf4j
@RequestMapping("/testCacheV3")
@RestController
public class TwoCacheTestControllerV3 {

    Map<String, User> map = new ConcurrentHashMap<>();

    {
        User user = new User();
        user.setId("1");
        user.setAge(11);
        user.setName("小王");
        map.put("1", user);
    }

    @RequestMapping("/getUserById/{id}")
    @DoubleCache(cacheName = "testUser", key = "#id")
    public User getUserById(@PathVariable("id") String id) {
        // 模拟从数据库获取数据需要1s
        AsyncUtil.sleep(1, TimeUnit.SECONDS);
        User userFormDB = map.get(id);
        return userFormDB;
    }

    @PostMapping("/updateById")
    @DoubleCache(cacheName = "testUser", key = "#id", type = CacheType.PUT)
    public User updateUser(@RequestBody User user) {
        log.info("update user data");
        AsyncUtil.sleep(1, TimeUnit.SECONDS);
        map.put(user.getId(), user);
        return user;
    }
}