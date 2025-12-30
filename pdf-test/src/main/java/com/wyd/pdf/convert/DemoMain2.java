package com.wyd.pdf.convert;

import cn.hutool.core.bean.BeanUtil;
import com.wyd.pdf.convert.domain.PdfTemplateData;
import com.wyd.pdf.convert.domain.ProcessData;
import com.wyd.pdf.convert.utils.FreeMarkerUtil;
import com.wyd.pdf.convert.utils.HtmlToImageUtil;
import com.wyd.pdf.convert.utils.QrCodeUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wyd.pdf.convert.utils.ImageAndPdfMergeUtil.mergeImageFileToPdf;

public class DemoMain2 {

    public static void main(String[] args) throws ParserConfigurationException {
        // ========== 步骤1：准备模板数据 ==========
        Map<String, Object> dataMap = new HashMap<>();
        PdfTemplateData data = new PdfTemplateData();
        data.setCompanyName("常州西洲机电科技有限公司");
        data.setPageNo("6/7");
        data.setCustomerName("征图印刷BU");
        data.setProductName("FLS-M021-连接背板A");
        data.setProductDrawingNo("RM-JJ-00051297-02");
        data.setProductionQty(42);
        data.setPartDrawingNo("RM-JJ-00051297-02-1");
        data.setBomCreator("李以亮");
        data.setDeliveryDate(LocalDateTime.now());
        data.setPartName("FLS-M021-连接背板A");
        data.setMaterialThickness(12);
        data.setMaterial("SUS304精板");
        data.setTaskNo("OD250809006");


        // 工序列表
        List<ProcessData> processDataList = new ArrayList<>();
        ProcessData processData = new ProcessData();
        processData.setProcessDate(LocalDateTime.now());
        processData.setProcessName("外购");
        processDataList.add(processData);
        data.setOrderProcesses(processDataList);


        Map<String, Object> map = BeanUtil.beanToMap(data);

// 二维码（通过QrCodeUtil生成）
        String qrContent = "www.baidu.com";
        map.put("qrCodeBase64", QrCodeUtil.generateQrCodeBase64(qrContent, 80, 80));
        map.put("qrCodeSerialNo", "5.0000465");



        // ========== 步骤2：渲染FreeMarker模板生成HTML ==========
        String html = FreeMarkerUtil.renderTemplate("produce_order.ftl", map);
        System.out.println("渲染后的HTML：\n" + html);

        // ========== 步骤3：HTML转图片（可生成多张，这里演示1张） ==========
        BufferedImage image = HtmlToImageUtil.htmlToImage(html, 942, 595); // 宽800px，高600p

        // ========== 步骤4：图片拼接为PDF ==========
        String pdfPath = "D:/productOrder55.pdf"; // 生成的PDF路径
        mergeImageFileToPdf("C:\\Users\\11748\\Downloads\\NE240273-09-050101.pdf", image, pdfPath);
    }

}
