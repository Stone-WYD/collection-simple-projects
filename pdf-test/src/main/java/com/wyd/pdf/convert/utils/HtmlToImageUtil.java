package com.wyd.pdf.convert.utils;

import org.w3c.dom.Document;
import org.xhtmlrenderer.swing.Java2DRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * HTML转图片工具类
 */
public class HtmlToImageUtil {
    /**
     * HTML字符串转BufferedImage
     * @param html HTML内容
     * @param width 图片宽度（像素）
     * @param height 图片高度（像素）
     * @return 内存图片对象
     */
    public static BufferedImage htmlToImage(String html, int width, int height) throws ParserConfigurationException {
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
}