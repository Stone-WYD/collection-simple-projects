package com.jgdsun.ba.mqtt;

import com.jgdsun.ba.mybatis.entity.TBaParameter;

/**
 * @author xh
 * @date 2025-04-21
 * @Description:
 */
public interface MQTTListener {

    void writeOne(TBaParameter parameter, String value);


}
