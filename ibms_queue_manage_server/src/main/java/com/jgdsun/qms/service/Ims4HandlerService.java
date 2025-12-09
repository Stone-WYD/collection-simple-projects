package com.jgdsun.qms.service;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.solon.annotation.Db;
import com.jgdsun.qms.model.TQueueManageEntity;
import com.jgdsun.qms.model.TQueueManageStatusEntity;
import com.jgdsun.qms.model.ims4.DeviceDetail;
import com.jgdsun.qms.model.ims4.IMS4Result;
import com.jgdsun.qms.model.ims4.detail.ListInfo;
import com.jgdsun.qms.model.ims4.dto.DeviceDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.noear.nami.annotation.NamiClient;
import org.noear.solon.annotation.Component;
import org.smartboot.http.common.utils.CollectionUtils;
import org.smartboot.http.common.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stone
 * @since 2025-10-17
 */
@Slf4j
@Component
public class Ims4HandlerService {

    @NamiClient
    private Ims4Service ims4Service;

    @Db("db1")
    private EasyEntityQuery eq;

    // 同步设备信息
    public void initDevice() {
        // 调用接口获取设备
        DeviceDetailDto dto = new DeviceDetailDto();
        dto.setNumber(1L);
        dto.setSize(300L);
        dto.setOrderBy("id desc");
        IMS4Result<DeviceDetail> result = null;
        try {
            result = ims4Service.searchByCondition(dto);
        } catch (Exception e) {
            log.error("未能调用三方接口，同步设备信息失败");
            return;
        }

        DeviceDetail deviceDetail = result.getData();
        List<ListInfo> list = deviceDetail.getList();
        List<TQueueManageEntity> entities = list.stream().map(listInfo -> {
            TQueueManageEntity entity = new TQueueManageEntity();
            entity.setId(listInfo.getId().toString());
            entity.setName(listInfo.getName());
            entity.setIp(listInfo.getIp());
            String status = listInfo.getStatus();
            if ("RUNNING".equals(status)) {
                entity.setStatus("2"); // 运行中
            } else if ("DORMANT".equals(status) || "BACKGROUND".equals(status)) {
                entity.setStatus("1"); // 休眠或后台
            } else if ("OFFLINE".equals(status)) {
                entity.setStatus("0"); // 离线
            }
            entity.setEquipmentId(listInfo.getMac());
            entity.setEquipmentSn(listInfo.getMac());
            return entity;
        }).collect(Collectors.toList());
        // 插入数据库数据
        List<TQueueManageEntity> dbData = eq.queryable(TQueueManageEntity.class).toList();
        if (CollectionUtils.isEmpty(dbData)) {
            eq.insertable(entities).batch().executeRows();
        } else {
            // 获取id数据
            Set<String> dbIdSet = dbData.stream().map(TQueueManageEntity::getId).collect(Collectors.toSet());
            Set<String> remoteIdSet = entities.stream().map(TQueueManageEntity::getId).collect(Collectors.toSet());
            // 找到需要新增的数据
            List<TQueueManageEntity> addData = entities.stream().filter(p -> !dbIdSet.contains(p.getId())).collect(Collectors.toList());
            // 需要删除的数据
            List<TQueueManageEntity> deleteData = dbData.stream().filter(p -> !remoteIdSet.contains(p.getId())).collect(Collectors.toList());
            // 进行数据库操作
            try (Transaction transaction = eq.beginTransaction()) {
                if (CollectionUtils.isNotEmpty(deleteData)) {
                    eq.deletable(TQueueManageEntity.class).disableLogicDelete()
                            .allowDeleteStatement(true)
                            .where(t -> t.id().in(deleteData.stream().map(TQueueManageEntity::getId).collect(Collectors.toList()))).executeRows();
                }
                if (CollectionUtils.isNotEmpty(addData)) {
                    eq.insertable(addData).batch().executeRows();
                }

                transaction.commit();
            }
        }
    }

