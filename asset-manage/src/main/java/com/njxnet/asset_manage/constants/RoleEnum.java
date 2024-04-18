package com.njxnet.asset_manage.constants;

/**
 * @program: asset-manage
 * @description: 角色枚举类
 * @author: Stone
 * @create: 2024-04-15 13:35
 **/
public enum RoleEnum {

    ADMIN(0, "管理员"),
    USER(1, "普通用户");

    private final Integer code;

    private final String name;

    RoleEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

