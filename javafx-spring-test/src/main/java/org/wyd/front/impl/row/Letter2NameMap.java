package org.wyd.front.impl.row;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author xh
 * @date 2025-02-11
 * @Description: 获取标识符到名称的映射
 */
public class Letter2NameMap {

    private final Map<String, String> map = new HashMap<>();

    public Letter2NameMap(String filePath) {
        try {
            EasyExcel.read(filePath, ExcelData.class, new PageReadListener<ExcelData>(dataList -> {
                for (ExcelData excelData : dataList) {
                    map.put(excelData.getCode(), excelData.getName());
                }
            })).sheet(1).doRead();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public String getName(String code) {
        if (map.isEmpty()) return null;
        return map.get(code);
    }

    public Set<String> getAllCode() {
        return map.keySet();
    }

    @Data
    public static class ExcelData {
        private String code;
        private String name;
    }

}
