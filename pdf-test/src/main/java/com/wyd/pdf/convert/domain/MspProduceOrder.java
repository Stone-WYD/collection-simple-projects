package com.wyd.pdf.convert.domain;

import lombok.Data;

import java.util.List;

@Data
public class MspProduceOrder {


    /**
     * 父工单id
     */
    private Long parentId;

    /**
     * msp_bom_main表的主键
     */
    private Long bomMainId;

    /**
     * 订单详情id
     */
    private Long orderDetailId;

    /**
     * 客户订单号
     * */
    private String customerOrderNo;

    /**
     * 工单号
     */
    private String workNo;

    /**
     * 工单名
     */
    private String workName;

    /**
     * 客户名
     */
    private String customerName;

    /**
     * 产成品名
     */
    private String productName;

    /**
     * 产成品图号
     */
    private String productDrawingNo;

    /**
     * 当前工单所要生产的零部件名
     */
    private String currentName;

    /**
     * 当前工单所要生产的零部件图号
     */
    private String currentDrawingNo;

    /**
     * 生产件数，这里记录的是订单中需要的数量
     */
    private Integer needNum;

    /**
     * 产能，下属所有组件/零件和自身工序产能之和
     */
    private Double totalCapacity;

    /**
     * 当前已完成的产能，可能随着不合格品的出现而减少
     */
    private Double currentCapacity;

    /**
     * 进度，已完成产能占总产能的百分比。这里的百分比可能随生产的过程出现减少的变化情况。比如某时刻发现不合格品，这里的值会减少。
     */
    private Integer capacityProcess;

    /**
     * 料厚
     */
    private String thickness;

    /**
     * 材质
     */
    private String material;

    /**
     * 状态：0：待确认，1：生产中，2：已完成，3：已结束。如果有重做操作，这里的已完成状态可能转为生产中。结束意味着产品工单完成了
     */
    private String status;

    /**
     * 是否为成品的工单，N：否，Y：是
     */
    private String productFlag;

    /**
     * 是否打印, N：否，Y：是
     */
    private String printFlag;

    /**
     * 交期
     */
    private String orderDeliveryDate;

    /**
     * 计划开始时间，仅包含当前工单的工序的始末时间，不包含子工单工序的始末时间，用于为后续为具体工序分配时间用，下同
     */
    private String scheduleBeginDate;

    /**
     * 计划结束时间，这里的时间是生成工单时生成，调整工单时会发生变动，与工单的工序计划时间要区分开
     */
    private String scheduleEndDate;

    /**
     * 展示开始时间
     */
    private String showBeginDate;

    /**
     * 展示结束时间
     */
    private String showEndDate;

    /**
     * 优先级（0=普通 1=紧急）
     */
    private Integer priority;


    /**
     * 工单中的工序信息，一对多的关系
     */
    private List<MspProduceProcess> produceProcessList;




}
