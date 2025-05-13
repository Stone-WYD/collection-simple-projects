package com.wyd.zmhkmiddleware.business.model.local.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工岗位信息
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EtEmplPost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 智慧人事系统人员编码
     */
    @TableId("ZHREMPL")
    @SerializedName("ZHREMPL")
    private String zhrempl;

    /**
     * 雇佣记录编号
     */
    @TableField("ZPOSTTY")
    @SerializedName("ZPOSTTY")
    private String zpostty;

    /**
     * 岗位编码
     */
    @TableField("ZHRPOST")
    @SerializedName("ZHRPOST")
    private String zhrpost;

    /**
     * 职衔
     */
    @TableField("ZZX")
    @SerializedName("ZZX")
    private String zzx;

    /**
     * 职务在岗状态（A-有效，I-无效）
     */
    @TableField("ZSTAT_PS")
    @SerializedName("ZSTAT_PS")
    private String zstatPs;

    /**
     * 删除标识符（X-已删除，空值-有效）
     */
    @TableField("ZDEL_PS")
    @SerializedName("ZDEL_PS")
    private String zdelPs;

    /**
     * 备用字段1
     */
    @TableField("ZATTR1_PS")
    @SerializedName("ZATTR1_PS")
    private String zattr1Ps;

    /**
     * 备用字段2
     */
    @TableField("ZATTR2_PS")
    @SerializedName("ZATTR2_PS")
    private String zattr2Ps;

    /**
     * 备用字段3
     */
    @TableField("ZATTR3_PS")
    @SerializedName("ZATTR3_PS")
    private String zattr3Ps;

    /**
     * 备用字段4
     */
    @TableField("ZATTR4_PS")
    @SerializedName("ZATTR4_PS")
    private String zattr4Ps;

    /**
     * 备用字段5
     */
    @TableField("ZATTR5_PS")
    @SerializedName("ZATTR5_PS")
    private String zattr5Ps;

    /**
     * 备用字段6
     */
    @TableField("ZATTR6_PS")
    @SerializedName("ZATTR6_PS")
    private String zattr6Ps;


}
