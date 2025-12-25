package com.wyd.pdf.convert;

import com.wyd.pdf.convert.utils.FreeMarkerUtil;
import com.wyd.pdf.convert.utils.HtmlToImageUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wyd.pdf.convert.utils.ImageAndPdfMergeUtil.mergeImageFileToPdf;

public class DemoMain {

    public static void main(String[] args) throws ParserConfigurationException {
        // ========== 步骤1：准备模板数据 ==========
        Map<String, Object> data = new HashMap<>();
        data.put("title", "订单详情");
        data.put("orderNo", "ORD20251225001");
        data.put("customerName", "张三");
        data.put("amount", 999.00);
        data.put("createTime", "2025-12-25 10:00:00");

        // ========== 步骤2：渲染FreeMarker模板生成HTML ==========
        String html = FreeMarkerUtil.renderTemplate("order.ftl", data);
        System.out.println("渲染后的HTML：\n" + html);

        // ========== 步骤3：HTML转图片（可生成多张，这里演示1张） ==========
        BufferedImage image = HtmlToImageUtil.htmlToImage(html, 800, 600); // 宽800px，高600p

        // ========== 步骤4：图片拼接为PDF ==========
        String pdfPath = "D:/order_20251225.pdf"; // 生成的PDF路径
        mergeImageFileToPdf("C:\\Users\\11748\\Downloads\\NE240273-09-050101.pdf", image, pdfPath);
    }

}
