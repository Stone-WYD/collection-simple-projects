package com.wyd.pdf.convert;

import cn.hutool.core.bean.BeanUtil;
import com.wyd.pdf.convert.domain.MspProduceOrder;
import com.wyd.pdf.convert.domain.MspProduceProcess;
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
        MspProduceOrder produceOrder = buildTestProduceOrder();
        Map<String, Object> map = BeanUtil.beanToMap(produceOrder);

// 二维码（通过QrCodeUtil生成）
        String qrContent = "www.baidu.com";
        map.put("qrCodeBase64", QrCodeUtil.generateQrCodeBase64(qrContent, 100, 100));
        map.put("companyName", "常州西洲机电科技");



        // ========== 步骤2：渲染FreeMarker模板生成HTML ==========
        String html = FreeMarkerUtil.renderTemplate("test.ftl", map);
        System.out.println("渲染后的HTML：\n" + html);

        // ========== 步骤3：HTML转图片（可生成多张，这里演示1张） ==========
        BufferedImage image = HtmlToImageUtil.htmlToImage(html, 1123, 794); // 宽800px，高600p

        // ========== 步骤4：图片拼接为PDF ==========
        String pdfPath = "D:/productOrder33.pdf"; // 生成的PDF路径
        mergeImageFileToPdf("C:\\Users\\11748\\Downloads\\NE240273-09-050101.pdf", image, pdfPath);
    }

    /**
     * 构建完整的生产工单测试实例
     */
    public static MspProduceOrder buildTestProduceOrder() {
        MspProduceOrder order = new MspProduceOrder();

        // 基础主键/关联ID
        order.setParentId(10001L);          // 父工单ID
        order.setBomMainId(20001L);         // BOM主表ID
        order.setOrderDetailId(30001L);     // 订单详情ID

        // 订单基础信息
        order.setCustomerOrderNo("KH20250815001"); // 客户订单号
        order.setWorkNo("GD250815001");            // 工单号
        order.setWorkName("FLS-M021-连接背板A生产工单"); // 工单名
        order.setCustomerName("征图印刷BU");         // 客户名
        order.setProductName("FLS-M021-连接背板A"); // 产成品名
        order.setProductDrawingNo("RM-JJ-00051297-02"); // 产成品图号

        // 零部件信息
        order.setCurrentName("FLS-M021-连接背板A-侧板"); // 当前生产零部件名
        order.setCurrentDrawingNo("RM-JJ-00051297-02-1"); // 零部件图号

        // 生产数量/产能/进度
        order.setNeedNum(42);               // 订单需求数量
        order.setTotalCapacity(420.0);      // 总产能（42件×10单位/件）
        order.setCurrentCapacity(380.0);    // 当前完成产能（因2件不合格减少）
        order.setCapacityProcess(90);       // 进度90%

        // 物料属性
        order.setThickness("12mm");         // 料厚
        order.setMaterial("SUS304精板");    // 材质

        // 状态/标识
        order.setStatus("1");               // 状态：生产中
        order.setProductFlag("Y");          // 成品工单
        order.setPrintFlag("N");            // 未打印
        order.setPriority(1);               // 优先级：紧急

        // 日期类字段（基于当前时间生成合理的时间范围）
        LocalDateTime now = LocalDateTime.now();
        order.setOrderDeliveryDate("2025-12-12 12:00:00"); // 交期：7天后
        order.setScheduleBeginDate("2025-12-12 12:00:00"); // 计划开始：1小时后
        order.setScheduleEndDate("2025-12-12 12:00:00");    // 计划结束：5天后
        order.setShowBeginDate("2025-12-12 12:00:00");                                     // 展示开始：当前时间
        order.setShowEndDate("2025-12-12 12:00:00");        // 展示结束：6天后

        // 工序列表（构建3个测试工序）
        order.setProduceProcessList(buildTestProcessList(order.getParentId()));

        return order;
    }

    /**
     * 构建工序测试列表
     */
    private static List<MspProduceProcess> buildTestProcessList(Long produceOrderId) {
        List<MspProduceProcess> processList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        // 工序1：冲压
        MspProduceProcess process1 = new MspProduceProcess();
        process1.setId(1001L);
        process1.setProduceOrderId(produceOrderId);
        process1.setCapacityExpectRecordId(40001L);
        process1.setParentProcessCode("ROOT");
        process1.setProcessCode("CY001");
        process1.setProcessName("冲压工序");
        process1.setProcessOrder(1);
        process1.setSwitchFlag("N");
        process1.setProcessCapacity(140.0); // 该工序产能
        process1.setFirstCheck(1);          // 首件确认：是
        process1.setSuccessNum(40);         // 合格数量
        process1.setFailNum(2);             // 不合格数量
        process1.setCurrentFinishNum(40);   // 完成数量（扣除不合格）
        process1.setExceptBeginDate("2025-12-12 12:00:00");
        process1.setExceptEndDate("2025-12-12 12:00:00");
        processList.add(process1);

        // 工序2：焊接
        MspProduceProcess process2 = new MspProduceProcess();
        process2.setId(1002L);
        process2.setProduceOrderId(produceOrderId);
        process2.setCapacityExpectRecordId(40002L);
        process2.setParentProcessCode("CY001");
        process2.setProcessCode("HJ001");
        process2.setProcessName("焊接工序");
        process2.setProcessOrder(2);
        process2.setSwitchFlag("N");
        process2.setProcessCapacity(140.0);
        process2.setFirstCheck(1);
        process2.setSuccessNum(38);
        process2.setFailNum(0);
        process2.setCurrentFinishNum(38);
        process2.setExceptBeginDate("2025-12-12 12:00:00");
        process2.setExceptEndDate("2025-12-12 12:00:00");
        processList.add(process2);

        // 工序3：打磨（过渡工序）
        MspProduceProcess process3 = new MspProduceProcess();
        process3.setId(1003L);
        process3.setProduceOrderId(produceOrderId);
        process3.setCapacityExpectRecordId(40003L);
        process3.setParentProcessCode("HJ001");
        process3.setProcessCode("DM001");
        process3.setProcessName("打磨工序");
        process3.setProcessOrder(3);
        process3.setSwitchFlag("Y");        // 过渡工序
        process3.setProcessCapacity(100.0);
        process3.setFirstCheck(0);          // 首件确认：否（待确认）
        process3.setSuccessNum(0);
        process3.setFailNum(0);
        process3.setCurrentFinishNum(0);
        process3.setExceptBeginDate("2025-12-12 12:00:00");
        process3.setExceptEndDate("2025-12-12 12:00:00");
        processList.add(process3);

        return processList;
    }

}
