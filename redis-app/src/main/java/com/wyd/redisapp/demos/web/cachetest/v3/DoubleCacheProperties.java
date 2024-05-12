package com.wyd.redisapp.demos.web.cachetest.v3;

/**
 * @program: redis-app
 * @author: Stone
 * @create: 2024-05-12 21:36
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 双重缓存配置
 * */
@Data
@Component
@ConfigurationProperties(prefix = "double.cache")
public class DoubleCacheProperties {

    private Boolean enable;

    private String type;

}