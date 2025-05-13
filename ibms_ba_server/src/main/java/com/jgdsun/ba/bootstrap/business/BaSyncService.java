package com.jgdsun.ba.bootstrap.business;

import com.jgdsun.ba.framework.mqtt.MqttListener;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Stone
 * @since 2025-04-30
 */
@Service
public class BaSyncService implements MqttListener {

    @Resource
    private BaSyncBasicInfoService baSyncBasicInfoService;

    @Resource
    private BaSyncRecordsInfoService baSyncRecordsInfoService;

    @Resource
    private BaControlService baControlService;

    public void syncBasicInfo(){
        baSyncBasicInfoService.syncBasicInfo();
    }

    public void syncBaBasicRecordsInfo() {
        baSyncRecordsInfoService.syncBaBasicRecordsInfo();
    }

    @Override
    public void writeOne(TBaParameter parameter, String value) {
        baControlService.control(parameter, value);
    }
}
