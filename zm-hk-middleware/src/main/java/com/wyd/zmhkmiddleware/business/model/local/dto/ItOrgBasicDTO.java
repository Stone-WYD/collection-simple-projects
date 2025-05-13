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
 * 组织基本信息
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("it_org_basic")
@ApiModel(value="ItOrgBasicDTO对象", description="组织基本信息")
public class ItOrgBasicDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "MDG组织编码")
    @TableId("ZORG")
    private String zorg;

    @ApiModelProperty(value = "智慧人事组织编码")
    @TableField("ZHRORG")
    private String zhrorg;

    @ApiModelProperty(value = "所属事业群")
    @TableField("ZBU")
    private String zbu;

    @ApiModelProperty(value = "组织类型")
    @TableField("ZORGTY")
    private String zorgty;

    @ApiModelProperty(value = "组织名称")
    @TableField("ZORGNM")
    private String zorgnm;

    @ApiModelProperty(value = "上级组织编码")
    @TableField("ZORG_F")
    private String zorgF;

    @ApiModelProperty(value = "组织负责人编码")
    @TableField("ZFZRNO")
    private String zfzrno;

    @ApiModelProperty(value = "分管负责人编码")
    @TableField("ZFGFZRNO")
    private String zfgfzrno;

    @ApiModelProperty(value = "法人公司编码")
    @TableField("ZFR_CMP")
    private String zfrCmp;

    @ApiModelProperty(value = "成本中心编码")
    @TableField("ZCOST")
    private String zcost;

    @ApiModelProperty(value = "状态（A-有效，I-无效）")
    @TableField("ZSTAS")
    private String zstas;

    @ApiModelProperty(value = "删除标记（X-已删除，空值-有效）")
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
