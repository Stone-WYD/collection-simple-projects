package com.njxnet.asset_manage.config;

import cn.dev33.satoken.stp.StpInterface;
import com.njxnet.asset_manage.entity.MyUser;
import com.njxnet.asset_manage.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionConfig implements StpInterface {

    @Resource
    private UserService userService;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // TODO: 2024/4/15 项目当前没有persion校验
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 此处业务设计：每个用户只会有一种角色
        MyUser user = userService.queryById((Long.valueOf((String) loginId)) );
        List<String> roleList = new ArrayList<>();
        roleList.add(String.valueOf(user.getRoleId()));
        return roleList;
    }
}
