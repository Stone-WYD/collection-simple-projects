package com.wyd.satokendemospringboot.demos.constants;

public enum OpsEnum {

    CREATE("c", "create"),
    READ("r","read"),
    UPDATE("u", "update"),
    DELETE("d", "delete"),
    ALL("*", "*");

    private final String code;

    private final String name;

    OpsEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
