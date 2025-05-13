package com.jgdsun.ba;

import com.jgdsun.ba.bootstrap.BootstrapService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import static java.lang.Thread.sleep;

/**
 * @author xh
 * @date 2025-04-08
 * @Description:
 */
@EnableScheduling
@EnableFeignClients(basePackages = "com.jgdsun.ba.brand.baorui.client")
@MapperScan("com.jgdsun.ba.mybatis.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy  // 启用AOP支持
public class SpringApp {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringApp.class, args);
        // 启动服务时刷新一次数据
        BootstrapService bootstrapService = context.getBean(BootstrapService.class);
        bootstrapService.syncBaBasicRecordsInfoTask();
        // bootstrapService.syncPatrolRecordsTask();
    }
}