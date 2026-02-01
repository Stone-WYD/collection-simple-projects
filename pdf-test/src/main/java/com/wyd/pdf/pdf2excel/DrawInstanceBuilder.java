package com.wyd.pdf.pdf2excel;


import java.util.List;
import java.util.Set;

public abstract class DrawInstanceBuilder {

    public Set<DrawInstance> build(String pdfPath) {
        List<String> stringContentFromPDF = getStringContentFromPDF(pdfPath);
        List<DrawInstance> instances = initInstances(stringContentFromPDF);
        Set<DrawInstance> roots = buildHierarchy(instances);
        determineProcess(roots);
        return roots;
    }

    // 1. 解析PDF每一页的内容
    public abstract List<String> getStringContentFromPDF(String pdfPath);

    // 2. 初始化实例，将String 转为 DrawInstance
    public abstract List<DrawInstance> initInstances(List<String> contentList);

    // 3. 构建父子关系，
    public abstract Set<DrawInstance> buildHierarchy(List<DrawInstance> instances);

    // 4. 确定工序信息
    public abstract void determineProcess(Set<DrawInstance> root);

}
