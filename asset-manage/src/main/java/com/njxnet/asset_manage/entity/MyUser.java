package com.njxnet.asset_manage.entity;

import java.io.Serializable;

/**
 * (MyUser)实体类
 *
 * @author makejava
 * @since 2023-05-29 15:50:40
 */
public class MyUser implements Serializable {
    private static final long serialVersionUID = -23917536641675099L;
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 可用标识符
     */
    private Integer enable;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

}

