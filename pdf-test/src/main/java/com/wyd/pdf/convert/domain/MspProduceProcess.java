package com.wyd.pdf.convert.domain;

import lombok.Data;


@Data
public class MspProduceProcess {

    private Long id;

    /**
     * msp_produce_order表主键
     */
    private Long produceOrderId;

    /**
     * msp_capacity_expect_record 工序产能表主键
     */
    private Long capacityExpectRecordId;

    /**
     * 父工序code
     */
    private String parentProcessCode;

    /**
     * 工序code
     */
    private String processCode;

    /**
     * 工序名
     */
    private String processName;

    /**
     * 工序说明
     * */
    private String processDesc;

    /**
     * 工序顺序
     */
    private Integer processOrder;

    /**
     * 过渡工序标志（Y表示是，N表示否）
     */
    private String switchFlag;

    /**
     * 工序分配到的产能（所有件数一起的产能）
     */
    private Double processCapacity;

    /**
     * 首件确认，1是，0否
     */
    private Integer firstCheck;

    /**
     * 合格数量，每当工作流经过当前工序就累计记录一次
     */
    private Integer successNum;

    /**
     * 不合格数量，每当工作流经过当前工序就累计记录一次
     */
    private Integer failNum;

    /**
     * 完成数量，这里的数量可能随着不合格产品的发现而减少。详细说明：计划生产5件，5件经过该工序且都完成时，这里的值是5，但后续工序产生出不合格产品导致要重做2件时，这里的值要变化为3
     */
    private Integer currentFinishNum;

    /**
     * 工序计划开始时间
     * */
    private String exceptBeginDate;

    /**
     * 工序计划结束时间，实际可能只展示结束时间，用于提示生产何时是deadline。与计划开始时间一样，确认工单时时间才会确定，且不会再发生变化。
     * 刚开始的值会与 msp_capacity_expect_record 中的计划时间保持一致，但考虑到工单存在重做的情况，msp_capacity_expect_record 中的数据可能会发生变化，所以这里的数据可能与msp_capacity_expect_record 中的数据不一致
     * */
    private String exceptEndDate;
}
