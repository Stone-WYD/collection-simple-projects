package com.wyd.excel.model;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DynamicPrcessPriceData {

    @ExcelProperty(index = 1)
    private String drawingNo;

    @ExcelProperty(index = 2)
    private String drawingName;

    @ExcelProperty(index = 3)
    private Integer num;

    @ExcelProperty(index = 4)
    private String process1;

    @ExcelProperty(index = 5)
    private String process2;

    @ExcelProperty(index = 6)
    private String process3;

    @ExcelProperty(index = 7)
    private String process4;

    @ExcelProperty(index = 8)
    private String process5;

    @ExcelProperty(index = 9)
    private String process6;

    @ExcelProperty(index = 10)
    private String process7;

    @ExcelProperty(index = 11)
    private String process8;

    @ExcelProperty(index = 12)
    private String process9;

    @ExcelProperty(index = 13)
    private String process10;

    @ExcelProperty(index = 14)
    private String process11;

    @ExcelProperty(index = 15)
    private String process12;

    @ExcelProperty(index = 16)
    private String process13;

    @ExcelProperty(index = 17)
    private String process14;

    @ExcelProperty(index = 18)
    private String process15;
}
