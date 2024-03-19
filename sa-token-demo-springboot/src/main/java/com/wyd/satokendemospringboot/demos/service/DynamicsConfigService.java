package com.wyd.satokendemospringboot.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.entity.DynamicsConfig;
import com.wyd.satokendemospringboot.demos.model.dto.DynamicsConfigDTO;

import java.util.List;

/**
 * (DynamicsConfig)表服务接口
 *
 * @author Stone
 * @since 2023-07-19 11:54:45
 */
public interface DynamicsConfigService extends IService<DynamicsConfig> {

    MyResult getConfig();

    MyResult updateConfig(List<DynamicsConfigDTO> dtoList);

    void init();

    MyResult showConfig2();
}

