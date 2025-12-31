package com.wyd.pdf.convert.utils;

import org.w3c.dom.Document;
import org.xhtmlrenderer.swing.Java2DRenderer;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HTML转图片工具类
 */
public class HtmlToImageUtil2 {
    /**
     * HTML字符串转BufferedImage
     * @param html HTML内容
     * @param width 图片宽度（像素）
     * @param height 图片高度（像素）
     * @return 内存图片对象
     */
    public static BufferedImage htmlToImage(String html, int width, int height) {
        // 1. 初始化XML解析器（用于解析HTML为Document）
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 关闭XML验证（兼容HTML标签）
        factory.setValidating(false);
        // dtd 文件起到校验作用，不是刚需
        // factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        try {
            // 将HTML字符串转为输入流（Flying Saucer要求XHTML规范，需确保HTML标签闭合）
            ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            // 初始化渲染器
            Java2DRenderer renderer = new Java2DRenderer(document, width, height);

            // 渲染为图片
            BufferedImage image = renderer.getImage();
            inputStream.close(); // 释放输入流
            return image;
        } catch (Exception e) {
            throw new RuntimeException("HTML转图片失败", e);
        }
    }

    /**
     * 深拷贝BufferedImage（创建独立的图片实例）
     * @param originalImage 原始图片
     * @return 复制后的新图片（独立资源，与原图片无关联）
     */
    public static BufferedImage copyBufferedImage(BufferedImage originalImage) {
        if (originalImage == null) {
            throw new IllegalArgumentException("原始图片不能为空");
        }

        // 1. 创建新的BufferedImage，与原图片同宽、同高、同图像类型
        BufferedImage copiedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                originalImage.getType() // 保持图片类型一致（如RGB、ARGB等）
        );

        // 2. 通过Graphics2D将原图片绘制到新图片上（深拷贝像素数据）
        Graphics2D g2d = copiedImage.createGraphics();
        try {
            // 开启抗锯齿，保证复制后的图片清晰度
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            // 绘制原图片到新图片（从坐标0,0开始，覆盖整个画布）
            g2d.drawImage(originalImage, 0, 0, null);
        } finally {
            // 必须释放Graphics2D资源，避免内存泄漏
            g2d.dispose();
        }

        return copiedImage;
    }

    /**
     * 保存BufferedImage到指定路径
     * @param image 要保存的图片
     * @param savePath 保存路径（如 "D:/images/copied_image.png"）
     * @param format 图片格式（推荐PNG/JPG，PNG无压缩损失，JPG需注意质量）
     */
    public static void saveBufferedImage(BufferedImage image, String savePath, String format) {
        if (image == null) {
            throw new IllegalArgumentException("要保存的图片不能为空");
        }
        if (savePath == null || savePath.isEmpty()) {
            throw new IllegalArgumentException("保存路径不能为空");
        }
        // 支持的格式：PNG/JPG/GIF（ImageIO内置支持，无需额外依赖）
        if (!"PNG".equalsIgnoreCase(format) && !"JPG".equalsIgnoreCase(format) && !"JPEG".equalsIgnoreCase(format)) {
            throw new IllegalArgumentException("仅支持PNG/JPG/JPEG格式");
        }

        File saveFile = new File(savePath);
        try {
            // 确保父目录存在（如D:/images/不存在则创建）
            File parentDir = saveFile.getParentFile();
            if (!parentDir.exists()) {
                boolean mkdirsSuccess = parentDir.mkdirs();
                if (!mkdirsSuccess) {
                    throw new IOException("创建保存目录失败：" + parentDir.getAbsolutePath());
                }
            }

            // 保存图片到文件（返回false表示格式不支持，此处已校验，可忽略）
            boolean saveSuccess = ImageIO.write(image, format, saveFile);
            if (!saveSuccess) {
                throw new IOException("保存图片失败，格式不支持：" + format);
            }
            System.out.println("图片保存成功：" + saveFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("保存图片异常", e);
        }
    }

}