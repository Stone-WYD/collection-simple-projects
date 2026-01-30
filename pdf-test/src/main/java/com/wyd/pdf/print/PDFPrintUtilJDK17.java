package com.wyd.pdf.print;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PageLayout;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.Sides;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

/**
 * JDK 17环境下的PDF打印工具类
 * 特性：A4纸、双面打印、内容居中、适配打印边距
 */
public class PDFPrintUtilJDK17 {

    /**
     * 打印PDF文件
     * @param pdfFilePath PDF文件绝对路径
     * @param printerName 打印机名称（null则使用系统默认打印机）
     * @throws IOException 加载PDF文件异常
     * @throws PrinterException 打印相关异常
     */
    public static void printPDF(String pdfFilePath, String printerName) throws IOException, PrinterException {
        // JDK 17 try-with-resources自动关闭PDDocument，无需手动finally关闭
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            // 1. 查找目标打印机
            PrintService printService = getTargetPrintService(printerName);

            // 2. 构建打印请求属性集（核心打印参数）
            PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();
            // 2.1 A4纸张规格（强制覆盖PDF原始纸张设置）
            printAttributes.add(MediaSizeName.ISO_A4);
            // 2.2 双面打印（长边装订，短边装订可改为Sides.TUMBLE）
            printAttributes.add(Sides.DUPLEX);
            printAttributes.add(OrientationRequested.LANDSCAPE);
            // 2.3 打印份数（可按需修改）
            printAttributes.add(new Copies(1));

            // 3. 配置PDF打印规则（居中、适配边距）
            PDFPrintable printable = new PDFPrintable(
                    document,
                    Scaling.SCALE_TO_FIT,// 缩放至适配打印边距，避免内容溢出
                    false
            );
            // 3.1 开启内容居中 + 适配页面边距


            // 4. 执行打印任务
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintService(printService);
            printerJob.setPrintable(printable);

            // 可选：弹出打印对话框让用户确认/修改参数（注释则直接静默打印）
            // if (printerJob.printDialog(printAttributes)) {
            printerJob.print(printAttributes);
            System.out.println("PDF打印任务提交成功（JDK 17）：" + pdfFilePath);
            // }
        }
    }

    /**
     * 获取目标打印服务（打印机）
     * @param printerName 打印机名称（null则返回默认打印机）
     * @return 匹配的PrintService
     * @throws PrinterException 未找到指定打印机时抛出
     */
    private static PrintService getTargetPrintService(String printerName) throws PrinterException {
        if (printerName == null) {
            // JDK 17中PrinterJob.lookupDefaultPrintService()兼容无异常
            PrintService defaultService = PrinterJob.lookupPrintServices()[0];
            if (defaultService == null) {
                throw new PrinterException("未找到系统默认打印机");
            }
            return defaultService;
        }

        // 遍历所有可用打印机，匹配名称（模糊匹配）
        for (PrintService service : PrinterJob.lookupPrintServices()) {
            if (service.getName().contains(printerName)) {
                return service;
            }
        }
        throw new PrinterException("未找到指定打印机：" + printerName);
    }

    // 测试主方法（JDK 17可直接运行）
    public static void main(String[] args) {
        try {
            // 替换为你的PDF文件路径
            String pdfPath = "D:/wyd2.pdf";
            // 打印机名称（null用默认，示例："HP LaserJet M281fdw"）
            String printerName = "Brother DCP-B7638DN";

            printPDF(pdfPath, printerName);
        } catch (IOException | PrinterException e) {
            // JDK 17支持多异常捕获的简化写法
            System.err.println("打印失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
