package com.jgdsun.qms.model;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.jgdsun.qms.model.proxy.TQueueManageStatusEntityProxy;
import lombok.Data;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.EntityFileProxy;

import java.time.LocalDateTime;

/**
 * 设备状态 实体类。
 *
 * @author easy-query-plugin automatic generation
 * @since 1.0
 */
@Data
@Table(value = "t_queue_manage_status")
@EntityFileProxy
public class TQueueManageStatusEntity implements ProxyEntityAvailable<TQueueManageStatusEntity , TQueueManageStatusEntityProxy> {

    /**
     * id
     */
    @Column(primaryKey = true, value = "ID")
    private String id;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 添加时间
     */
    private LocalDateTime addtime;

    /**
     * 设备状态 0设备异常 1上线 2下线
     */
    private String status;


}
