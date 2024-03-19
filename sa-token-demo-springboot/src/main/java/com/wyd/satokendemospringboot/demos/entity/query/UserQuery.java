package com.wyd.satokendemospringboot.demos.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class UserQuery implements Serializable {

    // 使用分组校验时，再使用swagger会对返回的接口传参说明有干扰

    @ApiModelProperty(notes = "用户名, 账号密码登录接口使用", name = "userName")
    @NotEmpty(message = "用户名不能为空", groups = {loginQuery.class})
    private String userName;

    @ApiModelProperty(notes = "用户密码, 账号密码登录接口使用", name = "password")
    @Length(min = 6, message = "密码请最少输入6位", groups = {loginQuery.class})
    private String password;

    @ApiModelProperty(notes = "电话号码, 手机号登录接口使用", name = "telephone")
    @Length(min = 11, max = 11, message = "手机号请输入11位", groups = {telephoneLoginQuery.class})
    private String telephone;

    @ApiModelProperty(notes = "验证码, 手机号登录接口使用", name = "code")
    @NotEmpty(message = "验证码不能为空", groups = {telephoneLoginQuery.class})
    private String code;

    public interface loginQuery{}

    public interface telephoneLoginQuery{}
}
