package com.wyd.zmhkmiddleware.business.model.local.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工银行信息
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EtEmplBank implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 智慧人事系统人员编码
     */
    @TableId("ZHREMPL")
    @SerializedName("ZHREMPL")
    private String zhrempl;

    /**
     * 银行信息流水码
     */
    @TableField("BANK_ID")
    @SerializedName("BANK_ID")
    private String bankId;

    /**
     * 雇佣记录编号
     */
    @TableField("ZPOSTTY")
    @SerializedName("ZPOSTTY")
    private String zpostty;

    /**
     * 银行国家
     */
    @TableField("LAND1")
    @SerializedName("LAND1")
    private String land1;

    /**
     * 银行名称
     */
    @TableField("ZBANKA")
    @SerializedName("ZBANKA")
    private String zbanka;

    /**
     * 银行代码
     */
    @TableField("BANKL")
    @SerializedName("BANKL")
    private String bankl;

    /**
     * 银行账号
     */
    @TableField("ZBANKN")
    @SerializedName("ZBANKN")
    private String zbankn;

    /**
     * 帐户持有人姓名
     */
    @TableField("KOINH")
    @SerializedName("KOINH")
    private String koinh;

    /**
     * 是否主要账户（X-是，空值-否）
     */
    @TableField("ZMAIN")
    @SerializedName("ZMAIN")
    private String zmain;

    /**
     * 删除标识符（X-已删除，空值-有效）
     */
    @TableField("ZDEL_BK")
    @SerializedName("ZDEL_BK")
    private String zdelBk;

    /**
     * 备用字段1
     */
    @TableField("ZATTR1_BK")
    @SerializedName("ZATTR1_BK")
    private String zattr1Bk;

    /**
     * 备用字段2
     */
    @TableField("ZATTR2_BK")
    @SerializedName("ZATTR2_BK")
    private String zattr2Bk;

    /**
     * 备用字段3
     */
    @TableField("ZATTR3_BK")
    @SerializedName("ZATTR3_BK")
    private String zattr3Bk;

    /**
     * 备用字段4
     */
    @TableField("ZATTR4_BK")
    @SerializedName("ZATTR4_BK")
    private String zattr4Bk;

    /**
     * 备用字段5
     */
    @TableField("ZATTR5_BK")
    @SerializedName("ZATTR5_BK")
    private String zattr5Bk;

    /**
     * 备用字段6
     */
    @TableField("ZATTR6_BK")
    @SerializedName("ZATTR6_BK")
    private String zattr6Bk;


}
