package com.wyd.zmhkmiddleware.business.model.local.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 岗位基本信息
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ItPostBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * MDG岗位编码
     */
    @TableField("ZPOST")
    @SerializedName("ZPOST")
    private String zpost;

    /**
     * 智慧人事系统岗位编码
     */
    @TableId("ZHRPOST")
    @SerializedName("ZHRPOST")
    private String zhrpost;

    /**
     * 所属BU/事业群
     */
    @TableField("ZBU")
    @SerializedName("ZBU")
    private String zbu;

    /**
     * 岗位名称
     */
    @TableField("ZPOSTNAM")
    @SerializedName("ZPOSTNAM")
    private String zpostnam;

    /**
     * 所属组织编码
     */
    @TableField("ZPS_ORG")
    @SerializedName("ZPS_ORG")
    private String zpsOrg;

    /**
     * 状态（A-有效，I-无效）
     */
    @TableField("ZSTAS")
    @SerializedName("ZSTAS")
    private String zstas;

    /**
     * 删除标记（X-已删除，空值-有效）
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
