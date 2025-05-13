package com.jgdsun.ba.framework.mqtt;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.mybatis.mapper.TBaParameterMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

import static com.jgdsun.ba.framework.mqtt.Constants.*;

/**
 * @author xh
 * @date 2025-04-22
 * @Description:
 */
@Slf4j
@Configuration
public class MqttConfiguration {

    @Value("${mqtt.broker}")
    private String broker;
    @Value("${mqtt.username}")
    private String userName;
    @Value("${mqtt.password}")
    private String passWord;
    @Value("${mqtt.clientid}")
    private String clientId; //客户端标识


    @Bean
    public MqttAsyncClient mqttAsyncClient(MqttListener mqttListener, TBaParameterMapper baParameterMapper) throws MqttException {

        MemoryPersistence persistence = new MemoryPersistence();
        if (StrUtil.isEmpty(clientId)) {
            clientId = UUID.randomUUID().toString();
        }
        MqttAsyncClient client = new MqttAsyncClient(broker, clientId, persistence, new TimerPingSender());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        // 表示客户端允许同时存在的未收到 ACK 确认的 QoS > 0 的消息
        connOpts.setMaxInflight(100);
        connOpts.setCleanSession(false);
        connOpts.setAutomaticReconnect(true);
        connOpts.setKeepAliveInterval(30);
        connOpts.setUserName(userName);
        connOpts.setPassword(passWord.toCharArray());
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectionLost(Throwable cause) {
                log.info("连接失败");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                log.info("topic " + topic + "  " + message.toString());
                try {

                    if (topic.equals(DE_AIR_CONDITIONING_CONTROL_TOPIC)) {
                        //空调开关控制
                        log.info("空调开关控制 ");
                        // String msg = message.toString();
                        //   AirconditioningControlBean controlBean = gson.fromJson(msg, AirconditioningControlBean.class);
                        //   controlListener.controlAirConditioning(controlBean);
                    } else if (topic.equals(DE_AIRCONDITIONING_SET_TOPIC)) {
                        log.info("空调设置控制 ");
                        // String msg = message.toString();
                        //  AirconditioningSetBean setBean = gson.fromJson(msg, AirconditioningSetBean.class);
                        // controlListener.setAirConditioning(setBean);
                    } else if (topic.equals(BA_CONTROL_TOPIC)) {
                        log.info("BA控制");
                        JSONObject jsonObject = JSON.parseObject(message.toString());
                        String id = jsonObject.getString("id");

                        String value = jsonObject.getString("value");
                        TBaParameter parameter = baParameterMapper.selectById(id);
                        mqttListener.writeOne(parameter, value);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                try {
                    // 订阅myTopic话题
                    client.subscribe(DE_AIR_CONDITIONING_CONTROL_TOPIC, 1);//订阅一个
                    client.subscribe(DE_AIRCONDITIONING_SET_TOPIC, 1);//订阅一个
                    client.subscribe(BA_CONTROL_TOPIC, 1);//订阅一个
                    client.subscribe(BA_VALUE_CHANGE_TOPIC, 1);//订阅一个
                } catch (MqttException e) {
                    log.error("mqtt 订阅主题异常 " + e.getMessage());
                }
            }
        });
        client.connect(connOpts);
        log.info("mqtt client is created successfully!");
        return client;
    }


}
