package com.wyd.zmhkmiddleware.business.service.local.impl;

import com.wyd.zmhkmiddleware.business.model.local.po.EtEmplBasic;
import com.wyd.zmhkmiddleware.business.mapper.EtEmplBasicMapper;
import com.wyd.zmhkmiddleware.business.model.local.query.PersonQuery;
import com.wyd.zmhkmiddleware.business.service.local.EtEmplBasicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyd.zmhkmiddleware.common.AjaxCustomResult;
import com.wyd.zmhkmiddleware.common.enums.SyncRecordEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.wyd.zmhkmiddleware.common.enums.SyncRecordEnum.SYNC_TYPE_PERSON;

/**
 * <p>
 * 员工基本信息 服务实现类
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
@Service
public class EtEmplBasicServiceImpl extends ServiceImpl<EtEmplBasicMapper, EtEmplBasic> implements EtEmplBasicService {

    @Resource
    private EtEmplBasicMapper etEmplBasicMapper;

    @Override
    public AjaxCustomResult<List<EtEmplBasic>> getPersonListWithQuery(PersonQuery personQuery) {

        Long count;
        List<EtEmplBasic> data;
        if (SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode().equals(personQuery.getSyncFlag())) {
            count = etEmplBasicMapper.countSync(SYNC_TYPE_PERSON.getCode(), true, SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode());
            data = etEmplBasicMapper.listBySyncStatus(SYNC_TYPE_PERSON.getCode(), true, SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode(),
                    (personQuery.getPage() - 1) * personQuery.getLimit(), personQuery.getLimit());
        } else {
            count = etEmplBasicMapper.countSync(SYNC_TYPE_PERSON.getCode(), false, SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode());
            data = etEmplBasicMapper.listBySyncStatus(SYNC_TYPE_PERSON.getCode(), false, SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode(),
                    (personQuery.getPage() - 1) * personQuery.getLimit(), personQuery.getLimit());
        }

        AjaxCustomResult<List<EtEmplBasic>> result = new AjaxCustomResult<>();
        result.setCount(count);
        result.setCode(0L);
        result.setMsg("操作成功！");
        result.setData(data);
        return result;
    }
}
