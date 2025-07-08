package com.wyd.zmhkmiddleware.business.service.local.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wyd.zmhkmiddleware.business.mapper.EtEmplBasicMapper;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangOrg;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangPerson;
import com.wyd.zmhkmiddleware.business.model.hk.query.UpdateOrgQuery;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.wyd.zmhkmiddleware.common.enums.ResultStatusCode.ERROR;
import static com.wyd.zmhkmiddleware.common.enums.SyncRecordEnum.SYNC_STATUS_SUCCESS;

/**
 * @author Stone
 * @since 2025-06-10
 */
@Slf4j
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

        Map<String, SyncInfoDTO> jobNoToDTO = dtoList.stream().collect(Collectors.toMap(SyncInfoDTO::getZhrempl, p -> p));
        Set<String> jobSet = jobNoToDTO.keySet();
        LambdaQueryWrapper<SynchronizationRecord> qeuryWrapper = new LambdaQueryWrapper<>();
        qeuryWrapper.eq(SynchronizationRecord::getSyncStatus, SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode());
        List<SynchronizationRecord> allList = synchronizationRecordService.list(qeuryWrapper);
        // 查询是否有调岗的人员，调岗的人员曾经有同步成功的记录
        List<SynchronizationRecord> records = allList.stream().filter(p -> jobSet.contains(p.getExtend1())).collect(Collectors.toList());
        Map<String, SynchronizationRecord> jobNoToRecord = records.stream().collect(Collectors.toMap(SynchronizationRecord::getExtend1, p -> p));
        // 查询组织，避免每次都重复操作
        Set<String> orgSet = records.stream().map(SynchronizationRecord::getExtend2).collect(Collectors.toSet());

        jobNoToDTO.forEach((jobNo, syncInfoDTO) -> {
            HaiKangPerson hkPerson = syncConvertService.convert2HKPerson2(syncInfoDTO, jobNo);
            HaiKangOrg haiKangOrg = syncConvertService.convert2HKOrg(syncInfoDTO.getZpsOrg());
            if (ObjectUtil.isNotNull(jobNoToRecord.get(jobNo))) {
                // 不更新组织
                // 更新人员
                haiKangPersonService.updatePerson(hkPerson);
            } else {
                if (!orgSet.contains(syncInfoDTO.getZpsOrg())) {
                    try {
                        haiKangOrgService.batchAddOrg(Collections.singletonList(haiKangOrg));
                    } catch (Exception e) {
                        // 如果是因为组织已存在，这里应该继续向下执行，所以不抛出异常
                        log.error("插入组织失败，有可能是组织已存在！请翻看日志", e);
                    }
                }
                // 新增人员
                haiKangPersonService.addPerson(hkPerson);
                SynchronizationRecord record = new SynchronizationRecord();
                record.setType(SyncRecordEnum.SYNC_TYPE_PERSON.getCode());
                record.setBussinessId(syncInfoDTO.getZhrempl());
                record.setExtend1(jobNo);
                record.setExtend2(syncInfoDTO.getZpsOrg());
                record.setSyncDate(DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
                record.setSyncStatus(SYNC_STATUS_SUCCESS.getCode());
                record.setSyncPerson(syncInfoDTO.getZemplnm());
                synchronizationRecordService.save(record);
            }
        });
        return AjaxResultUtil.getTrueAjaxResult(true);
    }
}
