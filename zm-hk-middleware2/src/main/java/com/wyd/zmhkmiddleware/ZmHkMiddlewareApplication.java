package com.wyd.zmhkmiddleware;

import com.google.gson.reflect.TypeToken;
import com.wyd.zmhkmiddleware.business.model.hk.result.BatchAddOrgResult;
import com.wyd.zmhkmiddleware.business.model.hk.result.HaiKangResult;
import com.wyd.zmhkmiddleware.business.service.hk.util.HaiKangInvocationUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
@MapperScan("com.wyd.zmhkmiddleware.business.mapper")
public class ZmHkMiddlewareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZmHkMiddlewareApplication.class, args);
    }

}
