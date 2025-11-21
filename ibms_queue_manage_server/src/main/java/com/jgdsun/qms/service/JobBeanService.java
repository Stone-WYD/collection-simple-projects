package com.jgdsun.qms.service;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author Stone
 * @since 2025-10-17
 */
@Component
public class JobBeanService {

    @Inject
    private Ims4HandlerService ims4Service;

    private boolean firstFlag = true;

    // 5 min 调用一次
    @Scheduled(fixedRate = 1000 * 60 * 5)
    // @Scheduled(fixedRate = 1000 * 1)
    public void remoteJob() {
        ims4Service.initDb();
        // 第一次启动或者每天0点调用一次
        if (firstFlag || checkTime(LocalDateTime.now())) {
            System.out.println("===================");
            ims4Service.initDevice();
            firstFlag = false;
        }

        // 5 min 调用一次，更新状态
        ims4Service.updateDeviceStatus();
    }

    private boolean checkTime(LocalDateTime now) {
        // 每天0点调用一次
        return now.getHour() == 0 && now.getMinute() == 0;
    }


}
