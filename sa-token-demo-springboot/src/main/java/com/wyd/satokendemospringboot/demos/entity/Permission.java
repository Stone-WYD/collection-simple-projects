package com.wyd.satokendemospringboot.demos.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 不同角色对不同业务有默认的权限，如果对某个账户设置了权限，则按照账户权限为准(Permission)实体类
 *
 * @author makejava
 * @since 2023-05-22 17:48:47
 */
public class Permission implements Serializable {
    private static final long serialVersionUID = 584636310084429536L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 业务类型
     */
    private String business;
    /**
     * 操作权限集合
     */
    private String permissions;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人id
     */
    private Long createId;
    /**
     * 创建人
     */
    private String createName;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人id
     */
    private Long updateId;
    /**
     * 更新人
     */
    private String updateName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

}

