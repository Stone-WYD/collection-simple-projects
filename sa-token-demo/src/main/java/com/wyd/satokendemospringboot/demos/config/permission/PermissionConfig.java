package com.wyd.satokendemospringboot.demos.config.permission;

import cn.dev33.satoken.stp.StpInterface;
import com.wyd.satokendemospringboot.demos.service.PermissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionConfig implements StpInterface {

    @Resource
    private PermissionService permissionService;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return permissionService.queryAllPermission((Long.valueOf((String) loginId) ));
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 此处业务设计：每个用户只会有一种角色
        String role = permissionService.queryRole((Long.valueOf((String) loginId)) );
        List<String> roleList = new ArrayList<>();
        roleList.add(role);
        return roleList;
    }
}
