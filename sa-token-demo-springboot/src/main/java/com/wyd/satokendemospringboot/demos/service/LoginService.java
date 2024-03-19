package com.wyd.satokendemospringboot.demos.service;

import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.entity.query.UserQuery;

public interface LoginService {

    MyResult passwordLogin(UserQuery userQuery);

    MyResult telephoneLogin(UserQuery userQuery);
}
