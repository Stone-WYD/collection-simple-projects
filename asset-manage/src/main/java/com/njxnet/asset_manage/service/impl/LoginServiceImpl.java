package com.njxnet.asset_manage.service.impl;

import cn.dev33.satoken.stp.StpUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.common.AjaxResultUtil;
import com.njxnet.asset_manage.dao.MyUserDao;
import com.njxnet.asset_manage.entity.MyUser;
import com.njxnet.asset_manage.model.query.UserQuery;
import com.njxnet.asset_manage.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.njxnet.asset_manage.common.ResultStatusCode.CAPTCHA_ERROR;
import static com.njxnet.asset_manage.common.ResultStatusCode.LOGIN_FREEZE;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Resource
    private MyUserDao myUserDao;

    @Override
    public AjaxResult passwordLogin(UserQuery userQuery) {


        LambdaQueryWrapper<MyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MyUser::getUserName, userQuery.getUserName()).eq(MyUser::getUserPassword, userQuery.getPassword());

        List<MyUser> myUsers = myUserDao.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(myUsers))
            return AjaxResultUtil.getBussiseFalseAjaxResult(new AjaxResult<>(), CAPTCHA_ERROR.getName(), CAPTCHA_ERROR.getCode());
        MyUser myUser = myUsers.get(0);
        // 封禁
        if (myUser.getEnable() != 1){
            return AjaxResultUtil.getBussiseFalseAjaxResult(new AjaxResult<>(), LOGIN_FREEZE.getName(), LOGIN_FREEZE.getCode());
        }

        // 登录成功
        StpUtil.login(myUser.getId());
        AjaxResult<MyUser> result = new AjaxResult<>();
        result.setData(myUser);
        return AjaxResultUtil.getTrueAjaxResult(result);
    }

}
