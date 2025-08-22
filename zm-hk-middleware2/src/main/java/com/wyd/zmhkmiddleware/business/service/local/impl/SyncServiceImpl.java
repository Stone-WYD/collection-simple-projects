package com.wyd.zmhkmiddleware.business.service.local.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangOrg;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangPerson;
import com.wyd.zmhkmiddleware.business.model.local.dto.SyncInfoDTO;
import com.wyd.zmhkmiddleware.business.model.local.po.EtEmplBasic;
import com.wyd.zmhkmiddleware.business.model.local.query.PersonQuery;
import com.wyd.zmhkmiddleware.business.service.hk.HaiKangOrgService;
import com.wyd.zmhkmiddleware.business.service.hk.HaiKangPersonService;
import com.wyd.zmhkmiddleware.business.service.local.EtEmplBasicService;
import com.wyd.zmhkmiddleware.business.service.local.SyncConvertService;
import com.wyd.zmhkmiddleware.business.service.local.SyncService;
import com.wyd.zmhkmiddleware.common.AjaxCustomResult;
import com.wyd.zmhkmiddleware.common.AjaxResult;
import com.wyd.zmhkmiddleware.common.AjaxResultUtil;
import com.wyd.zmhkmiddleware.common.enums.SyncRecordEnum;
import com.wyd.zmhkmiddleware.util.transactional.SyncRecordTransactionalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Stone
 * @since 2025-05-12
 */
// @Service
@Slf4j
public class SyncServiceImpl implements SyncService {

    private final EtEmplBasicService etEmplBasicService;

    private final SyncConvertService syncConvertService;

    private final SyncRecordTransactionalUtil syncTransactionUtil;

    private final HaiKangPersonService haiKangPersonService;

    private final HaiKangOrgService haiKangOrgService;

    public SyncServiceImpl(EtEmplBasicService etEmplBasicService, SyncConvertService syncConvertService, SyncRecordTransactionalUtil syncTransactionUtil
            , HaiKangPersonService haiKangPersonService, HaiKangOrgService haiKangOrgService) {
        this.etEmplBasicService = etEmplBasicService;
        this.syncConvertService = syncConvertService;
        this.syncTransactionUtil = syncTransactionUtil;
        this.haiKangPersonService = haiKangPersonService;
        this.haiKangOrgService = haiKangOrgService;
    }


    /**
     * 从中免传过来的数据中获取人员列表
     *
     * @param personQuery
     * @return
     */
    @Override
    public AjaxCustomResult<List<SyncInfoDTO>> getPersonList(PersonQuery personQuery) {
        // todo 目前只有是否同步这一个筛选条件，如果后期要加其他筛选条件，
        //  可以考虑先将人员数据全部获取存放到内存中，再根据条件进行筛选
        try {
            List<EtEmplBasic> basicList;
            Long count;
            if (StrUtil.isEmpty(personQuery.getSyncFlag())) {
                // 查询全部
                Page<EtEmplBasic> page = etEmplBasicService.page(new Page<>(personQuery.getPage(), personQuery.getLimit()));
                basicList = page.getRecords();
                count = page.getTotal();
            } else {
                // 根据同步条件查询
                AjaxCustomResult<List<EtEmplBasic>> personListWithQuery = etEmplBasicService.getPersonListWithQuery(personQuery);
                basicList = personListWithQuery.getData();
                count = personListWithQuery.getCount();
            }
            List<SyncInfoDTO> resultData = syncConvertService.getSyncInfoDTOs(basicList);
            AjaxCustomResult<List<SyncInfoDTO>> result = new AjaxCustomResult<>();
            result.setCount(count);
            result.setCode(0L);
            result.setMsg("操作成功！");
            result.setData(resultData);
            return result;
        } catch (Exception e) {
            log.error("获取人员信息失败！", e);
            AjaxCustomResult<List<SyncInfoDTO>> failResult = new AjaxCustomResult<>();
            failResult.setMsg("操作失败，详细内容请查看日志");
            return failResult;
        }
    }

    /**
     * 同步人员信息为主。如果发现人员还没有同步过组织，则新增组织，如果发现没有人员，则新增人员。
     * 如果人员和组织都已存在，则更新人员。不会删除任何数据。
     * */
    @Override
    public AjaxResult<Boolean> syncInfo(List<SyncInfoDTO> dtoList) {

        Set<String> personIds = dtoList.stream().map(SyncInfoDTO::getZhrempl).collect(Collectors.toSet());
        List<EtEmplBasic> list = etEmplBasicService.list(new LambdaQueryWrapper<EtEmplBasic>().in(EtEmplBasic::getZhrempl, personIds));
        Map<String, EtEmplBasic> basicMap = list.stream().collect(Collectors.toMap(EtEmplBasic::getZhrempl, p -> p));
        List<SyncInfoDTO> syncInfoDTOs = syncConvertService.getSyncInfoDTOs(list);

        // todo 考虑到取消同步后再加入的人员直接再使用 personId 加入海康平台可能会有问题，这里预留扩展处理，交由子类实现
        syncInfoDTOs = processSyncInfoDTOs(syncInfoDTOs);

        // 仅针对第一次同步或需要更新的人员：遍历同步信息，如果人员存在，则更新人员，如果人员不存在，则新增人员。
        for (SyncInfoDTO syncInfoDTO : syncInfoDTOs) {
            EtEmplBasic etEmplBasic = basicMap.get(syncInfoDTO.getZhrempl());
            HaiKangPerson hkPerson = syncConvertService.convert2HKPerson(syncInfoDTO, etEmplBasic);
            if (ObjectUtil.isNotEmpty(syncInfoDTO.getSyncFlag()) && syncInfoDTO.getSyncFlag().equals(SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode())) {
                syncTransactionUtil.syncRecordTransactional(syncInfoDTO.getZhrempl(), SyncRecordEnum.SYNC_TYPE_PERSON.getCode(),
                        () -> // 曾经同步过，则只更新人员
                            haiKangPersonService.updatePerson(hkPerson));
            } else {
                syncTransactionUtil.syncRecordTransactional(syncInfoDTO.getZhrempl(), SyncRecordEnum.SYNC_TYPE_PERSON.getCode(),
                        () -> {
                            // 组织同步
                            HaiKangOrg haiKangOrg = syncConvertService.convert2HKOrg(syncInfoDTO.getZpsOrg());
                            haiKangOrgService.addOrgIfNotExist(haiKangOrg);
                            // 新增人员
                            haiKangPersonService.addPerson(hkPerson);});
            }
        }
        return AjaxResultUtil.getTrueAjaxResult(true);
    }


    protected List<SyncInfoDTO> processSyncInfoDTOs(List<SyncInfoDTO> syncInfoDTOs) {
        return syncInfoDTOs;
    }

}
