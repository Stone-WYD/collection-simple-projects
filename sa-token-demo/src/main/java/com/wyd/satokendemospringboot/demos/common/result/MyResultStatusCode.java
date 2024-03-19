package com.wyd.satokendemospringboot.demos.common.result;

public enum MyResultStatusCode {
    SUCCESS(0, "操作成功"),
    ERROR(500, "服务器内部错误"),
    BUSSINESS_ERROR(600, "业务上存在问题"),
    DATABASE_ERROR(700, "数据库异常"),
    REQUEST_AUTH_FAILED(1000, "请求参数校验不通过");

    private int code;
    private String name;

    private MyResultStatusCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
