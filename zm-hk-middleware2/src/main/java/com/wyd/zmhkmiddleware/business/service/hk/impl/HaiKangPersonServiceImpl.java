package com.wyd.zmhkmiddleware.business.service.hk.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.gson.reflect.TypeToken;
import com.wyd.zmhkmiddleware.business.model.hk.query.BatchDeletePersonQuery;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangPerson;
import com.wyd.zmhkmiddleware.business.model.hk.query.UpdatePersonQuery;
import com.wyd.zmhkmiddleware.business.model.hk.result.BatchAddPersonResult;
import com.wyd.zmhkmiddleware.business.model.hk.result.DeletePersonResult;
import com.wyd.zmhkmiddleware.business.model.hk.result.HaiKangResult;
import com.wyd.zmhkmiddleware.business.service.hk.HaiKangPersonService;
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
@Slf4j
@Service
public class HaiKangPersonServiceImpl implements HaiKangPersonService {

    @Resource
    private HaiKangInvocationUtils invocationUtils;

    @Override
    public HaiKangResult<BatchAddPersonResult> batchAddPerson(List<HaiKangPerson> persons) {
        HaiKangResult<BatchAddPersonResult> result = invocationUtils.post(UrlConstants.BATCH_ADD_PERSON, persons,
                new TypeToken<HaiKangResult<BatchAddPersonResult>>() {
                });
        if (ObjectUtil.isNull(result)) {
            log.error("调用海康接口无返回");
            throw new BaseException(500, "调用海康接口无返回");
        }
        return result;
    }

    @Override
    public HaiKangResult<List<DeletePersonResult>> batchDeletePerson(BatchDeletePersonQuery batchDeletePersonQuery) {
        return invocationUtils.post(UrlConstants.BATCH_DELETE_PERSON, batchDeletePersonQuery,
                new TypeToken<HaiKangResult<List<DeletePersonResult>>>(){});
    }

    @Override
    public HaiKangResult<String> updatePerson(UpdatePersonQuery updatePersonQuery) {
        return invocationUtils.post(UrlConstants.UPDATE_PERSON, updatePersonQuery,
                new TypeToken<HaiKangResult<String>>(){});
    }

    @Override
    public void updatePerson(HaiKangPerson hkPerson) {
        UpdatePersonQuery updatePersonQuery = new UpdatePersonQuery();
        BeanUtil.copyProperties(hkPerson, updatePersonQuery);
        updatePerson(updatePersonQuery);
    }

    @Override
    public void addPerson(HaiKangPerson hkPerson) {
        batchAddPerson(ListUtil.of(hkPerson));
    }


}
