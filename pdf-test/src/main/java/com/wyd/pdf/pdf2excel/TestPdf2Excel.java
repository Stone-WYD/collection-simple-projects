package com.wyd.pdf.pdf2excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.wyd.pdf.pdf2excel.entity.BomExcelEntity;
import com.wyd.pdf.pdf2excel.impl.XzDrawInstanceBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestPdf2Excel {

    public static void main(String[] args) {

        Set<DrawInstance> roots = new XzDrawInstanceBuilder()
                .build("D:\\Tencent\\xwechat_files\\wxid_jfqlvgtpx8ju22_57d4\\msg\\file\\2026-01\\PDF合并(1).pdf");

        // 将 root 转为 BomExcelEntity 列表
        List<BomExcelEntity> entities = convertToBomExcelEntities(roots);
        
        // 打印转换结果
        System.out.println("=== BomExcelEntity 列表 ===");
        for (BomExcelEntity entity : entities) {
            System.out.println("层级: " + entity.getLevel() + ", 零件号: " + entity.getPartNumber() + ", 工序: " + entity.getProcessString());
        }

        // excel 导出
        exportToLocal(entities, BomExcelEntity.class, "D:\\BOM1.xlsx");
    }

    private static List<BomExcelEntity> convertToBomExcelEntities(Set<DrawInstance> roots) {

        List<BomExcelEntity> result = new ArrayList<>();

        roots.forEach(r -> {
            List<BomExcelEntity> entities = new ArrayList<>();
            if (r != null) {
                inorderTraversal(r, "1", entities);
            }
            result.addAll(entities);
        });
        return result;
    }

    private static void inorderTraversal(DrawInstance instance, String currentLevel, List<BomExcelEntity> entities) {
        // 转换当前实例
        BomExcelEntity entity = new BomExcelEntity();
        entity.setLevel(currentLevel);
        entity.setPartNumber(instance.getDrawingNumber());
        entity.setProcessString(instance.getProcessString());
        entities.add(entity);

        // 递归处理子实例（中序遍历）
        if (instance.getChildren() != null && !instance.getChildren().isEmpty()) {
            List<DrawInstance> children = new ArrayList<>(instance.getChildren());
            for (int i = 0; i < children.size(); i++) {
                DrawInstance child = children.get(i);
                String childLevel = currentLevel + "." + (i + 1);
                inorderTraversal(child, childLevel, entities);
            }
        }
    }

    public static <T> void exportToLocal(List<T> data, Class<T> clazz, String filePath) {
        // 1. 创建文件父目录（如果不存在）
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        // 2. 使用EasyExcel写入文件
        EasyExcel.write(filePath, clazz)
                // 自动适配列宽（根据内容最长的单元格调整）
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 指定sheet名称
                .sheet("bom")
                // 写入数据
                .doWrite(data);

        System.out.println("Excel导出成功，文件路径：" + filePath);
    }

}
