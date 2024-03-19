package com.njxnet.asset_manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.entity.Asset;
import com.njxnet.asset_manage.model.AssetDTO;
import com.njxnet.asset_manage.model.query.AssetQuery;
import com.njxnet.asset_manage.service.AssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: asset-manage
 * @description: assert controller类
 * @author: Stone
 * @create: 2024-03-19 10:59
 **/
@RequestMapping(value = "/asset")
@RestController
@Api(value = "应收账款管理", protocols = "http/https", tags = "应收账款管理")
@Slf4j
public class AssetController {

    @Resource
    private AssetService assetService;

    @ApiOperation(value = "新增资产", notes = "新增资产")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public AjaxResult<?> insertAsset(@RequestBody @Validated(AssetDTO.insert.class) AssetDTO assetDTO) {
        return assetService.insertAsset(assetDTO);
    }

    @ApiOperation(value = "更新资产信息", notes = "更新资产信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResult<?> updateAsset(@RequestBody @Validated(AssetDTO.update.class) AssetDTO assetDTO) {
        return assetService.updateAsset(assetDTO);
    }

    @ApiOperation(value = "查询资产信息", notes = "查询资产信息")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public AjaxResult<Page<AssetDTO>> queryAsset(@RequestBody AssetQuery query) {
        return assetService.queryAsset(query);
    }

    @ApiOperation(value = "删除资产信息", notes = "删除资产信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public AjaxResult<?> deleteAsset(@RequestBody @Validated(AssetDTO.update.class) AssetDTO assetDTO) {
        return assetService.deleteAsset(assetDTO);
    }
    
}

