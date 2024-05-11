package com.wyd.redisapp.demos.web.cachetest.v3.extend.impl.rabbitmq;

import com.wyd.redisapp.demos.web.cachetest.v3.extend.SynchronizeCache;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: redis-app
 * @author: Stone
 * @create: 2024-05-11 21:13
 */
@Configuration
public class MqConfig {

    @Bean
    public MessageConverter messageConverter() {
        // 1.定义消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        // 2.配置自动创建消息id，用于识别不同消息，也可以在业务中基于ID判断是否是重复消息
        jackson2JsonMessageConverter.setCreateMessageIds(true);
        return jackson2JsonMessageConverter;
    }

    /*@Bean
    public FanoutExchange fanoutExchangeForDoubleCache() {
        return ExchangeBuilder.fanoutExchange("double_cache.fanout").build();
    }

    @Bean
    public Queue fanoutQueueForDoubleCache() {
        return new Queue(SynchronizeCache.localIp);
    }

    // 绑定交换机和队列
    @Bean
    public Binding bindingQueueForDoubleCache(FanoutExchange fanoutExchangeForDoubleCache,
                                              Queue fanoutQueueForDoubleCache) {
        return BindingBuilder.bind(fanoutQueueForDoubleCache).to(fanoutExchangeForDoubleCache);
    }*/


}