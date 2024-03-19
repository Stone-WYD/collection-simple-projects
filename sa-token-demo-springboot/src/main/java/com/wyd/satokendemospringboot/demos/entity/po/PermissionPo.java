package com.wyd.satokendemospringboot.demos.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionPo {

    private Long id;

    private String business;

    private Long roleId;

    private List<String> ops;
}
