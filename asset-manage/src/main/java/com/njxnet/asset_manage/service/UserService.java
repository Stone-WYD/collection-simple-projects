package com.njxnet.asset_manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.entity.Asset;
import com.njxnet.asset_manage.entity.MyUser;
import com.njxnet.asset_manage.model.UserDTO;
import com.njxnet.asset_manage.model.query.UserManageQuery;

/**
 * @program: asset-manage
 * @description: 用户service
 * @author: Stone
 * @create: 2024-04-15 13:44
 **/
public interface UserService extends IService<MyUser> {
    AjaxResult change(UserDTO query);

    AjaxResult<Page<UserDTO>> query(UserManageQuery query);

    void delete(String userId);

    MyUser queryById(Long userId);
}

