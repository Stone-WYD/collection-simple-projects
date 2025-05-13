package com.wyd.zmhkmiddleware.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class FeignGsonConfig {

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
}

