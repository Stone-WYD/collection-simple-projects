package com.wyd.excel.model;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class DynamicPrcessPriceData {

    @ExcelProperty(index = 1)
    private String drawingNo;

    @ExcelProperty(index = 2)
    private String drawingName;

    @ExcelProperty(index = 3)
    private Integer num;

    @ExcelProperty(index = 4)
    private BigDecimal process1;

    @ExcelProperty(index = 5)
    private BigDecimal process2;

    @ExcelProperty(index = 6)
    private BigDecimal process3;

    @ExcelProperty(index = 7)
    private BigDecimal process4;

    @ExcelProperty(index = 8)
    private BigDecimal process5;

    @ExcelProperty(index = 9)
    private BigDecimal process6;

    @ExcelProperty(index = 10)
    private Double process7;

    @ExcelProperty(index = 11)
    private Double process8;

    @ExcelProperty(index = 12)
    private BigDecimal process9;

    @ExcelProperty(index = 13)
    private BigDecimal process10;

    @ExcelProperty(index = 14)
    private BigDecimal process11;

    @ExcelProperty(index = 15)
    private BigDecimal process12;

    @ExcelProperty(index = 16)
    private BigDecimal process13;

    @ExcelProperty(index = 17)
    private BigDecimal process14;

    @ExcelProperty(index = 18)
    private BigDecimal process15;
}
