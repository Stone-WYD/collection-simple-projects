package com.jgdsun.qms.model;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.jgdsun.qms.model.proxy.TQueueManageEntityProxy;
import lombok.Data;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.EntityFileProxy;

import java.time.LocalDateTime;

/**
 * 叫号系统 实体类。
 *
 * @author easy-query-plugin automatic generation
 * @since 1.0
 */
@Data
@Table(value = "t_queue_manage")
@EntityFileProxy
public class TQueueManageEntity implements ProxyEntityAvailable<TQueueManageEntity , TQueueManageEntityProxy> {

    /**
     * id
     */
    @Column(primaryKey = true, value = "ID")
    private String id;

    /**
     * 楼层ID
     */
    private String storeyId;

    /**
     * 名称
     */
    private String name;

    /**
     * 通讯协议 ,0 modbus RTU, 1 tcp/ip , 2 http .....
     */
    private Integer protocol;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 设备通讯地址，通讯中的设备标识
     */
    private String address;

    /**
     * 寄存器地址
     */
    private Integer registerAddress;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 效果图坐标X
     */
    private Integer positionX;

    /**
     * 效果图坐标Y
     */
    private Integer positionY;

    /**
     * 添加时间
     */
    private LocalDateTime addtime;

    /**
     * 是否启用0不启用1启用
     */
    private String enable;

    /**
     * 设备id
     */
    private String equipmentId;

    /**
     * 设备序号
     */
    private String equipmentSn;

    /**
     * 状态 0下线 1后台 2运行中
     */
    private String status;

    /**
     * 设备类型
     */
    private String typeinfo;

    private String stat;

    /**
     * 效果图坐标Z 三维时单位为0.01
     */
    private Integer positionZ;

    /**
     * x轴旋转 单位为0.01
     */
    private Integer rotationX;

    /**
     * Y轴旋转 单位为0.01
     */
    private Integer rotationY;

    /**
     * Z轴旋转 单位为0.01
     */
    private Integer rotationZ;


}
