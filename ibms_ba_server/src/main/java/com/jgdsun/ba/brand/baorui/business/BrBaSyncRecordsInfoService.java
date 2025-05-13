package com.jgdsun.ba.brand.baorui.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgdsun.ba.bootstrap.business.BaSyncRecordsInfoService;
import com.jgdsun.ba.brand.baorui.client.BrFeignClient;
import com.jgdsun.ba.brand.baorui.model.deviceparam.BrDeviceParam;
import com.jgdsun.ba.brand.baorui.model.deviceparam.CommonDeviceData;
import com.jgdsun.ba.brand.baorui.model.deviceparam.DeviceDataStruct;
import com.jgdsun.ba.brand.baorui.model.deviceparam.query.BrDeviceParamQuery;
import com.jgdsun.ba.brand.mp.TBaParameterMapperServiceImpl;
import com.jgdsun.ba.framework.util.SimpleCache;
import com.jgdsun.ba.mybatis.entity.TBa;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.mybatis.entity.TBaParameterValue;
import com.jgdsun.ba.mybatis.mapper.TBaMapper;
import com.jgdsun.ba.mybatis.mapper.TBaParameterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Stone
 * @since 2025-04-30
 */
@Slf4j
@Service
public class BrBaSyncRecordsInfoService extends BaSyncRecordsInfoService {

    @Resource
    private BrFeignClient brFeignClient;

    @Autowired
    private TBaParameterMapper baParameterMapper;

    @Autowired
    private TBaMapper tBaMapper;

    @Autowired
    private TBaParameterMapperServiceImpl tBaParameterService;


    private final SimpleCache<List<TBaParameter>> tBaParametersCache = new SimpleCache<>(5);
    private final SimpleCache<List<TBaParameterValue>> tBaParameterValuesCache = new SimpleCache<>(5);

    @Override
    protected List<TBaParameter> getTBaParameters() {
        // 业务上不存在多线程情况，如果有即使重复操作概率也不高，不会造成性能损耗的影响
        List<TBaParameter> data = tBaParametersCache.getData();
        if (CollectionUtil.isEmpty(data)) {
            convertDbData();
            return tBaParametersCache.getData();
        }
        return data;
    }

    @Override
    protected List<TBaParameterValue> getTBaParameterValues() {
        // 业务上不存在多线程情况，如果有即使重复操作概率也不高，不会造成性能损耗的影响
        List<TBaParameterValue> data = tBaParameterValuesCache.getData();
        if (CollectionUtil.isEmpty(data)) {
            convertDbData();
            return tBaParameterValuesCache.getData();
        }
        return data;
    }

    public void convertDbData() {
        // 获取所有设备
        LambdaQueryWrapper<TBa> tBalq = new LambdaQueryWrapper<>();
        tBalq.select(TBa::getId, TBa::getEquipmentTypeId);
        List<TBa> tBas = tBaMapper.selectList(tBalq);
        // 获取所有设备参数
        LambdaQueryWrapper<TBaParameter> tbaParameterlq = new LambdaQueryWrapper<>();
        tbaParameterlq.select(TBaParameter::getEquipmentId, TBaParameter::getId, TBaParameter::getObName);
        Map<String, Map<String, String>> baId2obName2paramId = baParameterMapper.selectList(tbaParameterlq).stream()
                .collect(Collectors.groupingBy(TBaParameter::getEquipmentId, Collectors.toMap(TBaParameter::getObName, TBaParameter::getId)));
        // 调用接口获取设备读数
        List<TBaParameterValue> tBaParameterValues = new ArrayList<>();
        tBas.forEach(ba -> {
            try {
                BrDeviceParamQuery query = new BrDeviceParamQuery();
                query.setSubsystemId(ba.getEquipmentTypeId());
                query.setId(ba.getId());
                List<BrDeviceParam> brDeviceParamList = brFeignClient.queryAllcommondeviceinfo(query).getData();
                if (CollectionUtil.isNotEmpty(brDeviceParamList)) {
                    // 只会返回一条记录
                    BrDeviceParam brDeviceParam = brDeviceParamList.get(0);
                    CommonDeviceData commonDeviceData = brDeviceParam.getCommonDeviceData();
                    if (ObjectUtil.isNotNull(commonDeviceData)) {
                        List<DeviceDataStruct> deviceDataStruct = commonDeviceData.getDeviceDataStruct();
                        if (CollectionUtil.isNotEmpty(deviceDataStruct)) {
                            deviceDataStruct.forEach(dataStruct -> {
                                TBaParameterValue tBaParameterValue = new TBaParameterValue();
                                tBaParameterValues.add(tBaParameterValue);
                                tBaParameterValue.setParameterId(baId2obName2paramId.get(ba.getId()).get(dataStruct.getIdentifier()));
                                if ("true".equals(dataStruct.getData())) {
                                    tBaParameterValue.setValue("1");
                                } else if ("false".equals(dataStruct.getData())) {
                                    tBaParameterValue.setValue("0");
                                } else {
                                    tBaParameterValue.setValue(dataStruct.getData());
                                }
                                Long timestamp = dataStruct.getTimestamp();
                                if (ObjectUtil.isNotEmpty(timestamp)) {
                                    tBaParameterValue.setAddtime(new java.util.Date(timestamp));
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                log.error("设备{}获取数据失败", ba.getId(), e);
            }
        });
        // 获取要更新的数据
        getUpdateData(tBaParameterValues);
    }

    private void getUpdateData(List<TBaParameterValue> tBaParameterValues) {
        Set<String> parameterIds = tBaParameterValues.stream().map(TBaParameterValue::getParameterId).collect(Collectors.toSet());
        if (CollectionUtil.isNotEmpty(parameterIds)) {
            List<TBaParameterValue> updatedTBaParameterValues = new ArrayList<>();
            List<TBaParameter> updatedTBaParameters = new ArrayList<>();

            List<TBaParameter> tbBParameters = tBaParameterService.listByIds(parameterIds);
            // 如果 tbBParameters 的 lastValue 发生了变化，则更新
            Map<String, TBaParameter> paramId2TBaParameter = tbBParameters.stream().collect(Collectors.toMap(TBaParameter::getId, p -> p));
            for (TBaParameterValue tBaParameterValue : tBaParameterValues) {
                TBaParameter baParameter = paramId2TBaParameter.get(tBaParameterValue.getParameterId());
                // 判断是否是需要更新的数据
                if (StrUtil.isEmpty(baParameter.getLastvalue()) || !baParameter.getLastvalue().equals(tBaParameterValue.getValue())) {
                    baParameter.setLastvalue(tBaParameterValue.getValue());
                    baParameter.setLasttime(tBaParameterValue.getAddtime());
                    updatedTBaParameters.add(baParameter);
                    updatedTBaParameterValues.add(tBaParameterValue);
                }
            }
            // 对变化的值进行缓存
            tBaParametersCache.setData(updatedTBaParameters);
            tBaParameterValuesCache.setData(updatedTBaParameterValues);
        }
    }
}
