package com.njxnet.service.tmsp.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.njxnet.service.tmsp.util.ApplicationContextUtil;
import com.njxnet.service.tmsp.util.MyThreadPoolExecutor;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class MyConfig {

    @Value("${config.url.base}")
    private String baseUrl;

    @Bean
    public ApplicationContextUtil applicationContextUtil() {
        return new ApplicationContextUtil();
    }

    @Bean
    public ThreadPoolExecutor myThreadPoolExecutor(){
        // 给线程命名
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("myThreadPoolExecutor-worker-%d").build();
        // 通过核心数确定线程数
        int processors = Runtime.getRuntime().availableProcessors();
        log.info("processors:{}", processors);
        return new MyThreadPoolExecutor(processors,
                processors * 2,
                0L,
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(1000),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }


    @Bean
    public WebClient myWebClient(){
        // 配置超时时间，读写超时时间
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(10))
                        .addHandlerLast(new WriteTimeoutHandler(10)));

        // 创建 webClient
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl)
                .build();
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
