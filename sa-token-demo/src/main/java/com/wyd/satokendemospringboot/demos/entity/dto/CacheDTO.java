package com.wyd.satokendemospringboot.demos.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
public class CacheDTO {

    @ApiModelProperty(notes = "用户id", name = "name")
    private String name;

    @ApiModelProperty(notes = "用户密码", name = "password")
    private String password;

    @ApiModelProperty(notes = "缓存更新时间", name = "updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone ="GMT+8")
    private Date updateTime;
}
