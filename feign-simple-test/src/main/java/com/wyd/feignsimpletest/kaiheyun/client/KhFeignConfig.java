package com.wyd.feignsimpletest.kaiheyun.client;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wyd.feignsimpletest.kaiheyun.util.KhFeignRequestContext;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.context.annotation.Bean;

/**
 * @author xh
 * @date 2025-03-05
 * @Description:
 */
public class KhFeignConfig {

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
    public RequestInterceptor requestInterceptorr() {
        return template -> {
            // 固定 header 强制设置Content-Type并指定编码
            if (template.url().contains("login")) {
                template.header("Content-Type", "application/json;charset=UTF-8");
            }

            if (!template.url().contains("login")) {
                // 非登录接口可能有些参数要设置
                // headers
                KhFeignRequestContext.getHeaders().forEach((key, value) -> {
                    if (StrUtil.isNotBlank(value)) {
                        template.header(key, value);
                    }
                });
                // 一些固定 param（这里只填充登录接口里获取到的一些通用内容），不考虑一个 key 对应数组 value 的情况
                // todo 如果有其他参数这里可以根据 url 进行区分
                KhFeignRequestContext.getParams().forEach((key, value) -> {
                    if (StrUtil.isNotBlank(value)) {
                        template.query(key, value);
                    }
                });
            }

            /* 打印信息用于测试时使用
            String curl = StrUtil.format("curl -X {} '{}' \\\n" +
                            "-H 'Content-Type: {}' \\\n" +
                            "-d '{}'",
                    template.method(),
                    template.url(),
                    template.headers().get("Content-Type"),
                    new String(template.body()), StandardCharsets.UTF_8);
            System.out.println("CURL Command:\n" + curl);*/
        };
    }
}

