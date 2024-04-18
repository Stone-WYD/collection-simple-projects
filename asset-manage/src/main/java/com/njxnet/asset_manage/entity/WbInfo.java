package com.njxnet.asset_manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program: asset-manage
 * @description: 维保信息
 * @author: Stone
 * @create: 2024-04-16 14:39
 **/
@Data
public class WbInfo {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Long id;

    /**
     * asset表的id
     */
    private Integer assetId;

    /**
     * 接收人id
     */
    private Long receiveId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 接收时间
     */
    private LocalDateTime receiveTime;

    /**
     * 备注
     */
    private String back;

    /**
     * 预留字段
     */
    private String extend;

    /**
     * 维保记录
     */
    private String info;

    @TableField(exist = false)
    private String receiveName;

}

