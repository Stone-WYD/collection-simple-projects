package com.jgdsun.ba.brand.baorui.client;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jgdsun.ba.framework.logincheck.aspectj.exception.MyLoginException;
import com.jgdsun.ba.framework.util.FeignRequestContext;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.context.annotation.Bean;

/**
 * @author xh
 * @date 2025-03-05
 * @Description:
 */
public class BrFeignConfig {

    @Bean
    public Encoder feignEncoder(Gson gson) {
        return new GsonEncoder(gson);
    }

    @Bean
    public Decoder feignDecoder(Gson gson) {
        return new GsonDecoder(gson);
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder() {
            private final ErrorDecoder defaultDecoder = new Default();

            @Override
            public Exception decode(String methodKey, Response response) {
                if (response.status() == 401) {
                    return new MyLoginException("身份认证已过期 (HTTP 401)");
                }
                return defaultDecoder.decode(methodKey, response);
            }
        };
    }

    @Bean
    public RequestInterceptor requestInterceptorr() {
        return template -> {
            // todo 根据具体情况调整
            // 针对 POST 方法统一设置 JSON 请求头
            if ("POST".equalsIgnoreCase(template.method())) {
                template.header("Content-Type", "application/json;charset=UTF-8");
            }
            if (template.url().contains("token")) {
                template.header("Authorization", "Basic c3dvcmQ6c3dvcmRfc2VjcmV0");
            }
            // 有通用参数和请求头的话就添加
            FeignRequestContext.getHeaders().forEach((key, value) -> {
                if (StrUtil.isNotBlank(value)) {
                    template.header(key, value);
                }
            });
            FeignRequestContext.getParams().forEach((key, value) -> {
                if (StrUtil.isNotBlank(value)) {
                    template.query(key, value);
                }
            });
        };
    }


}

