package com.wyd.zmhkmiddleware.business.service.local;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangOrg;
import com.wyd.zmhkmiddleware.business.model.hk.query.HaiKangPerson;
import com.wyd.zmhkmiddleware.business.model.local.dto.SyncInfoDTO;
import com.wyd.zmhkmiddleware.business.model.local.po.*;
import com.wyd.zmhkmiddleware.common.enums.SyncRecordEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Stone
 * @since 2025-05-13
 */
@Component
public class SyncConvertService {

    @Resource
    private EtEmplPostService etEmplPostService;

    @Resource
    private ItPostBasicService itPostBasicService;

    @Resource
    private ItOrgBasicService itOrgBasicService;

    @Resource
    private SynchronizationRecordService syncRecordService;

    @Value("${artemis.config.rootOrgId}")
    private String rootOrgId;


    public HaiKangPerson convert2HKPerson(SyncInfoDTO syncInfoDTO, EtEmplBasic etEmplBasic) {
        HaiKangPerson haiKangPerson = new HaiKangPerson();
        haiKangPerson.setPersonId(etEmplBasic.getZhrempl());
        haiKangPerson.setPersonName(etEmplBasic.getZemplnm());
        haiKangPerson.setPhoneNo(etEmplBasic.getZphoneno());
        haiKangPerson.setOrgIndexCode(syncInfoDTO.getZpsOrg());
        // 出生日期
        String gbdat = etEmplBasic.getGbdat();
        if (StrUtil.isNotEmpty(gbdat) && gbdat.length() == 8 ) {
            // 20050101 格式转为 2005-01-01
            try {
                String birth = gbdat.substring(0, 4) + "-"
                        + gbdat.substring(4, 6) + "-" + gbdat.substring(6, 8);
                haiKangPerson.setBirthday(birth);
            } catch (Exception e) {
                // 不做任何处理，不重要
            }
        }
        haiKangPerson.setEmail(syncInfoDTO.getZemail());
        haiKangPerson.setCertificateNo(etEmplBasic.getZzjhm());
        return haiKangPerson;
    }

    public HaiKangPerson convert2HKPerson2(SyncInfoDTO syncInfoDTO, String personId) {
        HaiKangPerson haiKangPerson = new HaiKangPerson();
        haiKangPerson.setPersonId(personId);
        haiKangPerson.setPersonName(syncInfoDTO.getZemplnm());
        haiKangPerson.setPhoneNo(syncInfoDTO.getZphoneno());
        haiKangPerson.setOrgIndexCode(syncInfoDTO.getZpsOrg());
        // 出生日期
        String gbdat = syncInfoDTO.getGbdat();
        if (StrUtil.isNotEmpty(gbdat) && gbdat.length() == 8 ) {
            // 20050101 格式转为 2005-01-01
            try {
                String birth = gbdat.substring(0, 4) + "-"
                        + gbdat.substring(4, 6) + "-" + gbdat.substring(6, 8);
                haiKangPerson.setBirthday(birth);
            } catch (Exception e) {
                // 不做任何处理，不重要
            }
        }
        haiKangPerson.setEmail(syncInfoDTO.getZemail());
        haiKangPerson.setCertificateNo(syncInfoDTO.getZzjhm());
        return haiKangPerson;
    }

    public HaiKangOrg convert2HKOrg(String orgId) {
        LambdaQueryWrapper<ItOrgBasic> itOrgQueryWrapper = new LambdaQueryWrapper<>();
        itOrgQueryWrapper.eq(ItOrgBasic::getZhrorg, orgId);
        List<ItOrgBasic> orgList = itOrgBasicService.list(itOrgQueryWrapper);

        if (ObjectUtil.isNotEmpty(orgList)) {
            ItOrgBasic org = orgList.get(0);
            HaiKangOrg haiKangOrg = new HaiKangOrg();
            haiKangOrg.setOrgIndexCode(org.getZhrorg());
            haiKangOrg.setOrgName(org.getZorgnm());
            haiKangOrg.setParentIndexCode(rootOrgId);
            return haiKangOrg;
        }
        return null;
    }

