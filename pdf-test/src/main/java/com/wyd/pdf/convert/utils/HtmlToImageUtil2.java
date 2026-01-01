package com.wyd.pdf.convert.utils;

import org.w3c.dom.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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


    private static final float A4_WIDTH_INCH = 210f / 25.4f;  // 8.2677英寸
    private static final float A4_HEIGHT_INCH = 297f / 25.4f; // 11.6929英寸

    public static BufferedImage htmlToA4Image(String html, int dpi) {


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

            // 2. 换算A4纸对应的像素数（核心：物理尺寸→像素）
            int a4PixelWidth = Math.round(A4_WIDTH_INCH * dpi);  // ≈2480像素
            int a4PixelHeight = Math.round(A4_HEIGHT_INCH * dpi); // ≈3508像素
            // 初始化渲染器
            Java2DRenderer renderer = new Java2DRenderer(document, a4PixelHeight, a4PixelWidth);

            // 4. 关键：设置DPI匹配换算基准（源码init()默认72DPI，需覆盖）
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setDPI(dpi); // 必须和换算用的300DPI一致！
            // 可选：开启抗锯齿，提升文字/二维码清晰度（源码支持设置渲染提示）
            // 1. 创建渲染提示Map（针对你的工艺流程卡优化）
            Map<RenderingHints.Key, Object> hints = new HashMap<>();
            // 核心：开启全局抗锯齿（消除文字/表格/二维码锯齿）
            hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 文字抗锯齿（针对宋体等文字的高清渲染）
            hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            // 图像插值（二维码/图片缩放时更清晰）
            hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            // 线条平滑（表格边框更细腻）
            hints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            renderer.setRenderingHints(hints);

            // 5. 触发渲染（源码getImage()首次调用才会渲染）
            BufferedImage a4Image = renderer.getImage();
            inputStream.close(); // 释放输入流
            return a4Image;
        } catch (Exception e) {
            throw new RuntimeException("HTML转图片失败", e);
        }
    }
}