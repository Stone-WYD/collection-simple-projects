package com.wyd.zmhkmiddleware.business.service.local.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyd.zmhkmiddleware.business.mapper.UserMapper;
import com.wyd.zmhkmiddleware.business.model.local.dto.LoginDTO;
import com.wyd.zmhkmiddleware.business.model.local.po.User;
import com.wyd.zmhkmiddleware.business.model.local.query.UpdatePasswordQuery;
import com.wyd.zmhkmiddleware.business.service.local.UserService;
import com.wyd.zmhkmiddleware.common.BaseException;
import com.wyd.zmhkmiddleware.util.LoginUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.wyd.zmhkmiddleware.common.enums.ResultStatusCode.LOGIN_ERROR;
import static com.wyd.zmhkmiddleware.common.enums.ResultStatusCode.PASSWORD_ERROR;

/**
* @author Admin
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-05-10 09:46:57
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private LoginUtils loginUtils;

    @Override
    public LoginDTO login(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, username);
        User user = getOne(queryWrapper);
        if (ObjectUtil.isEmpty(user) || !user.getPassword().equals(password)) {
            throw new BaseException(LOGIN_ERROR.getCode(), LOGIN_ERROR.getName());
        } else {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setToken(loginUtils.generageLoginToken(username));
            return loginDTO;
        }
    }

    @Override
    public void updatePassword(UpdatePasswordQuery param) {
        String userName = loginUtils.getUserName();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, userName).eq(User::getPassword, param.getOldPassword());
        User user = getOne(queryWrapper);
        if (ObjectUtil.isEmpty(user)) {
            throw new BaseException(PASSWORD_ERROR.getCode(), PASSWORD_ERROR.getName());
        }
        // 修改密码
        user.setPassword(param.getNewPassword());
        updateById(user);
    }
}




