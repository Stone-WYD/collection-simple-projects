package com.wyd.pdf.convert.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProcessData {
    /**
     * 工序序号
     */
    private String sequence = "";

    /**
     * 工序名称
     */
    private String processName = "";

    /**
     * 工序描述
     */
    private String processDesc = "";

    /**
     * 首件确认
     */
    private String firstPieceConfirm = "";

    /**
     * 良品数量
     */
    private String goodQty = "";

    /**
     * 不良品数量
     */
    private String badQty = "";

    /**
     * 操作员
     */
    private String operator = "";

    /**
     * 工序日期
     */
    private LocalDateTime processDate;

    /**
     * 最终检验结果
     */
    private String finalInspection = "";

    /**
     * 备注
     */
    private String remark = "";

    // 注：
    // 1. @Data 自动生成无参构造、getter/setter、toString、equals、hashCode
    // 2. 字段默认值设为空字符串，替代原方法的 null 替换逻辑，简化赋值操作
    // 3. 若需全参构造，可添加 @AllArgsConstructor；需 Builder 模式可添加 @Builder
}
