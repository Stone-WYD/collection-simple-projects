package com.jgdsun.ba.brand.baorui.business;

import com.jgdsun.ba.bootstrap.business.BaControlService;
import com.jgdsun.ba.brand.baorui.client.BrFeignClient;
import com.jgdsun.ba.brand.baorui.model.BrCommonResult;
import com.jgdsun.ba.brand.baorui.model.deviceparam.query.control.BrControlQuery;
import com.jgdsun.ba.brand.baorui.model.deviceparam.query.control.Property;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author xh
 * @date 2025-04-22
 * @Description:
 */
@Slf4j
@Service
public class BrBaControlService implements BaControlService {

    @Resource
    private BrFeignClient brFeignClient;

    @Override
    public void control(TBaParameter parameter, String value) {

        BrControlQuery brControlQuery = new BrControlQuery();
        // 将 equipmentId 作为数组唯一对象放入
        ArrayList<String> deviceIds = new ArrayList<>();
        deviceIds.add(parameter.getEquipmentId());
        brControlQuery.setDeviceIds(deviceIds);

        // 构建 property
        ArrayList<Property> properties = new ArrayList<>();
        Property property = new Property();
        properties.add(property);
        brControlQuery.setProperties(properties);
        property.setIdentifier(parameter.getObName());
        // 区分 bool 和数值
        if ("BV".equals(parameter.getType())) {
            if ("0".equals(value)) property.setData(false);
            else property.setData(true);
        } else property.setData(value);

        // 结果处理
        BrCommonResult<String> result = brFeignClient.sendChangeData(brControlQuery);
        if (!result.getSuccess()) {
            log.error("控制设备失败：{}", result.getMsg());
        }
    }
}
