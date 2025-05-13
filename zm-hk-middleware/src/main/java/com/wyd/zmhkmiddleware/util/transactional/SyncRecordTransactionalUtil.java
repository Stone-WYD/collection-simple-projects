package com.wyd.zmhkmiddleware.util.transactional;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wyd.zmhkmiddleware.business.model.local.po.SynchronizationRecord;
import com.wyd.zmhkmiddleware.business.service.local.SynchronizationRecordService;
import com.wyd.zmhkmiddleware.common.enums.SyncRecordEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Stone
 * @since 2025-05-13
 */
@Slf4j
@Component
public class SyncRecordTransactionalUtil {

    @Resource
    private SynchronizationRecordService syncRecordService;

    public void syncRecordTransactional(String bussinessId, Integer type, TransactionalForSyncRecord transactionalForSyncRecord) {
        LambdaUpdateWrapper<SynchronizationRecord> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SynchronizationRecord::getSyncStatus, SyncRecordEnum.SYNC_STATUS_SUCCESS.getCode())
                .set(SynchronizationRecord::getSyncDate, DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN))
                .eq(SynchronizationRecord::getType, type)
                .eq(SynchronizationRecord::getBussinessId, bussinessId);
        syncRecordService.update(updateWrapper);
        try {
            transactionalForSyncRecord.execute();
        } catch (Exception e) {
            log.error("同步记录失败！", e);
            // 回滚操作
            updateWrapper.set(SynchronizationRecord::getSyncStatus, SyncRecordEnum.SYNC_STATUS_FAIL.getCode())
                    .set(SynchronizationRecord::getExtend1, e.getMessage())
                    .eq(SynchronizationRecord::getType, type)
                    .eq(SynchronizationRecord::getBussinessId, bussinessId);
            syncRecordService.update(updateWrapper);
            log.info("同步失败，同步记录表已回滚...");
            throw e;
        }
    }

    public void syncRecordTransactional(SynchronizationRecord syncRecord, TransactionalForSyncRecord transactionalForSyncRecord) {
        LambdaUpdateWrapper<SynchronizationRecord> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SynchronizationRecord::getBussinessId, syncRecord.getBussinessId())
                .eq(SynchronizationRecord::getType, syncRecord.getType());
        syncRecordService.update(syncRecord, updateWrapper);
        try {
            transactionalForSyncRecord.execute();
        } catch (Exception e) {
            log.error("同步记录失败！", e);
            // 回滚操作
            updateWrapper.set(SynchronizationRecord::getSyncStatus, SyncRecordEnum.SYNC_STATUS_FAIL.getCode())
                    .set(SynchronizationRecord::getExtend1, e.getMessage())
                    .eq(SynchronizationRecord::getType, syncRecord.getType())
                    .eq(SynchronizationRecord::getBussinessId, syncRecord.getBussinessId());
            syncRecordService.update(updateWrapper);
            log.info("同步失败，同步记录表已回滚...");
            throw e;
        }
    }

}
