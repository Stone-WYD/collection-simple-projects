package com.wyd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: asset-manage
 * @description: 启动类
 * @author: Stone
 * @create: 2024-03-19 09:41
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.wyd.asset_manage"})
@MapperScan(basePackages = {"com.wyd.asset_manage.dao"})
public class AssetManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetManageApplication.class, args);
    }
}

