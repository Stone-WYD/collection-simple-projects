package com.wyd.pdf.pdf2excel;

import com.wyd.pdf.pdf2excel.impl.XzDrawInstanceBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestPdfText {

    public static void main(String[] args) {
        Set<DrawInstance> roots = new XzDrawInstanceBuilder()
        .build("D:\\Tencent\\xwechat_files\\wxid_jfqlvgtpx8ju22_57d4\\msg\\file\\2026-01\\PDF合并.pdf");
        // 打印所有层级信息
        List<DrawInstance> rootList = new ArrayList<>(roots);
        DrawInstance drawInstance = rootList.get(0);
        if (drawInstance != null) {
            printHierarchy(drawInstance, 0);
        } else {
            System.out.println("无法解析PDF文件或未找到层级信息");
        }
    }

    private static void printHierarchy(DrawInstance instance, int level) {
        String indent = "  ".repeat(level);
        String prefix = level == 0 ? "" : "└─ ";
        boolean childFlag = instance.getChildren() != null && !instance.getChildren().isEmpty();

        String childrenPrefix = childFlag ? "" : "|";

        System.out.println(indent + prefix + "图号: " + instance.getDrawingNumber());
        System.out.println(indent + childrenPrefix + "  父级图号: " + (instance.getParentDrawingNumber() != null ? instance.getParentDrawingNumber() : "无"));
        System.out.println(indent + childrenPrefix +  "  工序流程: " + (instance.getProcessString() != null ? instance.getProcessString() : "无"));
        System.out.println(indent + childrenPrefix +  "  子实例数: " + (instance.getChildren() != null ? instance.getChildren().size() : 0));
        
        if (childFlag) {
            System.out.println(indent + "  子实例:");
            for (DrawInstance child : instance.getChildren()) {
                printHierarchy(child, level + 1);
            }
        }
        System.out.println(indent + childrenPrefix);
    }
}
