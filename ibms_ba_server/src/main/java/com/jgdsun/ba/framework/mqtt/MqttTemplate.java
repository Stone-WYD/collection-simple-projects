package com.jgdsun.ba.framework.mqtt;


import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqttTemplate {

    @Autowired
    private MqttAsyncClient client;

    /**
     * 发布信息
     *
     * @param topic
     * @param msg
     */
    public void publish(String topic, String msg) {
        try {
            client.publish(topic, msg.getBytes(), 1, false);
        } catch (MqttException e) {
            log.error("mqtt 异常 " + e.getMessage());
        }
    }
}