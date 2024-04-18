package com.njxnet.asset_manage.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.model.UserDTO;
import com.njxnet.asset_manage.model.query.UserManageQuery;
import com.njxnet.asset_manage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: asset-manage
 * @description: 用户管理controller
 * @author: Stone
 * @create: 2024-04-15 11:35
 **/
@SaCheckLogin
@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "创建或者更新用户信息")
    @PostMapping("/change")
    public AjaxResult change(@RequestBody UserDTO query){
        return userService.change(query);
    }

    @SaCheckRole( value = {"0"})
    @ApiOperation(value = "查询用户", notes = "查询用户")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public AjaxResult<Page<UserDTO>> queryAsset(@RequestBody UserManageQuery query) {
        return userService.query(query);
    }

    @SaCheckRole( value = {"0"})
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/delete/{userId}")
    public void passwordLogin(@PathVariable String userId){
        userService.delete(userId);
    }
}

