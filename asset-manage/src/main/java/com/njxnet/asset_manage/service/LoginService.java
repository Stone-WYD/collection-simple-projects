package com.njxnet.asset_manage.service;

import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.model.query.UserQuery;


public interface LoginService {

    AjaxResult passwordLogin(UserQuery userQuery);

}
