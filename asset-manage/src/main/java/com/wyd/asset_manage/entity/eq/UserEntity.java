package com.wyd.asset_manage.entity.eq;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.wyd.asset_manage.entity.eq.proxy.UserEntityProxy;
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
@Table(value = "sys_user")
@EntityFileProxy
@FieldNameConstants
public class UserEntity implements ProxyEntityAvailable<UserEntity , UserEntityProxy> {

    /**
     * 主键
     */
    @Column(primaryKey = true, value = "id")
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 所属法院
     */
    private String courtCode;

    /**
     * 角色标识
     */
    private String roleId;

    /**
     * 状态（0-冻结，1-可用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记 0-删除 1-未删除
     */
    private Integer delMark;

    /**
     * 用户角色
     */
    @Navigate(value = RelationTypeEnum.OneToOne,
            selfProperty = {UserEntity.Fields.roleId},
            targetProperty = {RoleEntity.Fields.id})
    private RoleEntity roleEntity;
}
