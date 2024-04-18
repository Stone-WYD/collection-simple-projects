package com.njxnet.asset_manage.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.common.AjaxResultUtil;
import com.njxnet.asset_manage.model.query.UserQuery;
import com.njxnet.asset_manage.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "登录模块")
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    // @ApiImplicitParam(name = "name",value = "姓名",required = true)
    @ApiOperation(value = "账户密码登录")
    @PostMapping("passwordLogin")
    public AjaxResult passwordLogin(@RequestBody UserQuery query){
        // 账号密码登录
        return loginService.passwordLogin(query);
    }

    @SaCheckLogin
    @ApiOperation(value = "登出")
    @RequestMapping(value = "/logout",  method = RequestMethod.GET)
    public void logout(){
        // 退出登录
        StpUtil.logout();
    }

}
