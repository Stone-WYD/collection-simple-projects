package com.wyd.pdf.pdf2excel.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class BomExcelEntity {

    // @ExcelProperty(value = "层级")
    @ExcelIgnore
    private String level;

    @ExcelProperty(value = "图号")
    private String partNumber;

    @ExcelProperty(value = "工序流程")
    private String processString;

}
