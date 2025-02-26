package com.wyd.asset_manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyd.asset_manage.common.AjaxResult;
import com.wyd.asset_manage.model.AssetDTO;
import com.wyd.asset_manage.model.query.AssetQuery;
import com.wyd.asset_manage.service.AssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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

    @ApiOperation(value = "导出 excel 表格模板", notes = "导出 excel 表格模板")
    @RequestMapping(value = "/getExcel", method = RequestMethod.GET)
    public void getExcelTemplate(HttpServletResponse response) {
        assetService.getExcelTemplate(response);
    }

    @ApiOperation(value = "根据 excel 文件导入数据", notes = "根据 excel 文件导入数据")
    @RequestMapping(value = "/importDataFromExcel", method = RequestMethod.POST)
    public AjaxResult<?> importDataFromExcel(@RequestPart("excelFile") MultipartFile excelFile) {
       return assetService.importDataFromExcel(excelFile);
    }
    
}

