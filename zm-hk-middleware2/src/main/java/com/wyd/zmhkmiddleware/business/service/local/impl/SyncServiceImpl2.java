package com.wyd.zmhkmiddleware.business.service.local.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wyd.zmhkmiddleware.business.mapper.EtEmplBasicMapper;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangOrg;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangPerson;
import com.wyd.zmhkmiddleware.business.model.local.dto.SyncInfoDTO;
import com.wyd.zmhkmiddleware.business.model.local.po.SynchronizationRecord;
import com.wyd.zmhkmiddleware.business.model.local.query.PersonQuery;
import com.wyd.zmhkmiddleware.business.service.hk.HaiKangOrgService;
import com.wyd.zmhkmiddleware.business.service.hk.HaiKangPersonService;
import com.wyd.zmhkmiddleware.business.service.local.SyncConvertService;
import com.wyd.zmhkmiddleware.business.service.local.SyncService;
import com.wyd.zmhkmiddleware.business.service.local.SynchronizationRecordService;
import com.wyd.zmhkmiddleware.common.AjaxCustomResult;
import com.wyd.zmhkmiddleware.common.AjaxResult;
import com.wyd.zmhkmiddleware.common.AjaxResultUtil;
import com.wyd.zmhkmiddleware.common.BaseException;
import com.wyd.zmhkmiddleware.common.enums.SyncRecordEnum;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wyd.zmhkmiddleware.common.enums.ResultStatusCode.ERROR;
import static com.wyd.zmhkmiddleware.common.enums.SyncRecordEnum.SYNC_STATUS_SUCCESS;

/**
 * @author Stone
 * @since 2025-06-10
 */
@Service
public class SyncServiceImpl2 implements SyncService {

    private EtEmplBasicMapper etEmplBasicMapper;

    private SynchronizationRecordService synchronizationRecordService;

    private SyncConvertService syncConvertService;

    private HaiKangPersonService haiKangPersonService;

    private HaiKangOrgService haiKangOrgService;

    public SyncServiceImpl2(EtEmplBasicMapper etEmplBasicMapper, SynchronizationRecordService synchronizationRecordService
    , SyncConvertService syncConvertService, HaiKangPersonService haiKangPersonService, HaiKangOrgService haiKangOrgService) {
        this.etEmplBasicMapper = etEmplBasicMapper;
        this.synchronizationRecordService = synchronizationRecordService;
        this.syncConvertService = syncConvertService;
        this.haiKangPersonService = haiKangPersonService;
        this.haiKangOrgService = haiKangOrgService;
    }

    @Override
    public AjaxCustomResult<List<SyncInfoDTO>> getPersonList(PersonQuery personQuery) {
        // 查出所有记录
        personQuery.setPage((personQuery.getPage() - 1) * personQuery.getLimit());
        List<SyncInfoDTO> list = etEmplBasicMapper.listSyncInfoDTO(personQuery);
        Long count = etEmplBasicMapper.countSyncInfoDTO(personQuery);
        AjaxCustomResult<List<SyncInfoDTO>> result = new AjaxCustomResult<>();
        result.setCount(count);
        result.setData(list);
        result.setMsg("操作成功！");
        result.setCode(0L);
        return result;
    }

    @Override
    public AjaxResult<Boolean> syncInfo(List<SyncInfoDTO> dtoList) {
        for (SyncInfoDTO syncInfoDTO : dtoList) {
            if (StrUtil.isEmpty(syncInfoDTO.getZhrempl())) {
                throw new BaseException(ERROR.getCode(), "人员编码不能为空！");
            }
            if (StrUtil.isEmpty(syncInfoDTO.getZhrpost())) {
                throw new BaseException(ERROR.getCode(), "岗位信息不能为空！");
            }
            if (StrUtil.isEmpty(syncInfoDTO.getZpsOrg())) {
                throw new BaseException(ERROR.getCode(), "组织信息不能为空！");
            }
        }

        Map<String, SyncInfoDTO> extend1ToDTO = dtoList.stream().collect(Collectors.toMap(p -> {
            String extend1 = p.getZhrempl() + p.getZhrpost() + p.getZpsOrg();
            if (extend1.length() > 49) {
                extend1 = extend1.substring(0, 49);
            }
            return extend1;
        }, p -> p));
        Set<String> extend1s = extend1ToDTO.keySet();
        LambdaQueryWrapper<SynchronizationRecord> query = new LambdaQueryWrapper<>();
        query.in(SynchronizationRecord::getExtend1, extend1s);
        List<SynchronizationRecord> list = synchronizationRecordService.list(query);
        list.forEach(record -> extend1ToDTO.remove(record.getExtend1()));

        extend1ToDTO.forEach((extend1, syncInfoDTO) -> {
            HaiKangPerson hkPerson = syncConvertService.convert2HKPerson2(syncInfoDTO, extend1);
            HaiKangOrg haiKangOrg = syncConvertService.convert2HKOrg(syncInfoDTO.getZpsOrg());
            haiKangOrgService.addOrgIfNotExist(haiKangOrg);
            // 新增人员
            haiKangPersonService.addPerson(hkPerson);
            SynchronizationRecord record = new SynchronizationRecord();
            record.setType(SyncRecordEnum.SYNC_TYPE_PERSON.getCode());
            record.setBussinessId(syncInfoDTO.getZhrempl());
            record.setExtend1(extend1);
            record.setExtend2(syncInfoDTO.getZhrpost());
            record.setExtend3(syncInfoDTO.getZpsOrg());
            record.setSyncDate(DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
            record.setSyncStatus(SYNC_STATUS_SUCCESS.getCode());
            record.setSyncPerson(syncInfoDTO.getZemplnm());
            synchronizationRecordService.save(record);
        });
        return AjaxResultUtil.getTrueAjaxResult(true);
    }
}
