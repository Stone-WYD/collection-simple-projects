package com.jgdsun.ba.bootstrap;

import com.jgdsun.ba.bootstrap.business.BaSyncBasicInfoService;
import com.jgdsun.ba.bootstrap.business.BaSyncRecordsInfoService;
import com.jgdsun.ba.bootstrap.business.BaSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author xh
 * @date 2025-04-08
 * @Description:
 */
@Slf4j
@Component
public class BootstrapService {

    // 是否刷入巡更人员、点位、设备信息
    @Value("${init.device}")
    private boolean initFlag;

    @Value("${init.device-refresh}")
    private boolean refreshFlag;

    @Resource
    private BaSyncService baSyncService;

    @PostConstruct
    public void init() {
        // 根据配置文件判断是否刷新数据库
        if (initFlag) {
            log.info("开始执行ba设备信息同步任务...");
            baSyncService.syncBasicInfo();
            log.info("ba设备信息同步任务完成");
        }
    }

    // 定时任务刷新ba设备信息
    @Scheduled(cron = "${init.ba-device-cron}")
    public void syncBaBasicInfoTask() {
        try {
            if (refreshFlag) {
                log.info("开始执行ba设备信息同步任务...");
                baSyncService.syncBasicInfo();
                log.info("ba设备信息同步任务完成");
            }
        } catch (Exception e) {
            log.error("ba设备信息同步失败", e);
        }
    }


    // 定时任务刷新ba设备状态信息
    @Scheduled(cron = "${init.ba-value-cron}")
    public void syncBaBasicRecordsInfoTask() {
        log.info("开始执行ba设备数据同步任务...");
        try {
            baSyncService.syncBaBasicRecordsInfo();
            log.info("ba设备数据同步任务完成");
        } catch (Exception e) {
            log.error("ba设备数据同步任务同步失败", e);
        }
    }

}
