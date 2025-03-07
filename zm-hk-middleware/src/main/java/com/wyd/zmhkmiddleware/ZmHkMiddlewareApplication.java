package com.wyd.zmhkmiddleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.wyd.zmhkmiddleware.business.service.client")
public class ZmHkMiddlewareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZmHkMiddlewareApplication.class, args);
    }

}
