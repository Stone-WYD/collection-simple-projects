package com.wyd.redisapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RedisAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisAppApplication.class, args);
    }

}
