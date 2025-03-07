package com.wyd.zmhkmiddleware.business.service;

import com.google.gson.Gson;
import com.wyd.zmhkmiddleware.business.service.client.FeignTestClient;
import com.wyd.zmhkmiddleware.business.web.model.test.AjaxResult;
import com.wyd.zmhkmiddleware.business.web.model.test.Query;
import com.wyd.zmhkmiddleware.business.web.model.test.UserEntity;
import com.wyd.zmhkmiddleware.business.web.model.zm.ZmCommonResult;
import com.wyd.zmhkmiddleware.business.web.model.zm.user.UserInfoBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xh
 * @date 2025-03-04
 * @Description:
 */
@Service
public class TestThirdService {

    @Autowired
    private FeignTestClient feignTestClient;

    public AjaxResult<UserEntity> test(ZmCommonResult<UserInfoBody> baseUserInfo) {
        System.out.println("========调用了feignTest4===========");
        ZmCommonResult<UserInfoBody> userInfoBodyZmCommonResult = feignTestClient.feignTest4();
        System.out.println("========打印返回结果：" + new Gson().toJson(userInfoBodyZmCommonResult) + "===========");


        System.out.println(baseUserInfo);
        Query<UserEntity> query = new Query<>();
        query.setQuery(new UserEntity(1L, "wyd"));
        return feignTestClient.feignTest(query);
    }

    /*public ZmCommonResult<UserInfoBody> getTestData() {
        UserInfoBody userInfoBody = new UserInfoBody();
        userInfoBody.setETEMPLBANK(new ArrayList<>());
        userInfoBody.setETEMPLBASIC(new ArrayList<>());

    }*/
}
