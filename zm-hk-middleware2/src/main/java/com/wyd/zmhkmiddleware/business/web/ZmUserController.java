package com.wyd.zmhkmiddleware.business.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.gson.Gson;
import com.wyd.zmhkmiddleware.business.model.local.po.*;
import com.wyd.zmhkmiddleware.business.model.zm.ZmCommonResult;
import com.wyd.zmhkmiddleware.business.model.zm.organization.ITORGBASIC;
import com.wyd.zmhkmiddleware.business.model.zm.organization.OrganizationInfoBody;
import com.wyd.zmhkmiddleware.business.model.zm.position.ITPOSTBASIC;
import com.wyd.zmhkmiddleware.business.model.zm.position.PositionInfoBody;
import com.wyd.zmhkmiddleware.business.model.zm.user.ETEMPLBANK;
import com.wyd.zmhkmiddleware.business.model.zm.user.ETEMPLBASIC;
import com.wyd.zmhkmiddleware.business.model.zm.user.ETEMPLPOST;
import com.wyd.zmhkmiddleware.business.model.zm.user.UserInfoBody;
import com.wyd.zmhkmiddleware.business.service.local.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Stone
 * @since  2025-03-03
 */
@Slf4j
@RestController
@RequestMapping("/yxpatrol")
@Api(value = "中免接口，请勿调用", protocols = "http/https", tags = "中免接口，请勿调用")
public class ZmUserController {

    @Resource
    private EtEmplBankService etEmplBankService;

    @Resource
    private EtEmplBasicService etEmplBasicService;

    @Resource
    private EtEmplPostService etEmplPostService;

    @Resource
    private ItOrgBasicService itOrgBasicService;

    @Resource
    private ItPostBasicService itPostBasicService;

    /*人员主数据*/
    @PostMapping("/getUpdateUserInfo")
    public String getUserInfo(@RequestBody ZmCommonResult<UserInfoBody> baseUserInfo) {
        Gson gson = new Gson();
        log.info("中免调用人员主数据接口: /yxpatrol/getUpdateUserInfo传入数据：{}", gson.toJson(baseUserInfo));
        UserInfoBody userInfoBody = baseUserInfo.getIREQUEST().getBODY();
        if (ObjectUtil.isNotEmpty(userInfoBody)) {
            // 员工银行信息
            List<ETEMPLBANK> etemplbank = userInfoBody.getETEMPLBANK();
            if (CollectionUtil.isNotEmpty(etemplbank)) {
                List<EtEmplBank> dbdata = etemplbank.stream().map(p ->
                        gson.fromJson(gson.toJson(p), EtEmplBank.class)).collect(Collectors.toList());
                etEmplBankService.saveOrUpdateBatch(dbdata);
            }
            // 员工基本信息
            List<ETEMPLBASIC> etemplbasic = userInfoBody.getETEMPLBASIC();
            if (CollectionUtil.isNotEmpty(etemplbasic)) {
                List<EtEmplBasic> dbdata = etemplbasic.stream().map(p ->
                    gson.fromJson(gson.toJson(p), EtEmplBasic.class)).collect(Collectors.toList());
                etEmplBasicService.saveOrUpdateBatch(dbdata);
            }
            // 员工岗位信息
            List<ETEMPLPOST> etemplpost = userInfoBody.getETEMPLPOST();
            if (CollectionUtil.isNotEmpty(etemplpost)) {
                List<EtEmplPost> dbdata = etemplpost.stream().map(p ->
                    gson.fromJson(gson.toJson(p), EtEmplPost.class)).collect(Collectors.toList());
                etEmplPostService.saveOrUpdateBatch(dbdata);
            }
        }
        return "success";
    }

    /*组织主数据*/
    @PostMapping("/getUpdateOrganizationInfo")
    public String getUpdateOrganizationInfo(@RequestBody ZmCommonResult<OrganizationInfoBody> organizationInfo) {
        Gson gson = new Gson();
        log.info("中免调用组织主数据接口: /yxpatrol/getUpdateOrganizationInfo传入数据：{}", gson.toJson(organizationInfo));
        OrganizationInfoBody organizationInfoBody = organizationInfo.getIREQUEST().getBODY();
        if (ObjectUtil.isNotEmpty(organizationInfoBody)) {
            List<ITORGBASIC> itorgbasic = organizationInfoBody.getITORGBASIC();
            if (CollectionUtil.isNotEmpty(itorgbasic)) {
                List<ItOrgBasic> dbdata = itorgbasic.stream().map(p ->
                    gson.fromJson(gson.toJson(p), ItOrgBasic.class)).collect(Collectors.toList());
                itOrgBasicService.saveOrUpdateBatch(dbdata);
            }
        }
        return "success";
    }

    /*岗位主数据*/
    @PostMapping("/getUpdatePositionInfo")
    public String getUpdatePositionInfo(@RequestBody ZmCommonResult<PositionInfoBody> positionInfo) {
        Gson gson = new Gson();
        log.info("中免调用岗位主数据接口: /yxpatrol/getUpdatePositionInfo传入数据：{}", gson.toJson(positionInfo));
        PositionInfoBody positionInfoBody = positionInfo.getIREQUEST().getBODY();
        if (ObjectUtil.isNotEmpty(positionInfoBody)) {
            List<ITPOSTBASIC> itpostbasic = positionInfoBody.getITPOSTBASIC();
            if (CollectionUtil.isNotEmpty(itpostbasic)) {
                List<ItPostBasic> dbdata = itpostbasic.stream().map(p ->
                    gson.fromJson(gson.toJson(p), ItPostBasic.class)).collect(Collectors.toList());
                itPostBasicService.saveOrUpdateBatch(dbdata);
            }
        }
        return "success";
    }

}
