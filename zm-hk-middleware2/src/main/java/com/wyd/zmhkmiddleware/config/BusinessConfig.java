package com.wyd.zmhkmiddleware.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.wyd.zmhkmiddleware.util.ApplicationContextUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @program: asset-manage
 * @description: 工具类配置加入ioc容器
 * @author: Stone
 * @create: 2024-03-19 14:34
 **/
@Configuration
public class BusinessConfig {

    @Value("${artemis.config.host}")
    private String host;

    @Value("${artemis.config.appKey}")
    private String appKey;

    @Value("${artemis.config.appSecret}")
    private String appSecret;

    @Bean
    public ApplicationContextUtil applicationContextUtil() {
        return new ApplicationContextUtil();
    }

    /**
     * 新增分页拦截器，并设置数据库类型为mysql
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQLITE));
        return interceptor;
    }

    @Bean
    public ArtemisConfig artemisConfig() {
        ArtemisConfig artemisConfig = new ArtemisConfig();
        artemisConfig.setHost(host);
        artemisConfig.setAppKey(appKey);
        artemisConfig.setAppSecret(appSecret);
        return artemisConfig;
    }

    @Bean("caffeineCacheManager")
    public CacheManager cacheManager(){
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                // 读后过期时间，第一次写后也会过期
                .expireAfterAccess(30, TimeUnit.MINUTES)
                // 初始化缓存空间大小
                .initialCapacity(100)
                // 最大的缓存条数
                .maximumSize(200)
        );
        return cacheManager;
    }

}

