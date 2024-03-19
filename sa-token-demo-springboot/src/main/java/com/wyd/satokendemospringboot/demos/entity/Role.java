package com.wyd.satokendemospringboot.demos.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Role)实体类
 *
 * @author makejava
 * @since 2023-05-22 17:49:36
 */
public class Role implements Serializable {
    private static final long serialVersionUID = 299488766429558075L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色创建者
     */
    private String roleCreator;
    /**
     * 角色创建者id
     */
    private Long roleCreatorId;
    /**
     * 角色创建时间
     */
    private Date roleCreateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCreator() {
        return roleCreator;
    }

    public void setRoleCreator(String roleCreator) {
        this.roleCreator = roleCreator;
    }

    public Long getRoleCreatorId() {
        return roleCreatorId;
    }

    public void setRoleCreatorId(Long roleCreatorId) {
        this.roleCreatorId = roleCreatorId;
    }

    public Date getRoleCreateTime() {
        return roleCreateTime;
    }

    public void setRoleCreateTime(Date roleCreateTime) {
        this.roleCreateTime = roleCreateTime;
    }

}

