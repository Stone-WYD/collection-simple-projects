package com.wyd.redisapp;

import com.wyd.redisapp.demos.utils.IpUtil;
import com.wyd.redisapp.demos.utils.SpringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.net.SocketException;
import java.util.HashMap;

import static com.wyd.redisapp.demos.config.RedisClient.keyBytes;

@SpringBootTest
class RedisAppApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testRedisTemplate() {
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("queue"))) {
            redisTemplate.opsForStream().createGroup("queue", "g1");
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "2");
        redisTemplate.opsForStream().add("queue", map);
    }

    @Test
    void testIpString() throws SocketException {
       String localIp = IpUtil.getLocalIp4Address() + ":" + SpringUtil.getConfig("server.port");
        System.out.println(localIp);
    }

}
