package com.wyd.satokendemospringboot.demos.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.common.result.MyResultUtil;
import com.wyd.satokendemospringboot.demos.dao.DynamicsConfigDao;
import com.wyd.satokendemospringboot.demos.entity.DynamicsConfig;
import com.wyd.satokendemospringboot.demos.model.dto.DynamicsConfigDTO;
import com.wyd.satokendemospringboot.demos.service.DynamicsConfigService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.env.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * (DynamicsConfig)表服务实现类
 *
 * @author Stone
 * @since 2023-07-19 11:54:45
 */
@Slf4j
@RefreshScope
@Service("dynamicsConfigService")
public class DynamicsConfigServiceImpl extends ServiceImpl<DynamicsConfigDao, DynamicsConfig> implements DynamicsConfigService {

    @Value("${config.exposure.name}")
    private String name;

    @Value("${config.exposure.version}")
    private String version;

    @Resource
    private ContextRefresher contextRefresher;

    @Resource
    private ConfigurableEnvironment environment;

    @Override
    public MyResult getConfig() {
        List<DynamicsConfig> list = query().list();
        MyResult<Object> result = new MyResult<>();
        // 转换成dto
        result.setData(DynamicsConfigUtil.convertToDTO(list));
        return MyResultUtil.getTrueResult(result);
    }

    @Override
    public MyResult updateConfig(List<DynamicsConfigDTO> dtoList) {
        // 转换成实体类对象
        List<DynamicsConfig> configList = DynamicsConfigUtil.convertToConfig(dtoList);
        // 刷新Spring中的配置
        refreshConfig(configList);
        // 更新数据库
        updateBatchById(configList);
        return MyResultUtil.getTrueResult(new MyResult<>());
    }

    @Override
    public void init() {
        List<DynamicsConfig> list = query().list();
        if (CollectionUtil.isEmpty(list)){
            initConfigs();
        } else {
            refreshConfig(list);
        }
    }

    @Override
    public MyResult showConfig2() {
        MyResult<Object> myResult = new MyResult<>();
        myResult.setData("name: " + name + ";" +  "version: " + version );
        return MyResultUtil.getTrueResult(myResult);
    }

    private void initConfigs() {
        List<DynamicsConfig> configList = new ArrayList<>();
        MutablePropertySources sources = environment.getPropertySources();
        for (PropertySource<?> source : sources) {
            if (source instanceof EnumerablePropertySource) {
                String[] propertyNames = ((EnumerablePropertySource<?>) source).getPropertyNames();
                // 进行一些初始化操作
                for (String name : propertyNames) {
                    if (name.contains("config") && name.contains("exposure")) {
                        DynamicsConfig config = new DynamicsConfig();
                        config.setCompleteCode(name);
                        config.setValue(Objects.requireNonNull(source.getProperty(name)).toString());
                        configList.add(config);
                    }
                }
            }
        }
        if (CollectionUtil.isNotEmpty(configList)){
            saveBatch(configList);
        }
    }

    /**
    * @Description: 刷新配置
    * @Author: Stone
    * @Date: 2023/7/19
    */
    private void refreshConfig(List<DynamicsConfig> configList) {
        // 修改配置文件中属性
        HashMap<String, Object> map = new HashMap<>();
        // 更新配置值
        for (DynamicsConfig config : configList) {
            map.put(config.getCompleteCode(), config.getValue());
        }
        MapPropertySource propertySource = new MapPropertySource("dynamic", map);
        // 将修改后的配置设置到environment中
        environment.getPropertySources().addFirst(propertySource);
        // 异步调用refresh方法，避免阻塞一直等待无响应
        new Thread(() ->{
            log.info("开始刷新");
            contextRefresher.refresh();
            log.info("刷新完成");
        } ).start();
    }

    private static class DynamicsConfigUtil {

        /**
         * @Description: 数据库实体类转成dto
         * @Author: Stone
         * @Date: 2023/7/19
         */
        public static List<DynamicsConfigDTO> convertToDTO(List<DynamicsConfig> configList ){
            List<DynamicsConfigDTO> resultList = new ArrayList<>();
            for (DynamicsConfig config : configList) {
                DynamicsConfigDTO dto = BeanUtil.copyProperties(config, DynamicsConfigDTO.class);
                // 暂时简单处理
                resultList.add(dto);
            }
            return resultList;
        }

        public static List<DynamicsConfig> convertToConfig(List<DynamicsConfigDTO> dtoList) {
            List<DynamicsConfig> resultList = new ArrayList<>();
            for (DynamicsConfigDTO dto : dtoList) {
                DynamicsConfig config = BeanUtil.copyProperties(dto, DynamicsConfig.class);
                // 暂时简单处理
                resultList.add(config);
            }
            return resultList;
        }
    }
}

