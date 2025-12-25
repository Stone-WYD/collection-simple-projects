package com.wyd.pdf.convert.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static javax.imageio.ImageIO.read;

public class ImageAndPdfMergeUtil {

    /**
     * 将图片拼接至已有PDF的末尾（核心方法）
     * @param existingPdfPath 已有PDF文件路径（如：D:/old.pdf）
     * @param image 待拼接的图片对象（BufferedImage）
     * @param outputPdfPath 拼接后新PDF的保存路径（如：D:/new.pdf）
     */
    public static void mergeImageToPdf(String existingPdfPath, BufferedImage image, String outputPdfPath) {
        // 1. 校验输入参数
        if (!Files.exists(new File(existingPdfPath).toPath())) {
            throw new RuntimeException("已有PDF文件不存在：" + existingPdfPath);
        }
        if (image == null) {
            throw new RuntimeException("待拼接的图片为空");
        }

        // 2. 读取已有PDF + 拼接图片页面（try-with-resources自动释放资源）
        try (PDDocument existingDoc = PDDocument.load(new File(existingPdfPath))) {
            // 3. 将图片转为PDF页面（适配图片尺寸）
            PDPage imagePage = createImagePage(existingDoc, image);

            // 4. 将图片页面添加到已有PDF的末尾（也可插入到指定位置）
            // 若需插入到开头：
            existingDoc.getPages().insertBefore(imagePage, existingDoc.getPage(0));

            // 5. 保存拼接后的PDF
            existingDoc.save(new File(outputPdfPath));
            System.out.println("图片与PDF拼接成功，新文件路径：" + outputPdfPath);
        } catch (IOException e) {
            throw new RuntimeException("图片与PDF拼接失败", e);
        }
    }

    /**
     * 将BufferedImage转为PDF页面（适配图片尺寸，避免变形）
     * @param doc PDF文档对象
     * @param image 图片对象
     * @return 图片对应的PDF页面
     */
    private static PDPage createImagePage(PDDocument doc, BufferedImage image) throws IOException {
        // 图片原始尺寸
        float imgWidth = image.getWidth();
        float imgHeight = image.getHeight();

        // 1. 创建PDF页面（页面尺寸与图片一致）
        PDRectangle pageSize = new PDRectangle(imgWidth, imgHeight);
        PDPage page = new PDPage(pageSize);

        // 2. 将图片绘制到PDF页面
        PDImageXObject pdImage = LosslessFactory.createFromImage(doc, image);
        try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
            // 绘制图片：覆盖整个页面（x=0, y=0 为页面左下角，符合PDF坐标体系）
            contentStream.drawImage(pdImage, 0, 0, imgWidth, imgHeight);
        }

        return page;
    }

    /**
     * 重载方法：支持图片文件路径（而非BufferedImage）
     * @param existingPdfPath 已有PDF路径
     * @param outputPdfPath 拼接后PDF路径
     */
    public static void mergeImageFileToPdf(String existingPdfPath, BufferedImage bufferedImage, String outputPdfPath) {
        try {
            // 将图片文件转为BufferedImage
            mergeImageToPdf(existingPdfPath, bufferedImage, outputPdfPath);
        } catch (Exception e) {
            throw new RuntimeException("读取图片文件失败", e);
        }
    }
}
