package com.wyd.pdf.convert.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PdfTemplateData {
    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 页码（如：6/7）
     */
    private String pageNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品图号
     */
    private String productDrawingNo;

    /**
     * 生产数量（数值型）
     */
    private Integer productionQty;

    /**
     * 零件图号
     */
    private String partDrawingNo;

    /**
     * BOM创建人
     */
    private String bomCreator;

    /**
     * 交付日期（日期型）
     */
    private LocalDateTime deliveryDate;

    /**
     * 零件名称
     */
    private String partName;

    /**
     * 材料厚度（数值型，单位：mm）
     */
    private Integer materialThickness;

    /**
     * 材料规格
     */
    private String material;

    /**
     * 任务单号
     */
    private String taskNo;


    private String qrCodeBase64;

    private List<ProcessData> orderProcesses;
}
