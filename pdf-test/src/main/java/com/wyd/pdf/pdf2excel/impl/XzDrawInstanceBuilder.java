package com.wyd.pdf.pdf2excel.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.wyd.pdf.pdf2excel.DrawInstance;
import com.wyd.pdf.pdf2excel.DrawInstanceBuilder;

import cn.hutool.core.collection.CollectionUtil;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XzDrawInstanceBuilder extends DrawInstanceBuilder {

    @Override
    public List<String> getStringContentFromPDF(String pdfPath) {
        List<String> result = new ArrayList<>();
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            int totalPages = document.getNumberOfPages();
            PDFTextStripper stripper = new PDFTextStripper();
            for (int pageNum = 1; pageNum <= totalPages; pageNum++) {
                // 只提取当前页的文本
                stripper.setStartPage(pageNum);
                stripper.setEndPage(pageNum);
                String pageText = stripper.getText(document).trim();
                result.add(pageText);
            }
        } catch (Exception e) {
            throw new RuntimeException("从pdf中获取文字信息失败！");
        }
        return result;
    }


    // 当前具体实现：尽可能获取能获取的信息来创建DrawInstance
    @Override
    public List<DrawInstance> initInstances(List<String> contentList) {
        List<DrawInstance> result = new ArrayList<>();
        if (CollectionUtil.isEmpty(contentList)) {
            return result;
        }

        Map<String, DrawInstance> instanceMap = new HashMap<>();
        contentList.forEach(content -> {
            DrawInstance drawInstance = new DrawInstance();
            drawInstance.setOriginalText(content);
            // 文字“文件保存” 后固定行数有图号和图名称
            String[] lines = content.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String pageContent = lines[i].trim();
                if (pageContent.contains("文件保存")) {
                    String drawNumber = lines[i + 4].trim();
                    drawInstance.setDrawingNumber(drawNumber);
                    String drawName = lines[i + 5].trim();
                    drawInstance.setDrawingName(drawName);
                    if (instanceMap.get(drawNumber) != null) {
                        drawInstance = instanceMap.get(drawNumber);
                        String originalText = drawInstance.getOriginalText();
                        if (ObjectUtil.isNull(originalText)) {
                            drawInstance.setOriginalText(pageContent);
                        } else {
                            drawInstance.setOriginalText(originalText + "\n" + content);
                        }
                    } else {
                        instanceMap.put(drawNumber, drawInstance);
                        result.add(drawInstance);
                    }
                    break;
                }
            }

            // 尽可能获取可知工序信息
            if (CollectionUtil.isEmpty(drawInstance.getProcessSequence())) {
                Set<String> processSequence = new HashSet<>();
                drawInstance.setProcessSequence(processSequence);
            }
            Set<String> processSequence = drawInstance.getProcessSequence();
            for (String line : lines) {
                String pageContent = line.trim();
                if (StrUtil.isEmpty(pageContent)) {
                    continue;
                }

                if (pageContent.contains("归属图号")) {
                    // 示例 归属图号:SJ-5706-A
                    drawInstance.setParentDrawingNumber(line.split(":")[1].trim());
                }

                // 钳工
                if (pageContent.contains("攻牙")) {
                    processSequence.add("钳工");
                }
                // 有焊接表，则添加焊接
                if (pageContent.contains("项目号 零件号 厚度 外形尺寸 数量 材料")) {
                    processSequence.add("焊接");
                }
            }

            // 折弯
            if ((content.contains("上") || content.contains("下")) &&
                    (content.contains("°") && content.contains("R"))) {
                processSequence.add("折弯");
            }

            // 焊接件
            String drawingName = drawInstance.getDrawingName();
            if (StrUtil.isNotEmpty(drawingName) && drawingName.contains("焊接机架")) {
                processSequence.add("焊接");
                processSequence.add("龙门");
                processSequence.add("喷塑");
                processSequence.add("转组装");
            }
            if (StrUtil.isNotEmpty(drawingName) && drawingName.contains("门板焊接件")) {
                processSequence.add("焊接");
                processSequence.add("喷塑");
                processSequence.add("转组装");
            }

        });

        Set<DrawInstance> plus = new HashSet<>();
        result.forEach(instance -> {
            String parentDrawingNumber = instance.getParentDrawingNumber();
            if (ObjectUtil.isNull(instanceMap.get(parentDrawingNumber))) {
                DrawInstance drawInstance = new DrawInstance();
                drawInstance.setDrawingNumber(parentDrawingNumber);
                plus.add(drawInstance);
            }
        });
        if (CollectionUtil.isNotEmpty( plus)) {
            result.addAll(plus);
        }
        return result;
    }

    // 当前具体实现：通过parentDrawingNumber和焊接关系表得知
    @Override
    public Set<DrawInstance> buildHierarchy(List<DrawInstance> instances) {
        // todo 到这里了
        if (CollectionUtil.isEmpty(instances)) {
            return null;
        }
        // 先根据图号建立实例之间的关系
        Map<String, DrawInstance> instanceMap = new HashMap<>();
        instances.forEach(instance -> {
            instanceMap.put(instance.getDrawingNumber(), instance);
        });
        // 再根据parentDrawingNumber建立实例之间的关系
        instances.forEach(instance -> {
            String parentDrawingNumber = instance.getParentDrawingNumber();
            if (StrUtil.isEmpty(parentDrawingNumber)) return;

            if (instanceMap.containsKey(parentDrawingNumber)) {
                DrawInstance parent = instanceMap.get(parentDrawingNumber);
                instance.setParent(parent);
                // 初始化 children
                if (CollectionUtil.isEmpty(parent.getChildren())) {
                    parent.setChildren(new HashSet<>());
                }
                Set<DrawInstance> children = parent.getChildren();
                children.add(instance);
            }
        });
        // 根据焊接关系表确定父子关系
        instances.forEach(instance -> {
            String originalText = instance.getOriginalText();
            if (StrUtil.isEmpty(originalText)) return;
            if (originalText.contains("项目号 零件号 厚度 外形尺寸 数量 材料")) {
                // 解析焊接表，提取零件号
                String[] lines = originalText.split("\n");
                boolean foundTable = false;
                for (String line : lines) {
                    String trimmedLine = line.trim();
                    if (trimmedLine.contains("项目号 零件号 厚度 外形尺寸 数量 材料")) {
                        foundTable = true;
                        continue;
                    }
                    if (foundTable && trimmedLine.matches("\\d+\\s+[^\\s]+".concat(".*"))) {
                        // 提取零件号（第二个字段）
                        String[] parts = trimmedLine.split("\\s+");
                        if (parts.length >= 2) {
                            String partNumber = parts[1];
                            // 初始化 children
                            if (CollectionUtil.isEmpty(instance.getChildren())) {
                                instance.setChildren(new HashSet<>());
                            }
                            Set<DrawInstance> children = instance.getChildren();

                            // 获取子实例
                            DrawInstance child = instanceMap.get(partNumber);
                            if (child != null) {
                                // 建立父子关系
                                child.setParentDrawingNumber(instance.getDrawingNumber());
                                child.setParent(instance);
                                children.add(child);
                            } else {
                                // 没有图，说明是管子，特殊处理
                                DrawInstance pipeDrawInstance = pipeDrawInstance(partNumber, instance);
                                children.add(pipeDrawInstance);
                            }

                        }
                    }
                }
            }
        });
        // 查找root
        Set<DrawInstance> roots = new HashSet<>();
        for (DrawInstance instance : instances) {
            if (instance.getParentDrawingNumber() == null) {
                roots.add(instance);
            }
        }
        return roots;
    }

    // 当前具体实现：根据 攻牙、折弯线确定钳工、折弯工序，根据是否有焊接表确定焊接工序，根据是否有子物料确定下料工序
    @Override
    public void determineProcess(Set<DrawInstance> root) {
        if (root != null) {
            root.forEach(this::processInstance);
        }
    }

    private void processInstance(DrawInstance instance) {
        Set<String> processes = instance.getProcessSequence();
        if (processes == null) {
            processes = new HashSet<>();
            instance.setProcessSequence(processes);
        }

        // 确保有下料工序（如果没有子工序）
        if (instance.getChildren() == null || instance.getChildren().isEmpty()) {
            if (!processes.contains("切管")) {
                processes.add("下料");
            }
        }

        // 按顺序构建工序列表
        List<String> orderedProcesses = new ArrayList<>();
        if (processes.contains("切管")) orderedProcesses.add("切管");
        if (processes.contains("下料")) orderedProcesses.add("下料");
        if (processes.contains("钳工")) orderedProcesses.add("钳工");
        if (processes.contains("折弯")) orderedProcesses.add("折弯");
        if (processes.contains("焊接")) orderedProcesses.add("焊接");
        if (processes.contains("龙门")) orderedProcesses.add("龙门");
        if (processes.contains("喷塑")) orderedProcesses.add("喷塑");
        if (processes.contains("组装")) orderedProcesses.add("组装");
        if (processes.contains("转组装")) orderedProcesses.add("转组装");

        // 如果有父级，添加 "转" + 父级第一道工序
        if (instance.getParent() != null) {
            Set<String> parentProcesses = instance.getParent().getProcessSequence();
            if (parentProcesses != null && !parentProcesses.isEmpty()) {
                List<String> parentOrdered = new ArrayList<>();
                if (parentProcesses.contains("钳工")) parentOrdered.add("钳工");
                if (parentProcesses.contains("折弯")) parentOrdered.add("折弯");
                if (parentProcesses.contains("焊接")) parentOrdered.add("焊接");

                if (!parentOrdered.isEmpty()) {
                    String firstParentProcess = parentOrdered.get(0);
                    orderedProcesses.add("转" + firstParentProcess);
                }
            }
        }

        // 拼接完整工序到 processString
        String processString = String.join("-", orderedProcesses);
        instance.setProcessString(processString);

        // 递归处理子实例
        if (instance.getChildren() != null) {
            for (DrawInstance child : instance.getChildren()) {
                processInstance(child);
            }
        }
    }

    // 生成管子（一般不拆图，所以pdf里没有）
    private DrawInstance pipeDrawInstance(String drawingNumber, DrawInstance parent) {
        DrawInstance pipeDrawInstance = new DrawInstance();
        pipeDrawInstance.setDrawingNumber(drawingNumber);
        pipeDrawInstance.setParent(parent);
        Set<String> processes = new HashSet<>();
        processes.add("切管");
        processes.add("钳工");
        pipeDrawInstance.setProcessSequence(processes);

        return pipeDrawInstance;
    }
}
