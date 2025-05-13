package com.wyd.zmhkmiddleware.business.service.hk;

import com.wyd.zmhkmiddleware.business.model.hk.query.BatchDeleteOrgQuery;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangOrg;
import com.wyd.zmhkmiddleware.business.model.hk.query.UpdateOrgQuery;
import com.wyd.zmhkmiddleware.business.model.hk.result.BatchAddOrgResult;
import com.wyd.zmhkmiddleware.business.model.hk.result.DeleteOrgResult;
import com.wyd.zmhkmiddleware.business.model.hk.result.HaiKangResult;

import java.util.List;

/**
 * @author xh
 * @date 2025-03-18
 * @Description:
 */
public interface HaiKangOrgService {

    // 批量添加组织
    HaiKangResult<BatchAddOrgResult> batchAddOrg(List<HaiKangOrg> orgs);
    // 批量删除组织
    HaiKangResult<List<DeleteOrgResult>> batchDeleteOrg(BatchDeleteOrgQuery batchDeleteOrgQuery);
    // 修改组织
    HaiKangResult<String> updateOrg(UpdateOrgQuery updateOrgQuery);

    void addOrgIfNotExist(HaiKangOrg haiKangOrg);

    // todo 不一定需要获取组织


}
