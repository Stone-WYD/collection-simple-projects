package com.wyd.zmhkmiddleware;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyd.zmhkmiddleware.business.model.local.po.EtEmplBasic;
import com.wyd.zmhkmiddleware.business.model.local.po.SynchronizationRecord;
import com.wyd.zmhkmiddleware.business.model.local.po.User;
import com.wyd.zmhkmiddleware.business.model.local.query.PersonQuery;
import com.wyd.zmhkmiddleware.business.service.local.EtEmplBasicService;
import com.wyd.zmhkmiddleware.business.service.local.SynchronizationRecordService;
import com.wyd.zmhkmiddleware.business.service.local.UserService;
import com.wyd.zmhkmiddleware.common.AjaxCustomResult;
import com.wyd.zmhkmiddleware.common.enums.SyncRecordEnum;
import com.wyd.zmhkmiddleware.util.LoginUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ZmHkMiddlewareApplicationTests {

    @Resource
    private EtEmplBasicService etEmplBasicService;

    @Resource
    private UserService userService;

    @Resource
    private SynchronizationRecordService synchronizationRecordService;

    @Resource
    private LoginUtils loginTokenUtils;

    @Test
    void contextLoads() {

        List<EtEmplBasic> list = etEmplBasicService.list();
        for (EtEmplBasic etEmplBasic : list) {
            System.out.println(etEmplBasic);
        }

    }

    @Test
    void testUser() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        List<User> users = userService.listByIds(ids);
        for (User user : users) {
            System.out.println(user);
        }

        List<SynchronizationRecord> records = synchronizationRecordService.listByIds(ids);
        for (SynchronizationRecord record : records) {
            System.out.println(record);
        }
    }

    @Test
    void testCache() throws InterruptedException {
        String wyd = loginTokenUtils.generageLoginToken("wyd");
        Thread.sleep(4000);
        System.out.println(loginTokenUtils.checkToken(wyd));
    }

    @Test
    void testCache2() throws InterruptedException {
        String wyd = loginTokenUtils.generageLoginToken("wyd");
        Thread.sleep(6000);
        System.out.println(loginTokenUtils.checkToken(wyd));
        Thread.sleep(4000);
        System.out.println(loginTokenUtils.checkToken(wyd));
    }

    @Test
    void testMyBatisForSqlite() {
        Page<EtEmplBasic> page = etEmplBasicService.page(new Page<>(1, 3));
        for (EtEmplBasic etEmplBasic : page.getRecords()) {
            System.out.println(etEmplBasic);
        }
    }

    @Test
    void testPageForSync() {
        PersonQuery personQuery = new PersonQuery();
        personQuery.setPage(1);
        personQuery.setLimit(3);
        personQuery.setSyncFlag(String.valueOf(SyncRecordEnum.SYNC_STATUS_FAIL.getCode()));
        AjaxCustomResult<List<EtEmplBasic>> result = etEmplBasicService.getPersonListWithQuery(personQuery);
        System.out.println(result);

    }

}
