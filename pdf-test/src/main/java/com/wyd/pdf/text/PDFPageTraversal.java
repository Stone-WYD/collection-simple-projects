package com.wyd.pdf.text;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PDFPageTraversal {

    /**
     * 遍历PDF每一页，逐页提取文本
     */
    public static Map<Integer, String> extractPerPageText(String pdfPath) throws IOException {
        Map<Integer, String> pageTextMap = new HashMap<>();
        PDDocument document = null;

        try {
            // 第一步：尝试提取文本型PDF每一页
            document = PDDocument.load(new File(pdfPath));
            int totalPages = document.getNumberOfPages();
            System.out.println("检测到PDF共 " + totalPages + " 页，开始逐页提取文本...");

            PDFTextStripper stripper = new PDFTextStripper();
            for (int pageNum = 1; pageNum <= totalPages; pageNum++) {
                // 只提取当前页的文本
                stripper.setStartPage(pageNum);
                stripper.setEndPage(pageNum);
                String pageText = stripper.getText(document).trim();
                pageTextMap.put(pageNum, pageText);
            }
        } finally {
            if (document != null) {
                document.close(); // 确保关闭文档，释放资源
            }
        }
        return pageTextMap;
    }

    // 测试主方法
    public static void main(String[] args) {
        String pdfPath = "D:\\Tencent\\xwechat_files\\wxid_jfqlvgtpx8ju22_57d4\\msg\\file\\2026-02\\零件1.pdf"; // 替换为你的PDF路径
        try {
            Map<Integer, String> pageTextResult = extractPerPageText(pdfPath);

            // 遍历输出每一页内容
            for (Map.Entry<Integer, String> entry : pageTextResult.entrySet()) {
                int pageNum = entry.getKey();
                String text = entry.getValue();
                System.out.println("\n========== 第 " + pageNum + " 页内容 ==========");
                System.out.println(text.isEmpty() ? "该页无有效文本" : text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
