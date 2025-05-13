package com.wyd.zmhkmiddleware.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xh
 * @date 2025-03-03
 * @Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${knife4j.enable}")
    private boolean knige4jEnable;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Knige4J 使用
        if (knige4jEnable) {
            MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
            jsonConverter.setObjectMapper(new ObjectMapper());
            converters.add(0, jsonConverter);
        } else {
            converters.add(new GsonHttpMessageConverter(gson()));
        }
//        converters.add(0, new MyHttpMessageConverter());
    }


    public Gson gson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")  // 自定义日期格式
                .serializeNulls()                      // 序列化null值
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // 字段命名策略
                .create();
    }
}

