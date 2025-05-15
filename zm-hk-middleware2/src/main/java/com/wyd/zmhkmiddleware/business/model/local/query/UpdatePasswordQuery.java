package com.wyd.zmhkmiddleware.business.model.local.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class UpdatePasswordQuery {

    @ApiModelProperty(notes = "密码", name = "password", required = true)
    @NotBlank(message = "用户名不能为空")
    private String oldPassword;

    @ApiModelProperty(notes = "新密码", name = "newPassword", required = true)
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
