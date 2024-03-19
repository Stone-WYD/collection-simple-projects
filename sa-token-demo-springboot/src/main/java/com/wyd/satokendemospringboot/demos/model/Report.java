package com.wyd.satokendemospringboot.demos.model;

import lombok.Data;

import java.util.List;

/**
 * @program: sa-token-demo-springboot
 * @description: 二维码下载风险评估报告相关实体类
 * @author: Stone
 * @create: 2023-08-30 14:04
 **/
@Data
public class Report {

    private String name;

    private List<String> contents;
}
