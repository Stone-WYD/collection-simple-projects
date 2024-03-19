package com.njxnet.asset_manage.config;

import com.njxnet.asset_manage.util.ApplicationContextUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: asset-manage
 * @description: 工具类配置加入ioc容器
 * @author: Stone
 * @create: 2024-03-19 14:34
 **/
@Configuration
public class MyConfig {

    @Bean
    public ApplicationContextUtil applicationContextUtil() {
        return new ApplicationContextUtil();
    }
}

