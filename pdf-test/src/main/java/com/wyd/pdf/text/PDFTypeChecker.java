package com.wyd.pdf.text;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFTypeChecker {


    public static boolean isTextBasedPDF(String pdfPath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            // 提取前1页文本（无需全量提取，提高效率）
            stripper.setStartPage(1);
            stripper.setEndPage(1);
            String text = stripper.getText(document);
            // 有非空有效文本即为文本型
            System.out.println(text);
            return text != null && !text.trim().isEmpty();
        }
    }

    // 测试调用
    public static void main(String[] args) {
        String pdfPath = "D:\\Tencent\\xwechat_files\\wxid_jfqlvgtpx8ju22_57d4\\msg\\file\\2026-01\\PDF合并.pdf";
        try {
            if (isTextBasedPDF(pdfPath)) {
                System.out.println("该PDF是文本型，可直接提取文字");
            } else {
                System.out.println("该PDF是扫描型/图片型，需OCR识别");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
