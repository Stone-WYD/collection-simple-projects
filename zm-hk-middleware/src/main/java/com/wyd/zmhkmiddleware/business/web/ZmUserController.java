package com.wyd.zmhkmiddleware.business.web;

import com.google.gson.Gson;
import com.wyd.zmhkmiddleware.business.service.TestThirdService;
import com.wyd.zmhkmiddleware.business.web.model.test.AjaxResult;
import com.wyd.zmhkmiddleware.business.web.model.test.UserEntity;
import com.wyd.zmhkmiddleware.business.web.model.zm.organization.OrganizationInfoBody;
import com.wyd.zmhkmiddleware.business.web.model.zm.position.PositionInfoBody;
import com.wyd.zmhkmiddleware.business.web.model.zm.user.UserInfoBody;
import com.wyd.zmhkmiddleware.business.web.model.zm.ZmCommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xh
 * @date 2025-03-03
 * @Description: 中免用户接口
 */
@RestController
@RequestMapping("/zm")
public class ZmUserController {


    @Autowired
    private TestThirdService testThirdService;

    /*人员主数据*/
    @PostMapping("/getUpdateUserInfo")
    public String getUserInfo(@RequestBody ZmCommonResult<UserInfoBody> baseUserInfo) {
        System.out.println(new Gson().toJson(baseUserInfo));

        AjaxResult<UserEntity> result = testThirdService.test(baseUserInfo);

        return result.getMessage();
//        return null;
    }

    /*组织主数据*/
    @PostMapping("/getUpdateOrganizationInfo")
    public String getUpdateOrganizationInfo(@RequestBody ZmCommonResult<OrganizationInfoBody> organizationInfo) {
        System.out.println(new Gson().toJson(organizationInfo));
        return "success";
    }

    /*岗位主数据*/
    @PostMapping("/getUpdatePositionInfo")
    public String getUpdatePositionInfo(@RequestBody ZmCommonResult<PositionInfoBody> positionInfo) {
        System.out.println(new Gson().toJson(positionInfo));
        return "success";
    }

}