    public List<SyncInfoDTO> getSyncInfoDTOs(List<EtEmplBasic> personBasicList) {
        Set<String> personIdList = personBasicList.stream().map(EtEmplBasic::getZhrempl).collect(Collectors.toSet());

        // 本次查询需要的岗位信息
        LambdaQueryWrapper<EtEmplPost> etEmplPostQuery = new LambdaQueryWrapper<>();
        List<EtEmplPost> postList = etEmplPostService.list(etEmplPostQuery.in(EtEmplPost::getZhrempl, personIdList));
        Map<String, EtEmplPost> postMap = postList.stream().collect(Collectors.toMap(EtEmplPost::getZhrempl, p -> p));

        // 本次查询需要的详细岗位信息
        Set<String> postIdList = postList.stream().map(EtEmplPost::getZhrpost).collect(Collectors.toSet());
        LambdaQueryWrapper<ItPostBasic> itPostBasicQuery = new LambdaQueryWrapper<>();
        List<ItPostBasic> postConcretelist = itPostBasicService.list(itPostBasicQuery.in(ItPostBasic::getZhrpost, postIdList));
        Map<String, ItPostBasic> postConcreteMap = postConcretelist.stream().collect(Collectors.toMap(ItPostBasic::getZhrpost, p -> p));

        // 本次查询需要的组织信息
        Set<String> orgList = postConcretelist.stream().map(ItPostBasic::getZpsOrg).collect(Collectors.toSet());
        LambdaQueryWrapper<ItOrgBasic> itOrgBasicQuery = new LambdaQueryWrapper<>();
        List<ItOrgBasic> orgConcretelist = itOrgBasicService.list(itOrgBasicQuery.in(ItOrgBasic::getZhrorg, orgList));
        Map<String, ItOrgBasic> orgConcreteMap = orgConcretelist.stream().collect(Collectors.toMap(ItOrgBasic::getZhrorg, p -> p));

        // 本次查询需要的同步信息
        LambdaQueryWrapper<SynchronizationRecord> synchronizationRecordQuery = new LambdaQueryWrapper<>();
        synchronizationRecordQuery.eq(SynchronizationRecord::getType, SyncRecordEnum.SYNC_TYPE_PERSON.getCode())
                .in(SynchronizationRecord::getBussinessId, personIdList);
        List<SynchronizationRecord> syncRecordList = syncRecordService.list(synchronizationRecordQuery);
        Map<String, SynchronizationRecord> syncRecordMap = syncRecordList.stream().collect(Collectors.toMap(SynchronizationRecord::getBussinessId, p -> p));

        // 拼装数据
        List<SyncInfoDTO> resultData = new ArrayList<>();
        personBasicList.forEach(
                p -> {
                    // 复制基础信息
                    SyncInfoDTO syncInfoDTO = new SyncInfoDTO();
                    resultData.add(syncInfoDTO);
                    BeanUtil.copyProperties(p, syncInfoDTO);
                    // 同步记录
                    SynchronizationRecord syncRecord = syncRecordMap.get(p.getZhrempl());
                    if (ObjectUtil.isNotEmpty(syncRecord) && ObjectUtil.isNotEmpty(syncRecord.getSyncStatus())
                            && syncRecord.getSyncStatus().equals(SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode())) {
                        syncInfoDTO.setSyncFlag(SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode());
                    } else {
                        syncInfoDTO.setSyncFlag(SyncRecordEnum.SYNC_STATUS_FAIL.getCode());
                    }
                    // 复制岗位信息
                    EtEmplPost post = postMap.get(p.getZhrempl());
                    if (ObjectUtil.isEmpty(post)) return;
                    BeanUtil.copyProperties(post, syncInfoDTO);
                    // 详细岗位信息
                    ItPostBasic postConcrete = postConcreteMap.get(post.getZhrpost());
                    if (ObjectUtil.isEmpty(postConcrete)) return;
                    BeanUtil.copyProperties(postConcrete, syncInfoDTO);
                    // 组织信息
                    ItOrgBasic orgConcrete = orgConcreteMap.get(postConcrete.getZpsOrg());
                    if (ObjectUtil.isEmpty(orgConcrete)) return;
                    BeanUtil.copyProperties(orgConcrete, syncInfoDTO);
                }
        );
        return resultData;
    }



}
