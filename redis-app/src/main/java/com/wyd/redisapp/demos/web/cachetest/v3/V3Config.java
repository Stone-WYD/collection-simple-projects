package com.wyd.redisapp.demos.web.cachetest.v3;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.wyd.redisapp.demos.web.cachetest.v3.extend.SynchronizeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @program: redis-app
 * @author: Stone
 * @create: 2024-05-11 10:02
 */
@Configuration
public class V3Config {

    @Bean
    public Cache<String,Object> caffeineCache(){
        return Caffeine.newBuilder()
                .initialCapacity(128)//初始大小
                .maximumSize(1024)//最大数量
                .expireAfterWrite(60, TimeUnit.SECONDS)//过期时间
                .build();
    }

    @Bean
    public SynchronizeCacheManager synchronizeCacheManager() {
        return new SynchronizeCacheManager();
    }
}