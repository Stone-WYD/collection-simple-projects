package com.wyd.satokendemospringboot.demos.constants;

public enum EnableEnum {

    ENABLE_ENUM(1, "可用"),
    BAN_ENUM(0, "禁用");

    private final Integer code;

    private final String name;

    EnableEnum(Integer code, String name) {
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
