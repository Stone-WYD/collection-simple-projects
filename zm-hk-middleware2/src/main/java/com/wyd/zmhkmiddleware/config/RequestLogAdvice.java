package com.wyd.zmhkmiddleware.config;

/**
 * @author xh
 * @date 2025-04-11
 * @Description:
 */
import com.wyd.zmhkmiddleware.business.web.ZmUserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Objects;

@Slf4j
@ControllerAdvice
@ConditionalOnProperty(name = "zm.test", havingValue = "true") // 新增条件注解
public class RequestLogAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        // 判断是否是来自 ZmUserController 的方法
        return Objects.requireNonNull(methodParameter.getMethod()).getDeclaringClass() == ZmUserController.class;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
                                           Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            byte[] bodyBytes = StreamUtils.copyToByteArray(inputMessage.getBody());
            String rawBody = new String(bodyBytes, Charset.defaultCharset());
            log.info("中免传入参数数据为：{}", rawBody);

            // 2. 创建新的可重复读取的InputStream
            ByteArrayInputStream cachedInputStream = new ByteArrayInputStream(bodyBytes);
            return super.beforeBodyRead(new HttpInputMessage() {
                @Override
                public InputStream getBody() {
                    return cachedInputStream;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            }, parameter, targetType, converterType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read request body", e);
        }

    }
}
