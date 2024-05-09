package com.wyd.redisapp.demos.web.cachetest.v1;

import com.github.benmanes.caffeine.cache.Cache;
import com.wyd.redisapp.demos.config.RedisClient;
import com.wyd.redisapp.demos.utils.AsyncUtil;
import com.wyd.redisapp.demos.utils.JsonUtil;
import com.wyd.redisapp.demos.web.User;
import com.wyd.redisapp.demos.web.cachetest.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/testCacheV1")
@RestController
public class TwoCacheTestControllerV1 {

    @Autowired
    private Cache<String,Object> cache;

    Map<String, User> map = new ConcurrentHashMap<>();

    {
        User user = new User();
        user.setId("1");
        user.setAge(11);
        user.setName("小王");
        map.put("1", user);
    }

    @RequestMapping("/getUserById/{id}")
    public User getUserById(@PathVariable("id") String id) {
        String key = CacheConstant.USER + id;
        User user = (User) cache.get(key,
                k -> {
                    //先查询 Redis
                    User userFromRedis = JsonUtil.toObj(RedisClient.getStr(k), User.class) ;
                    if (Objects.nonNull(userFromRedis)) {
                        log.info("get data from redis");
                        return userFromRedis;
                    }

                    // Redis没有则查询 DB
                    log.info("get data from database");
                    // 模拟从数据库获取数据需要1s
                    AsyncUtil.sleep(1, TimeUnit.SECONDS);
                    User u = map.get(id);
                    RedisClient.setStrWithExpire(k, JsonUtil.toStr(u), 120000L);
                    return u;
                });
        return user;
    }

    @PostMapping("/updateById")
    public void updateUser(@RequestBody User user) {
        log.info("update user data");
        String key=CacheConstant.USER + user.getId();
        AsyncUtil.sleep(1, TimeUnit.SECONDS);
        map.put(key, user);
        //修改 Redis
        RedisClient.setStrWithExpire(key, JsonUtil.toStr(user), 120000L);
        // 修改本地缓存
        cache.put(key,user);
    }
}