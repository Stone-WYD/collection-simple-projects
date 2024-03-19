package com.wyd.satokendemospringboot.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.entity.Ieum;
import com.wyd.satokendemospringboot.demos.entity.dto.IEumContainerDTO;


/**
 * (Ieum)表服务接口
 *
 * @author makejava
 * @since 2023-06-24 16:38:43
 */
public interface IeumService extends IService<Ieum> {

    MyResult testIEum(IEumContainerDTO iEumContainerDTO);

    MyResult testIEumWithDataBaseOps(IEumContainerDTO iEumContainerDTO);
}

