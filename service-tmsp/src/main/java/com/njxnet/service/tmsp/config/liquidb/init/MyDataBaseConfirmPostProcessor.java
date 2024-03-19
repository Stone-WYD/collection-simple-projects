package com.njxnet.service.tmsp.config.liquidb.init;

import cn.hutool.core.util.StrUtil;
import com.njxnet.service.tmsp.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.net.URI;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @program: TMSP
 * @description: 数据库确认 postprocessor
 * @author: Stone
 * @create: 2024-01-02 20:10
 **/
@Slf4j
public class MyDataBaseConfirmPostProcessor implements BeanPostProcessor {

    private static boolean needInit = false;

    public static boolean needInit() {
        return needInit;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            DataSource dataSource = (DataSource) bean;
            if (needInit(dataSource)) {
                // 第一次启动该程序，需要进行一些初始化数据库的操作
                needInit = true;
            }
        }
        needInit = false;
        return bean;
    }

    /**
     * 检测一下数据库中表是否存在，若存在则不初始化；否则基于 schema-all.sql 进行初始化表
     *
     * @param dataSource
     * @return
     */
    private boolean needInit(DataSource dataSource) {
        String database = ApplicationContextUtil.getConfig("database.name");

        // 根据是否存在表来判断是否需要执行sql操作
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        if (autoInitDatabase(database)) {
            return true;
        }

        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT table_name FROM information_schema.TABLES where table_name = 'user_info' and table_schema = '" + database + "';");
        return CollectionUtils.isEmpty(list);
    }

    /**
     * 数据库不存在时，尝试创建数据库
     */
    private boolean autoInitDatabase(String database) {
        // 查询失败，可能是数据库不存在，尝试创建数据库之后再次测试。这里不能直接使用 dataSource，因为如果数据库不存在的话，使用 dataSource 会报错。
        URI url = URI.create(ApplicationContextUtil.getConfig("spring.datasource.url").substring(5));
        String uname = ApplicationContextUtil.getConfig("spring.datasource.username");
        String pwd = ApplicationContextUtil.getConfig("spring.datasource.password");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + url.getHost() + ":" + url.getPort() +
                "?useUnicode=true&characterEncoding=UTF-8&useSSL=false", uname, pwd);
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery("select schema_name from information_schema.schemata where schema_name = '" + database + "'");
            if (!set.next()) {
                // 不存在时，创建数据库
                String createDb = "CREATE DATABASE IF NOT EXISTS " + database;
                connection.setAutoCommit(false);
                statement.execute(createDb);
                connection.commit();
                log.info("创建数据库（{}）成功", database);
                if (set.isClosed()) {
                    set.close();
                }
                return true;
            }
            set.close();
            log.info("数据库已存在，无需初始化");
            return false;
        } catch (SQLException e2) {
            throw new RuntimeException(e2);
        }
    }

}
