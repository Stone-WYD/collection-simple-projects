package com.wyd.satokendemospringboot.demos.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.common.result.MyResultUtil;
import com.wyd.satokendemospringboot.demos.entity.dto.MyUserDTO;
import com.wyd.satokendemospringboot.demos.entity.query.UserQuery;
import com.wyd.satokendemospringboot.demos.service.LoginService;
import com.wyd.satokendemospringboot.demos.util.Constants;
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
    public MyResult passwordLogin(@Validated(value = UserQuery.loginQuery.class)
                                      @RequestBody UserQuery query){
        // 账号密码登录
        return loginService.passwordLogin(query);
    }

    @ApiOperation(value = "手机号登录")
    @PostMapping("telephoneLogin")
    public MyResult telephoneLogin(@Validated(value = UserQuery.telephoneLoginQuery.class)
                                       @RequestBody UserQuery query){
        MyResult myResult = new MyResult();
        // TODO: 2023/5/17 手机号登录逻辑

        return MyResultUtil.getTrueResult(myResult);
    }
    @ApiOperation(value = "登出")
    @RequestMapping(value = "/logout",  method = RequestMethod.GET)
    public MyResult logout(){
        // 退出登录
        StpUtil.logout();
        return MyResultUtil.getTrueResult(new MyResult());
    }

    @ApiOperation(value = "astoken角色测试：写文章")
    @SaCheckRole( value = {Constants.GOODS_ADMIN, Constants.ADMIN}, mode = SaMode.OR)
    @RequestMapping( value = "/getUserInfo", method = RequestMethod.POST)
    public MyResult<MyUserDTO> getUserInfo(Long userId){
        // 实际调用没有用，这里只是测试 Knife4j 使用的
        MyResult myResult = new MyResult<>();
        myResult.setData(new MyUserDTO(userId, null, null));
        return MyResultUtil.getTrueResult(myResult);
    }

}
