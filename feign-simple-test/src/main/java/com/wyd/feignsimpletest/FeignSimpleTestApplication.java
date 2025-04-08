package com.wyd.feignsimpletest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.wyd.feignsimpletest.kaiheyun.client")
public class FeignSimpleTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignSimpleTestApplication.class, args);
    }

}
