package com.wyd.zmhkmiddleware.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;

/**
 * @author Stone
 * @since 2025-05-12
 */
public class MyHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private final MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(new ObjectMapper());
    private final GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter(gson());

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        // clazz 来自 com.wyd.zmhkmiddleware.business.model.zm 包下则使用gson，否则使用jackson
        if (clazz.getPackage().getName().startsWith("com.wyd.zmhkmiddleware.business.model.zm")) {
            return gsonHttpMessageConverter.read(clazz, inputMessage);
        } else {
            // 用 Jackson 解析
            return jacksonConverter.read(clazz, inputMessage);
        }
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // 可选：决定写入时是否也做 fallback，这里保持简单只处理读取
        jacksonConverter.write(o, null, outputMessage);
    }

    private Gson gson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")  // 自定义日期格式
                .serializeNulls()                      // 序列化null值
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // 字段命名策略
                .create();
    }

}
