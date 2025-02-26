package com.wyd.asset_manage.test.mybatis.ocp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: asset-manage
 * @description:
 * @author: Stone
 * @create: 2024-04-07 16:37
 **/
@Configuration
@MapperScan(basePackages = {"com.wyd.asset_manage.test.mybatis.ocp"})
public class MybatisOcpConfig {
}

