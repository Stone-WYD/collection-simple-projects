package com.wyd.zmhkmiddleware.business.service.local;

import com.wyd.zmhkmiddleware.business.model.local.dto.LoginDTO;
import com.wyd.zmhkmiddleware.business.model.local.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Admin
* @description 针对表【user】的数据库操作Service
* @createDate 2025-05-10 09:46:57
*/
public interface UserService extends IService<User> {

    LoginDTO login(String username, String password);
}
