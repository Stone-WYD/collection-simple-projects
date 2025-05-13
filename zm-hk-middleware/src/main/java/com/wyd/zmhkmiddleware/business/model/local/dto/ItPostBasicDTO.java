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
 * 岗位基本信息
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("it_post_basic")
@ApiModel(value="ItPostBasicDTO对象", description="岗位基本信息")
public class ItPostBasicDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "MDG岗位编码")
    @TableId("ZPOST")
    private String zpost;

    @ApiModelProperty(value = "智慧人事系统岗位编码")
    @TableField("ZHRPOST")
    private String zhrpost;

    @ApiModelProperty(value = "所属BU/事业群")
    @TableField("ZBU")
    private String zbu;

    @ApiModelProperty(value = "岗位名称")
    @TableField("ZPOSTNAM")
    private String zpostnam;

    @ApiModelProperty(value = "所属组织编码")
    @TableField("ZPS_ORG")
    private String zpsOrg;

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