    // 更新设备状态
    public void updateDeviceStatus() {
        try (Transaction transaction = eq.beginTransaction()) {
            long count = eq.queryable(TQueueManageStatusEntity.class).count();
            Set<String> runningIdSet;
            if (count == 0) {
                runningIdSet = new HashSet<>();
            } else {
                List<TQueueManageEntity> runnningList = eq.queryable(TQueueManageEntity.class).where(t -> t.status().eq("2")).select(t -> t.FETCHER.id().status()).toList();
                runningIdSet = runnningList.stream().map(TQueueManageEntity::getId).collect(Collectors.toSet());
            }
            doUpdate("RUNNING", runningIdSet);
            doUpdate("DORMANT", runningIdSet);
            doUpdate("OFFLINE", runningIdSet);
            doUpdate("BACKGROUND", runningIdSet);
            transaction.commit();
        }
    }

    // 初始化数据库
    public void initDb() {
        DatabaseCodeFirst databaseCodeFirst = eq.getDatabaseCodeFirst();

        //如果不存在数据库则创建
        databaseCodeFirst.createDatabaseIfNotExists();
        //自动同步数据库表
        if (!databaseCodeFirst.tableExists(TQueueManageEntity.class)) {
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(TQueueManageEntity.class, TQueueManageStatusEntity.class));
            //执行命令
            codeFirstCommand.executeWithTransaction(arg->{
                System.out.println(arg.getSQL());
                arg.commit();
            });
        }
    }

    private void doUpdate(String status, Set<String> runningIdSet) {
        System.out.println("============status:" + status);
        if (StringUtils.isEmpty(status)) {return;}
        IMS4Result<List<Integer>> result = ims4Service.searchIdsByCondition(status);
        List<Integer> ids = result.getData();
        if (CollectionUtils.isEmpty(ids)) { return;}
        List<String> idStrList = ids.stream().map(String::valueOf).collect(Collectors.toList());

        if ("RUNNING".equals(status)) {
            // 其他状态变成 RUNNING 上线
            List<TQueueManageStatusEntity> statusList = idStrList.stream().filter(id -> !runningIdSet.contains(id)).map(id -> {
                TQueueManageStatusEntity statusEntity = new TQueueManageStatusEntity();
                // 按时间戳生成id
                statusEntity.setId(String.valueOf(System.currentTimeMillis()) + id);
                statusEntity.setStatus("1");
                statusEntity.setDeviceId(String.valueOf(id));
                statusEntity.setAddtime(LocalDateTime.now());
                return statusEntity;
            }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(statusList)) {
                eq.insertable(statusList).batch().executeRows();
            }
        } else {
            // RUNNING 变成其他状态 下线
            List<TQueueManageStatusEntity> statusList = runningIdSet.stream().filter(id -> ids.contains(Integer.parseInt(id))).map(id -> {
                TQueueManageStatusEntity statusEntity = new TQueueManageStatusEntity();
                statusEntity.setId(System.currentTimeMillis() + id);
                statusEntity.setStatus("2");
                statusEntity.setDeviceId(id);
                statusEntity.setAddtime(LocalDateTime.now());
                return statusEntity;
            }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(statusList)) {
                eq.insertable(statusList).batch().executeRows();
            }
        }

        // 更新数据库数据
        switch (status) {
            case "RUNNING":
                eq.updatable(TQueueManageEntity.class).setColumns(t -> t.status().set("2")).where(t -> t.id().in(idStrList)).executeRows();
                break;
            case "DORMANT":
            case "BACKGROUND":
                eq.updatable(TQueueManageEntity.class).setColumns(t -> t.status().set("1")).where(t -> t.id().in(idStrList)).executeRows();
                break;
            case "OFFLINE":
                eq.updatable(TQueueManageEntity.class).setColumns(t -> t.status().set("0")).where(t -> t.id().in(idStrList)).executeRows();
                break;
        }
    }

}
