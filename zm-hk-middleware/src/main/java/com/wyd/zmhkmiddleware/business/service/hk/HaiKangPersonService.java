package com.wyd.zmhkmiddleware.business.service.hk;

import com.wyd.zmhkmiddleware.business.model.hk.query.BatchDeletePersonQuery;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangPerson;
import com.wyd.zmhkmiddleware.business.model.hk.query.UpdatePersonQuery;
import com.wyd.zmhkmiddleware.business.model.hk.result.BatchAddPersonResult;
import com.wyd.zmhkmiddleware.business.model.hk.result.DeletePersonResult;
import com.wyd.zmhkmiddleware.business.model.hk.result.HaiKangResult;

import java.util.List;

/**
 * @author xh
 * @date 2025-03-18
 * @Description:
 */
public interface HaiKangPersonService {

    // 批量添加人员
    HaiKangResult<BatchAddPersonResult> batchAddPerson(List<HaiKangPerson> persons);

    // 批量删除人员
    HaiKangResult<List<DeletePersonResult>> batchDeletePerson(BatchDeletePersonQuery batchDeletePersonQuery);

    // 修改人员
    HaiKangResult<String> updatePerson(UpdatePersonQuery updatePersonQuery);

    void updatePerson(HaiKangPerson hkPerson);

    void addPerson(HaiKangPerson hkPerson);

    // todo 不一定需要查询人员


}
