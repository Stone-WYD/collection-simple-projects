package org.wyd.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;

import java.util.*;

/**
 * @author Stone
 * @since 2025-05-24
 */
public class ExcelUtil {

    public static Set<String> getPicNumFromExcel(String excelFile) {
        // 获取图号列的数据
        Set<String> figureNumbers = new HashSet<>();

        // 使用PageReadListener逐行处理数据
        EasyExcel.read(excelFile, PartData.class, new PageReadListener<PartData>(dataList -> {
            for (PartData data : dataList) {
                if (data.getFigureNumber() != null) {
                    figureNumbers.add(data.getFigureNumber());
                }
            }
        })).sheet().doRead(); // 默认读取第一个Sheet

        return figureNumbers;
    }

    public static void main(String[] args) {
        String excelFile = "C:\\Users\\Admin\\Desktop\\导出清单  5.24.xlsx";
        Set<String> figureNumbers = getPicNumFromExcel(excelFile);
        System.out.println(figureNumbers);
    }


}
