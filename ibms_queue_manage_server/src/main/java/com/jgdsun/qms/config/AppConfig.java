package com.jgdsun.qms.config;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;

/**
 * @author Stone
 * @since 2025-10-16
 */
@Configuration
public class AppConfig {

    @Bean(name = "db1",typed=true)
    public DataSource db1DataSource(@Inject("${db1}") HikariDataSource dataSource){
        return dataSource;
    }

}
