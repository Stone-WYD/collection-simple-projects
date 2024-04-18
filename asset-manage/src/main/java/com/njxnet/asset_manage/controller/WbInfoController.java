package com.njxnet.asset_manage.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.common.AjaxResultUtil;
import com.njxnet.asset_manage.common.BaseException;
import com.njxnet.asset_manage.common.ResultStatusCode;
import com.njxnet.asset_manage.entity.WbInfo;
import com.njxnet.asset_manage.model.UserDTO;
import com.njxnet.asset_manage.model.query.UserManageQuery;
import com.njxnet.asset_manage.service.WbInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: asset-manage
 * @description: 维保信息controller
 * @author: Stone
 * @create: 2024-04-16 14:42
 **/
@SaCheckLogin
@Api(tags = "维保信息维护")
@RestController
@RequestMapping("/wbInfo")
public class WbInfoController {

    @Resource
    private WbInfoService wbInfoService;

    @SaCheckRole( value = {"0"})
    @ApiOperation(value = "创建维保信息")
    @PostMapping("/create")
    public AjaxResult create(@RequestBody WbInfo wbInfo){
        if (wbInfo.getId()!=null) throw new BaseException(ResultStatusCode.FAIL.getCode(), "创建时不能传入id");
        wbInfoService.save(wbInfo);
        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }

    @ApiOperation(value = "更新维保信息")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody WbInfo wbInfo){
        if (wbInfo.getId()==null) throw new BaseException(ResultStatusCode.FAIL.getCode(), "更新时需要传入id");
        wbInfoService.updateById(wbInfo);
        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }

    @ApiOperation(value = "查询维保信息", notes = "查询维保信息")
    @RequestMapping(value = "/query/{assetId}", method = RequestMethod.GET)
    public AjaxResult<List<WbInfo>> queryAsset(@PathVariable Integer assetId) {
        return wbInfoService.queryByAssetId(assetId);
    }

    @SaCheckRole( value = {"0"})
    @ApiOperation(value = "删除维保信息")
    @DeleteMapping("/delete/{id}")
    public void passwordLogin(@PathVariable String id){
        wbInfoService.removeById(id);
    }


}

