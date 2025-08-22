package com.wyd.zmhkmiddleware.business.model.local.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * 员工基本信息
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class EtEmplBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * MDG人员编码
     */
    @TableField("ZEMPL")
    @SerializedName("ZEMPL")
    private String zempl;

    /**
     * 智慧人事系统人员编码
     */
    @TableId("ZHREMPL")
    @SerializedName("ZHREMPL")
    private String zhrempl;

    /**
     * 姓名
     */
    @TableField("ZEMPLNM")
    @SerializedName("ZEMPLNM")
    private String zemplnm;

    /**
     * 国籍
     */
    @TableField("REF_POSTA")
    @SerializedName("REF_POSTA")
    private String refPosta;

    /**
     * 性别
     */
    @TableField("ZXB")
    @SerializedName("ZXB")
    private String zxb;

    /**
     * 出生日期
     */
    @TableField("GBDAT")
    @SerializedName("GBDAT")
    private String gbdat;

    /**
     * 手机
     */
    @TableField("ZPHONENO")
    @SerializedName("ZPHONENO")
    private String zphoneno;

    /**
     * 工作电话
     */
    @TableField("ZTELNO")
    @SerializedName("ZTELNO")
    private String ztelno;

    /**
     * 证件国家
     */
    @TableField("ZZJGJ")
    @SerializedName("ZZJGJ")
    private String zzjgj;

    /**
     * 证件类型
     */
    @TableField("ZZJLX")
    @SerializedName("ZZJLX")
    private String zzjlx;

    /**
     * 证件号码
     */
    @TableField("ZZJHM")
    @SerializedName("ZZJHM")
    private String zzjhm;

    /**
     * 电子邮件地址
     */
    @TableField("ZEMAIL")
    @SerializedName("ZEMAIL")
    private String zemail;

    /**
     * 人员状态（A-有效，T-离职）
     */
    @TableField("ZSTAS_E")
    @SerializedName("ZSTAS_E")
    private String zstasE;

    /**
     * 用工类型
     */
    @TableField("ZYGLX")
    @SerializedName("ZYGLX")
    private String zyglx;

    /**
     * 入职日期
     */
    @TableField("ZHIRE_DT")
    @SerializedName("ZHIRE_DT")
    private String zhireDt;

    /**
     * 离职日期
     */
    @TableField("ZLEAVE_DT")
    @SerializedName("ZLEAVE_DT")
    private String zleaveDt;

    /**
     * 事业群（人资）
     */
    @TableField("ZBU")
    @SerializedName("ZBU")
    private String zbu;

    /**
     * 删除标识符（X-已删除，空值-有效）
     */
    @TableField("ZDEL")
    @SerializedName("ZDEL")
    private String zdel;

    /**
     * 备用字段1
     */
    @TableField("ZATTR1")
    @SerializedName("ZATTR1")
    private String zattr1;

    /**
     * 备用字段2
     */
    @TableField("ZATTR2")
    @SerializedName("ZATTR2")
    private String zattr2;

    /**
     * 备用字段3
     */
    @TableField("ZATTR3")
    @SerializedName("ZATTR3")
    private String zattr3;

    /**
     * 备用字段4
     */
    @TableField("ZATTR4")
    @SerializedName("ZATTR4")
    private String zattr4;

    /**
     * 备用字段5
     */
    @TableField("ZATTR5")
    @SerializedName("ZATTR5")
    private String zattr5;

    /**
     * 备用字段6
     */
    @TableField("ZATTR6")
    @SerializedName("ZATTR6")
    private String zattr6;


}
