package org.wyd.util;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author Stone
 * @since 2025-05-24
 */
public class PartData {

    // 使用@ExcelProperty注解绑定列名
    @ExcelProperty("图号")
    private String figureNumber;

    // 必须提供getter/setter
    public String getFigureNumber() {
        return figureNumber;
    }

    public void setFigureNumber(String figureNumber) {
        this.figureNumber = figureNumber;
    }
}
