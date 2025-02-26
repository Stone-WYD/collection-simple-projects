package com.wyd.asset_manage.service.common;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyd.asset_manage.common.AjaxResult;
import com.wyd.asset_manage.common.AjaxResultUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: HNCC
 * @description: 通用服务类，带泛型， T:查什么类型数据，Q:查询条件，DTO:查询后转换成什么类型返回
 * @author: Stone
 * @create: 2023-07-24 18:07
 **/
 public class MyCommonService {

    public static <T, DTO> AjaxResult<Page<DTO>> queryForPage(Class<DTO> dtoClass, PageQuery<T> pageQuery) {
        Page<T> pageResult = pageQuery.query();
        // 类型转换
        List<T> pageResultRecords = pageResult.getRecords();
        List<DTO> dtoResult = pageResultRecords.stream()
                .map(r -> BeanUtil.copyProperties(r, dtoClass)).collect(Collectors.toList());
        // 返回结果
        Page<DTO> resultPage = BeanUtil.copyProperties(pageResult, Page.class);
        resultPage.setRecords(dtoResult);
        return AjaxResultUtil.getTrueAjaxResult(resultPage);
    }
}
