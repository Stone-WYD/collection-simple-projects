package com.wyd.zmhkmiddleware.business.model.hk.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Stone
 * @since 2025-06-18
 */
@Data
public class HaiKangDataResultContent implements Serializable {

    private String code;

    private String message;

    private String clientId;

}
