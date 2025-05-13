package com.jgdsun.ba.bootstrap.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import com.jgdsun.ba.brand.mp.TBaParameterMapperServiceImpl;
import com.jgdsun.ba.brand.mp.TBaParameterValueServiceImpl;
import com.jgdsun.ba.framework.mqtt.MqttListener;
import com.jgdsun.ba.framework.mqtt.MqttTemplate;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.mybatis.entity.TBaParameterValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jgdsun.ba.framework.mqtt.Constants.BA_VALUE_CHANGE_TOPIC;

/**
 * @author Stone
 * @since 2025-04-30
 */
@Service
public abstract class BaSyncRecordsInfoService{

    @Autowired
    private TBaParameterValueServiceImpl tBaParameterValueService;

    @Autowired
    private TBaParameterMapperServiceImpl tBaParameterService;

    @Resource
    TransactionTemplate template;

    @Autowired
    private MqttTemplate mqttTemplate;

    public void syncBaBasicRecordsInfo() {
        List<TBaParameter> tBaParameters = getTBaParameters();
        List<TBaParameterValue > tBaParameterValues = getTBaParameterValues();
        if (CollectionUtil.isEmpty(tBaParameters)) return;


        template.execute(status -> {
            try {
                // 更新当前值
                tBaParameterService.updateBatchById(tBaParameters);
                // 插入新纪录
                tBaParameterValueService.saveBatch(tBaParameterValues);
                return Boolean.TRUE;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });

        // 发送mqtt消息
        Set<String> deviceIds = tBaParameters.stream().map(TBaParameter::getEquipmentId).collect(Collectors.toSet());
        // mqtt 推送数据变更信息
        deviceIds.forEach(deviceId -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("id", deviceId);
            mqttTemplate.publish(BA_VALUE_CHANGE_TOPIC, jsonObject.toString());
        });
    }

    /**
     * 这里获取到的应该是表值发生变化的参数值和记录，不能是所有数据，且应该是获取数据库记录后更新表读数后的数据。
     * */
    protected abstract List<TBaParameter> getTBaParameters();

    protected abstract List<TBaParameterValue> getTBaParameterValues();

}
