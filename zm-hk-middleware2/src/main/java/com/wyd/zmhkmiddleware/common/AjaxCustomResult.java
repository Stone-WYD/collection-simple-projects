package com.wyd.zmhkmiddleware.common;

import lombok.Data;

/**
 * @author Stone
 * @since 2025-05-12
 */
@Data
public class AjaxCustomResult<T> {

    private T data;

    private Long code;

    private String msg;

    private Long count;

}
