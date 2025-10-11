package com.wyd.excel.write;

import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GenerateExcelModel {

    public static void main(String[] args) {
        // 源文件路径
        InputStream inputStream = GenerateExcelModel.class.getClassLoader().getResourceAsStream("报价模板-final.xlsx");
        // 输出文件路径
        String outputFilePath = "modified.xlsx";

        try {
            // 读取源Excel文件
            Workbook workbook = WorkbookFactory.create(inputStream);

            // 获取第一个工作表（索引从0开始）
            Sheet sheet = workbook.getSheetAt(0);

            // 修改单元格内容 - 不改变格式
            // 修改A1单元格（行0，列0）
            modifyCellContent(sheet, 3, 4, "激光");

            // 修改B3单元格（行2，列1）
            modifyCellContent(sheet, 3, 5, "测试1");

            // 修改D5单元格（行4，列3）
            modifyCellContent(sheet, 3, 6, "测试2");

            // 写入到新的Excel文件
            try (FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {
                workbook.write(outputStream);
            }

            // 关闭工作簿
            workbook.close();

            System.out.println("Excel文件修改完成，已保存至: " + outputFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改指定单元格的内容，但保持原有格式
     * @param sheet 工作表
     * @param rowIndex 行索引（从0开始）
     * @param cellIndex 列索引（从0开始）
     * @param newContent 新的单元格内容
     */
    private static void modifyCellContent(Sheet sheet, int rowIndex, int cellIndex, String newContent) {
        // 获取行，如果不存在则创建
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }

        // 获取单元格，如果不存在则创建
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }

        // 设置单元格内容，保留原有格式
        cell.setCellValue(newContent);
    }
}
