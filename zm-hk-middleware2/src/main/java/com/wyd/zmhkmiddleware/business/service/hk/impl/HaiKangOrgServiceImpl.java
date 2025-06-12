package com.wyd.zmhkmiddleware.business.service.hk.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.gson.reflect.TypeToken;
import com.wyd.zmhkmiddleware.business.model.hk.query.BatchDeleteOrgQuery;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangOrg;
import com.wyd.zmhkmiddleware.business.model.hk.query.UpdateOrgQuery;
import com.wyd.zmhkmiddleware.business.model.hk.result.BatchAddOrgResult;
import com.wyd.zmhkmiddleware.business.model.hk.result.DeleteOrgResult;
import com.wyd.zmhkmiddleware.business.model.hk.result.HaiKangResult;
import com.wyd.zmhkmiddleware.business.service.hk.HaiKangOrgService;
import com.wyd.zmhkmiddleware.business.service.hk.util.HaiKangInvocationUtils;
import com.wyd.zmhkmiddleware.business.service.hk.util.UrlConstants;
import com.wyd.zmhkmiddleware.common.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xh
 * @date 2025-03-19
 * @Description:
 */
@Service
@Slf4j
public class HaiKangOrgServiceImpl implements HaiKangOrgService {

    @Resource
    private HaiKangInvocationUtils invocationUtils;

    @Override
    public HaiKangResult<BatchAddOrgResult> batchAddOrg(List<HaiKangOrg> orgs) {
        return invocationUtils.post(UrlConstants.BATCH_ADD_ORG, orgs,
                new TypeToken<HaiKangResult<BatchAddOrgResult>>(){});
    }

    @Override
    public HaiKangResult<List<DeleteOrgResult>> batchDeleteOrg(BatchDeleteOrgQuery batchDeleteOrgQuery) {
        return invocationUtils.post(UrlConstants.BATCH_DELETE_ORG, batchDeleteOrgQuery,
                new TypeToken<HaiKangResult<List<DeleteOrgResult>>>(){});
    }

    @Override
    public HaiKangResult<String> updateOrg(UpdateOrgQuery updateOrgQuery) {
        return invocationUtils.post(UrlConstants.UPDATE_ORG, updateOrgQuery,
                new TypeToken<HaiKangResult<String>>(){});
    }

    @Override
    public void addOrgIfNotExist(HaiKangOrg haiKangOrg) {
        // 直接更新
        UpdateOrgQuery updateOrgQuery = new UpdateOrgQuery();
        BeanUtil.copyProperties(haiKangOrg, updateOrgQuery);
        HaiKangResult<String> updateResult = null;
        try {
            updateResult = updateOrg(updateOrgQuery);
        } catch (Exception e) {
            log.info("更新组织失败，尝试插入，失败报错：", e);
        }
        // 更新失败则插入
        HaiKangResult<BatchAddOrgResult> addResult = null;
        try {
            if (ObjectUtil.isEmpty(updateResult) || "success".equals(updateResult.getMsg())) {
                log.info("更新组织失败，尝试插入...");
                addResult = batchAddOrg(ListUtil.of(haiKangOrg));
            }
        } catch (Exception e) {
            log.error("插入组织失败，失败报错：", e);
            throw new RuntimeException("插入组织失败");
        }
        if (ObjectUtil.isNull(updateResult) && ObjectUtil.isNull(addResult)) {
            log.error("海康接口调用返回为空");
            throw new BaseException(500, "海康接口调用返回为空！");
        }
    }

}
