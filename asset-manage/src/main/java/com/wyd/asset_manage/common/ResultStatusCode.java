package com.wyd.asset_manage.common;

import lombok.Getter;

/**
 * 返回状态码枚举
 */
@Getter
public enum ResultStatusCode {
    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败"),
    ERROR(500, "服务器内部错误");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态信息
     */
    private final String name;

    ResultStatusCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
