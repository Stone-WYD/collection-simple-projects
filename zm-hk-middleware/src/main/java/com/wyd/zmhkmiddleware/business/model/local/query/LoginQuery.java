package com.wyd.zmhkmiddleware.business.model.local.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;


@Data
public class LoginQuery {

    @ApiModelProperty(notes = "用户名", name = "username", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(notes = "密码", name = "password", required = true)
    @NotBlank(message = "用户名不能为空")
    private String password;

    @ApiModelProperty(notes = "验证码key", name = "captchaKey", required = true)
    @NotBlank(message = "验证码key不能为空")
    private String captchaKey;

    @ApiModelProperty(notes = "验证码", name = "captchaValue", required = true)
    @NotBlank(message = "验证码不能为空")
    private String captchaValue;
}
