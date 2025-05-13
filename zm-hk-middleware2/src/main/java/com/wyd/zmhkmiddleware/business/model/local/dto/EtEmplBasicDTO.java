package com.wyd.zmhkmiddleware.business.model.local.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工基本信息
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("et_empl_basic")
@ApiModel(value="EtEmplBasicDTO对象", description="员工基本信息")
public class EtEmplBasicDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "MDG人员编码")
    @TableId("ZEMPL")
    private String zempl;

    @ApiModelProperty(value = "智慧人事系统人员编码")
    @TableField("ZHREMPL")
    private String zhrempl;

    @ApiModelProperty(value = "姓名")
    @TableField("ZEMPLNM")
    private String zemplnm;

    @ApiModelProperty(value = "国籍")
    @TableField("REF_POSTA")
    private String refPosta;

    @ApiModelProperty(value = "性别")
    @TableField("ZXB")
    private String zxb;

    @ApiModelProperty(value = "出生日期")
    @TableField("GBDAT")
    private String gbdat;

    @ApiModelProperty(value = "手机")
    @TableField("ZPHONENO")
    private String zphoneno;

    @ApiModelProperty(value = "工作电话")
    @TableField("ZTELNO")
    private String ztelno;

    @ApiModelProperty(value = "证件国家")
    @TableField("ZZJGJ")
    private String zzjgj;

    @ApiModelProperty(value = "证件类型")
    @TableField("ZZJLX")
    private String zzjlx;

    @ApiModelProperty(value = "证件号码")
    @TableField("ZZJHM")
    private String zzjhm;

    @ApiModelProperty(value = "电子邮件地址")
    @TableField("ZEMAIL")
    private String zemail;

    @ApiModelProperty(value = "人员状态（A-有效，T-离职）")
    @TableField("ZSTAS_E")
    private String zstasE;

    @ApiModelProperty(value = "用工类型")
    @TableField("ZYGLX")
    private String zyglx;

    @ApiModelProperty(value = "入职日期")
    @TableField("ZHIRE_DT")
    private String zhireDt;

    @ApiModelProperty(value = "离职日期")
    @TableField("ZLEAVE_DT")
    private String zleaveDt;

    @ApiModelProperty(value = "事业群（人资）")
    @TableField("ZBU")
    private String zbu;

    @ApiModelProperty(value = "删除标识符（X-已删除，空值-有效）")
    @TableField("ZDEL")
    private String zdel;

    @ApiModelProperty(value = "备用字段1")
    @TableField("ZATTR1")
    private String zattr1;

    @ApiModelProperty(value = "备用字段2")
    @TableField("ZATTR2")
    private String zattr2;

    @ApiModelProperty(value = "备用字段3")
    @TableField("ZATTR3")
    private String zattr3;

    @ApiModelProperty(value = "备用字段4")
    @TableField("ZATTR4")
    private String zattr4;

    @ApiModelProperty(value = "备用字段5")
    @TableField("ZATTR5")
    private String zattr5;

    @ApiModelProperty(value = "备用字段6")
    @TableField("ZATTR6")
    private String zattr6;


}
