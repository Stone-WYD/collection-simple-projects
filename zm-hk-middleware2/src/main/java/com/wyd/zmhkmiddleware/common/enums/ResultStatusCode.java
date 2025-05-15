package com.wyd.zmhkmiddleware.common.enums;

import lombok.Getter;

/**
 * 返回状态码枚举
 */
@Getter
public enum ResultStatusCode {
    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败"),
    ERROR(500, "服务器内部错误"),
    TOKEN_NOT_FOUND(1000,"用户token信息不存在"),
    UNKNOWN_FILE(1011, "未知文件"),
    FILE_UPLOAD_FAIL(1012, "文件上传失败"),
    CAPTCHA_ERROR(2001, "验证码输入错误"),
    LOGIN_ERROR(2002, "用户名或者密码错误"),
    PASSWORD_ERROR(2006, "密码错误"),
    NO_RIGHT_TO_DO_IT(2003, "对不起，您没有权限执行本操作"),
    LOGIN_FREEZE(2004,"账号已被冻结"),
    MISSING_PARAM(2005, "参数缺失"),
    ;


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
