package com.jgdsun.ba.bootstrap.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jgdsun.ba.framework.util.ApplicationContextUtil;
import com.jgdsun.ba.mybatis.entity.TBa;
import com.jgdsun.ba.mybatis.entity.TBaEquipmentType;
import com.jgdsun.ba.mybatis.entity.TBaParameter;
import com.jgdsun.ba.mybatis.mapper.TBaEquipmentTypeMapper;
import com.jgdsun.ba.mybatis.mapper.TBaMapper;
import com.jgdsun.ba.mybatis.mapper.TBaParameterMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Stone
 * @since 2025-04-29
 */
@Service
public abstract class BaSyncBasicInfoService {

    @Resource
    TBaEquipmentTypeMapper typeMapper;
    @Resource
    TBaParameterMapper parameterMapper;
    @Resource
    TBaMapper baMapper;
    @Resource
    TransactionTemplate template;

    public void syncBasicInfo() {
        // 考虑到有依赖关系，所以这么不能一起初始化，要一步步初始化
        // 获取设备类型和参数
        List<TBaEquipmentType> deviceTypes = getDeviceTypes();
        template.execute(status -> {
            try {
                deviceTypes.forEach(deviceType -> {
                    TBaEquipmentType brDeviceType = typeMapper.selectById(deviceType.getId());
                    if (ObjectUtil.isEmpty(brDeviceType)) {
                        // 添加设备类型
                        typeMapper.insert(deviceType);
                    } else {
                        // 更新设备类型
                        typeMapper.updateById(deviceType);
                    }
                });
                return Boolean.TRUE;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
        // 获取设备
        List<TBa> bas = getDevice();
        template.execute(status -> {
            try {
                bas.forEach(ba -> {
                    TBa dbBa = baMapper.selectById(ba.getId());
                    if (ObjectUtil.isEmpty(dbBa)) {
                        // 添加设备
                        baMapper.insert(ba);
                    } else {
                        // 更新设备
                        baMapper.updateById(ba);
                    }
                });
                return Boolean.TRUE;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
        // 获取设备参数
        List<TBaParameter> deviceParameters = getDeviceParameters();
        template.execute(status -> {
            try {
                deviceParameters.forEach(deviceParameter -> {
                    TBaParameter brDeviceParameter = parameterMapper.selectById(deviceParameter.getId());
                    if (ObjectUtil.isEmpty(brDeviceParameter)) {
                        // 添加设备参数
                        parameterMapper.insert(deviceParameter);
                    } else {
                        // 更新设备参数
                        parameterMapper.updateById(deviceParameter);
                    }
                });
                return Boolean.TRUE;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
    }

    /**
     * 获取到的数据，需要有id
     */
    protected abstract List<TBaEquipmentType> getDeviceTypes();

    protected abstract List<TBa> getDevice();

    protected abstract List<TBaParameter> getDeviceParameters();

}
