package com.wyd.pdf.convert;

import com.wyd.pdf.convert.utils.FreeMarkerUtil;
import com.wyd.pdf.convert.utils.HtmlToImageUtil;
import com.wyd.pdf.convert.utils.QrCodeUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wyd.pdf.convert.utils.ImageAndPdfMergeUtil.mergeImageFileToPdf;

public class DemoMain {

/*    public static void main(String[] args) throws ParserConfigurationException {
        // ========== 步骤1：准备模板数据 ==========
        Map<String, Object> data = new HashMap<>();
        // 基础信息
        data.put("title", "订单详情（含表格+二维码）");
        data.put("orderNo", "ORD20251225001");
        data.put("customerName", "张三");
        data.put("createTime", "2025-12-25 10:00:00");
        data.put("totalAmount", 1299.00);

        // 表格数据：订单明细列表
        List<Map<String, Object>> orderItems = new ArrayList<>();
        orderItems.add(buildOrderItem("小米14 Pro", 4999.00, 2, 999.80)); // 示例：0.2件（可改为整数）
        orderItems.add(buildOrderItem("小米充电宝", 99.20, 1, 99.20));
        orderItems.add(buildOrderItem("数据线", 29.90, 1, 29.90));
        data.put("orderItems", orderItems);

        // 二维码：内容为订单号，生成Base64
        String qrContent = "https://your-domain.com/order/check?no=" + data.get("orderNo"); // 二维码跳转链接
        String qrBase64 = QrCodeUtil.generateQrCodeBase64(qrContent, 120, 120); // 120x120像素
        data.put("qrCodeBase64", qrBase64);

        // ========== 步骤2：渲染FreeMarker模板生成HTML ==========
        String html = FreeMarkerUtil.renderTemplate("order.ftl", data);
        System.out.println("渲染后的HTML：\n" + html);

        // ========== 步骤3：HTML转图片（可生成多张，这里演示1张） ==========
        BufferedImage image = HtmlToImageUtil.htmlToImage(html, 800, 600); // 宽800px，高600p

        // ========== 步骤4：图片拼接为PDF ==========
        String pdfPath = "D:/order_20251225.pdf"; // 生成的PDF路径
        mergeImageFileToPdf("C:\\Users\\11748\\Downloads\\NE240273-09-050101.pdf", image, pdfPath);
    }*/

    public static void main(String[] args) throws ParserConfigurationException {
        // ========== 步骤1：准备模板数据 ==========
        Map<String, Object> data = new HashMap<>();
// 基础信息
        data.put("companyName", "常州西洲机电科技有限公司");
        data.put("pageNo", "6/7");
        data.put("customerName", "征图印刷BU");
        data.put("productName", "FLS-M021-连接背板A");
        data.put("productDrawingNo", "RM-JJ-00051297-02");
        data.put("productionQty", "42");
        data.put("partDrawingNo", "RM-JJ-00051297-02-1");
        data.put("bomCreator", "李以亮");
        data.put("deliveryDate", "2025-08-14");
        data.put("partName", "FLS-M021-连接背板A");
        data.put("materialThickness", "12");
        data.put("material", "SUS304精板");
        data.put("taskNo", "OD250809006");

// 工序列表
        List<Map<String, Object>> processes = new ArrayList<>();
        processes.add(buildProcess("1", "外购", "", "", "", "", "", "", "", ""));
        processes.add(buildProcess("2", "机加", "", "", "", "", "", "", "", ""));
        processes.add(buildProcess("3", "组装", "", "", "", "", "", "", "", ""));
        data.put("orderProcesses", processes);

// 二维码（通过QrCodeUtil生成）
        String qrContent = "www.baidu.com";
        data.put("qrCodeBase64", QrCodeUtil.generateQrCodeBase64(qrContent, 80, 80));
        data.put("qrCodeSerialNo", "5.0000465");



        // ========== 步骤2：渲染FreeMarker模板生成HTML ==========
        String html = FreeMarkerUtil.renderTemplate("produce_order.ftl", data);
        System.out.println("渲染后的HTML：\n" + html);

        // ========== 步骤3：HTML转图片（可生成多张，这里演示1张） ==========
        BufferedImage image = HtmlToImageUtil.htmlToImage(html, 942, 595); // 宽800px，高600p

        // ========== 步骤4：图片拼接为PDF ==========
        String pdfPath = "D:/productOrder4.pdf"; // 生成的PDF路径
        mergeImageFileToPdf("C:\\Users\\11748\\Downloads\\NE240273-09-050101.pdf", image, pdfPath);
    }

    private static Map<String, Object> buildProcess(
            String sequence,
            String processName,
            String processDesc,
            String firstPieceConfirm,
            String goodQty,
            String badQty,
            String operator,
            String processDate,
            String finalInspection,
            String remark
    ) {
        Map<String, Object> process = new HashMap<>();
        // 字段与模板严格对应，空值替换为空字符串（避免FreeMarker渲染NullPointerException）
        process.put("sequence", sequence == null ? "" : sequence);
        process.put("processName", processName == null ? "" : processName);
        process.put("processDesc", processDesc == null ? "" : processDesc);
        process.put("firstPieceConfirm", firstPieceConfirm == null ? "" : firstPieceConfirm);
        process.put("goodQty", goodQty == null ? "" : goodQty);
        process.put("badQty", badQty == null ? "" : badQty);
        process.put("operator", operator == null ? "" : operator);
        process.put("processDate", processDate == null ? "" : processDate);
        process.put("finalInspection", finalInspection == null ? "" : finalInspection);
        process.put("remark", remark == null ? "" : remark);
        return process;
    }


    /**
     * 构建订单明细项
     */
    private static Map<String, Object> buildOrderItem(String productName, double price, int quantity, double subtotal) {
        Map<String, Object> item = new HashMap<>();
        item.put("productName", productName);
        item.put("price", price);
        item.put("quantity", quantity);
        item.put("subtotal", subtotal);
        return item;
    }

}
