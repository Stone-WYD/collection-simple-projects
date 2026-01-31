package com.wyd.pdf.pdf2excel;


import java.util.List;

public abstract class DrawInstanceBuilder {

    public DrawInstance build(String pdfPath) {
        List<String> stringContentFromPDF = getStringContentFromPDF(pdfPath);
        List<DrawInstance> instances = initInstances(stringContentFromPDF);
        DrawInstance root = buildHierarchy(instances);
        determineProcess(root);
        return root;
    }

    // 解析PDF每一页的内容
    public abstract List<String> getStringContentFromPDF(String pdfPath);

    // 初始化实例，将String 转为 DrawInstance
    public abstract List<DrawInstance> initInstances(List<String> contentList);

    // 构建父子关系，
    public abstract DrawInstance buildHierarchy(List<DrawInstance> instances);

    // 确定工序信息
    public abstract void determineProcess(DrawInstance root);

}
