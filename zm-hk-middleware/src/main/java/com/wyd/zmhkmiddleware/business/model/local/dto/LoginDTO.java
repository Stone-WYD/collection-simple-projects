package com.wyd.zmhkmiddleware.business.model.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Stone
 * @since 2025-05-12
 */
@Data
public class LoginDTO {

    @ApiModelProperty(notes = "用户token", name = "用户token")
    private String token;

}
