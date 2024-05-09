package com.wyd.pdftest;

/**
 * @program: pdf-test
 * @author: Stone
 * @create: 2024-05-05 11:53
 */

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

/**
 * pdf加水印测试类
 * */
public class PdfBoxTest {

    public static void main(String[] args) throws IOException {
        // 读取原始 PDF 文件
        PDDocument document = PDDocument.load(new File("C:\\Users\\Stone\\Desktop\\我的简历 (1).pdf"));

        // 遍历 PDF 中的所有页面
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            PDPage page = document.getPage(i);
            PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.APPEND, true, true);

            // 设置字体和字号
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 36);

            // 设置透明度
            contentStream.setNonStrokingColor(100, 100, 100);

            // 添加文本水印
            contentStream.beginText();
            // 设置水印位置
            contentStream.newLineAtOffset(100, 100);
            // 设置水印内容
            contentStream.showText("wyd-test");
            contentStream.endText();

            contentStream.close();
        }

        // 保存修改后的 PDF 文件
        document.save(new File("output.pdf"));
        document.close();

    }

}