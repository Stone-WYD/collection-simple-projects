package com.wyd.satokendemospringboot.demos.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class MyUserDTO implements Serializable {

    @ApiModelProperty(notes = "用户id", name = "id")
    private Long id;

    @ApiModelProperty(notes = "用户名", name = "userName")
    private String userName;

    @ApiModelProperty(notes = "用户年龄", name = "age")
    private Integer age;
}
