package com.wyd.zmhkmiddleware.common.enums;

import lombok.Getter;

/**
 * @author Stone
 * @since 2025-05-12
 */
@Getter
public enum SyncRecordEnum {
    SYNC_STATUS_SUCCESS(1, "同步成功"),
    SYNC_STATUS_FAIL(0, "同步失败"),

    SYNC_TYPE_PERSON(1, "同步人员");



    private final Integer code;
    private final String desc;

    SyncRecordEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
