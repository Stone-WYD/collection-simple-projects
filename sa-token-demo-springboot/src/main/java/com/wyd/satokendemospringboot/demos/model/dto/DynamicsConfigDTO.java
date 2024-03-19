package com.wyd.satokendemospringboot.demos.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: sa-token-demo-springboot
 * @description: DynamicsConfig实体类DTO，考虑到有可能要分层展示才增加此类
 * @author: Stone
 * @create: 2023-07-19 11:56
 **/
@Data
public class DynamicsConfigDTO implements Serializable{

    //主键
    private Integer id;
    //配置名
    private String name;
    //配置最后一段英文名
    private String code;
    //配置完整code，即在配置文件中的路径
    private String completeCode;
    //配置值
    private String value;
    //配置含义描述
    private String commentary;

}
