package com.wyd.zmhkmiddleware.business.web;

import com.wyd.zmhkmiddleware.advice.CheckToken;
import com.wyd.zmhkmiddleware.business.model.local.dto.LoginDTO;
import com.wyd.zmhkmiddleware.business.model.local.dto.SyncInfoDTO;
import com.wyd.zmhkmiddleware.business.model.local.query.LoginQuery;
import com.wyd.zmhkmiddleware.business.model.local.query.PersonQuery;
import com.wyd.zmhkmiddleware.business.service.local.SyncService;
import com.wyd.zmhkmiddleware.common.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Stone
 * @since 2025-05-12
 */
@Validated
@RestController
@RequestMapping(value = "/manager")
@Api(value = "人员信息管理", protocols = "http/https", tags = "人员信息管理")
public class PersonManagerController {

    @Resource
    private SyncService syncService;

    @CheckToken
    @RequestMapping(value = "/getPersonList", method = RequestMethod.POST)
    @ApiOperation(value = "获取人员信息", notes = "获取人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "访问令牌", required = true, dataType = "string", paramType = "header")
    })
    public AjaxCustomResult<List<SyncInfoDTO>> getPersonList(@RequestBody @Validated PersonQuery personQuery) throws BaseException {
        // 获取人员信息
        return syncService.getPersonList(personQuery);
    }

    @CheckToken
    @RequestMapping(value = "/syncPersonInfo", method = RequestMethod.POST)
    @ApiOperation(value = "同步人员信息", notes = "同步人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "访问令牌", required = true, dataType = "string", paramType = "header")
    })
    public AjaxResult<Boolean> syncInfo(@RequestBody List<SyncInfoDTO> dtoList) throws BaseException {
        // 同步人员信息
        return syncService.syncInfo(dtoList);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiOperation(value = "测试", notes = "测试")
    public AjaxResult<Boolean> test(@RequestBody LoginQuery param) throws BaseException {
        // 测试
        return AjaxResultUtil.getTrueAjaxResult();
    }


}
