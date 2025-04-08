package com.wyd.asset_manage.entity.eq;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.wyd.asset_manage.entity.eq.proxy.RoleEntityProxy;
import lombok.Data;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.EntityFileProxy;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * 实体类。
 *
 * @author wyd
 * @since 1.0
 */
@Data
@Table(value = "sys_role")
@EntityFileProxy
@FieldNameConstants
public class RoleEntity implements ProxyEntityAvailable<RoleEntity , RoleEntityProxy> {

    /**
     * 主键
     */
    @Column(primaryKey = true, value = "id")
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 所属法院
     */
    private String courtCode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    private Integer delMark;

    /**
     * 是否超级管理员，0否，1是
     */
    private Integer adminMark;


}
