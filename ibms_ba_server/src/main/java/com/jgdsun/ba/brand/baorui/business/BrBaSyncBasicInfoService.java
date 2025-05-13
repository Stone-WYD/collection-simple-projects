package com.jgdsun.ba.brand.baorui.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgdsun.ba.bootstrap.business.BaSyncBasicInfoService;
import com.jgdsun.ba.brand.baorui.client.BrFeignClient;
import com.jgdsun.ba.brand.baorui.model.BrCommonResult;
import com.jgdsun.ba.brand.baorui.model.deviceparam.BrDeviceParam;
import com.jgdsun.ba.brand.baorui.model.deviceparam.DataPropertyList;
import com.jgdsun.ba.brand.baorui.model.deviceparam.ValuesConfig;
import com.jgdsun.ba.brand.baorui.model.deviceparam.query.BrDeviceParamQuery;
import com.jgdsun.ba.brand.baorui.model.devicetype.BrDeviceType;
import com.jgdsun.ba.framework.util.SimpleCache;
import com.jgdsun.ba.mybatis.entity.TBa;
import com.jgdsun.ba.mybatis.entity.TBaEquipmentType;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.mybatis.mapper.TBaEquipmentTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stone
 * @since 2025-04-29
 */
@Service
public class BrBaSyncBasicInfoService extends BaSyncBasicInfoService {

    @Resource
    private BrFeignClient brFeignClient;

    @Autowired
    private TBaEquipmentTypeMapper typeMapper;

    private final SimpleCache<List<TBa>> tBasCache = new SimpleCache<>(5);
    private final SimpleCache<List<TBaParameter>> tBaParametersCache = new SimpleCache<>(5);


    @Override
    protected List<TBaEquipmentType> getDeviceTypes() {
        // 获取设备类型列表
        BrCommonResult<List<BrDeviceType>> result = brFeignClient.queryAllsubsystemsettings();
        List<BrDeviceType> deviceTypes = result.getData();
        if (CollectionUtil.isEmpty(deviceTypes)) {
            return new ArrayList<>();
        }
        List<TBaEquipmentType> dbData = new ArrayList<>();
        deviceTypes.forEach(deviceType -> {
            // 获取设备类型
            TBaEquipmentType tBaEquipmentType = new TBaEquipmentType();
            tBaEquipmentType.setId(String.valueOf(deviceType.getId()));
            tBaEquipmentType.setName(deviceType.getSubsystemName());
            tBaEquipmentType.setStat("1");
            dbData.add(tBaEquipmentType);
        });
        return dbData;
    }

    @Override
    protected List<TBa> getDevice() {
        // 业务上不存在多线程情况，如果有即使重复操作概率也不高，不会造成性能损耗的影响
        List<TBa> data = tBasCache.getData();
        if (CollectionUtil.isEmpty(data)) {
            convertDbData();
            return tBasCache.getData();
        }
        return data;
    }

    @Override
    protected List<TBaParameter> getDeviceParameters() {
        // 业务上不存在多线程情况，如果有即使重复操作概率也不高，不会造成性能损耗的影响
        List<TBaParameter> data = tBaParametersCache.getData();
        if (CollectionUtil.isEmpty(data)) {
            convertDbData();
            return tBaParametersCache.getData();
        }
        return data;
    }

    protected void convertDbData() {
        // 一种类型一种类型同步
        List<TBaEquipmentType> types = typeMapper.selectList(new LambdaQueryWrapper<>());
        List<TBaParameter> tBaParameters = new ArrayList<>();
        List<TBa> tBas = new ArrayList<>();
        types.forEach(type -> {
            // 获取设备类型下的设备
            BrDeviceParamQuery query = new BrDeviceParamQuery();
            query.setSubsystemId(type.getId());
            BrCommonResult<List<BrDeviceParam>> result = brFeignClient.queryAllcommondeviceinfo(query);
            List<BrDeviceParam> data = result.getData();
            if (CollectionUtil.isNotEmpty(data)) doConvertDbData(data, tBaParameters, tBas);
        });
        tBasCache.setData(tBas);
        tBaParametersCache.setData(tBaParameters);
    }

    public void doConvertDbData(List<BrDeviceParam> deviceParams, List<TBaParameter> tBaParameters, List<TBa> tBas) {
        deviceParams.forEach(deviceParam -> {
            TBa tBa = new TBa();
            tBa.setId(deviceParam.getId());
            tBa.setName(deviceParam.getDeviceName());
            tBa.setEnable(String.valueOf(deviceParam.getDeviceStatus()));
            tBa.setEquipmentTypeId(String.valueOf(deviceParam.getSubsystemId()));
            tBa.setAddress(deviceParam.getAddressName());
            if (deviceParam.getIsDeleted() == 1L) {
                tBa.setStat("0");
            } else {
                tBa.setStat("1");
            }
            // 转换设备信息
            tBas.add(tBa);
            // 转换设备属性信息
            List<DataPropertyList> dataPropertyList = deviceParam.getDataPropertyList();
            if (CollectionUtil.isNotEmpty(dataPropertyList)) {
                dataPropertyList.forEach(dataProperty -> {
                    TBaParameter baParameter = new TBaParameter();
                    baParameter.setEquipmentId(tBa.getId());
                    baParameter.setName(dataProperty.getName());
                    baParameter.setObName(dataProperty.getIdentifier());
                    if ("bool".equals(dataProperty.getType())) {
                        baParameter.setType("BV");
                    } else baParameter.setType("AV");
                    ValuesConfig valuesConfig = dataProperty.getValuesConfig();
                    // 配置 BV 选项
                    if (ObjectUtil.isNotEmpty(valuesConfig)) {
                        String options = valuesConfig.getFalse() + "," + valuesConfig.getTrue();
                        baParameter.setOptions(options);
                    }
                    // 配置单位
                    if (ObjectUtil.isNotEmpty(dataProperty.getSpecs()) &&
                            ObjectUtil.isNotEmpty(dataProperty.getSpecs().getUnit())) {
                        baParameter.setUnit(dataProperty.getSpecs().getUnit());
                    }
                    // 配置是否可读
                    if (StrUtil.isNotEmpty(dataProperty.getAccessMode())) {
                        if (dataProperty.getAccessMode().equals("r")) {
                            baParameter.setWriteAble("0");
                        } else baParameter.setWriteAble("1");
                    }
                    baParameter.setStat("1");
                    tBaParameters.add(baParameter);
                });
            }
        });
    }
}
