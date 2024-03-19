package com.wyd.satokendemospringboot.demos.entity.dto;

import com.wyd.satokendemospringboot.demos.constants.TestIEum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IEumContainerDTO implements Serializable {

    @ApiModelProperty(notes = "物品名，数据库查找条件", name = "testName")
    private String testName;

    @ApiModelProperty(notes = "枚举类，人员姓名", name = "testIEum")
    private TestIEum testIeum;

    @ApiModelProperty(notes = "物品数量", name = "num")
    private Integer num;

    @ApiModelProperty(notes = "物品单价", name = "price")
    private Double price;

}
