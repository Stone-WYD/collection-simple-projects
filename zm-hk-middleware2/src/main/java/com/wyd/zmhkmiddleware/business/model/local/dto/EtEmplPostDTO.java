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
 * 员工岗位信息
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("et_empl_post")
@ApiModel(value="EtEmplPostDTO对象", description="员工岗位信息")
public class EtEmplPostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "智慧人事系统人员编码")
    @TableId("ZHREMPL")
    private String zhrempl;

    @ApiModelProperty(value = "雇佣记录编号")
    @TableField("ZPOSTTY")
    private String zpostty;

    @ApiModelProperty(value = "岗位编码")
    @TableField("ZHRPOST")
    private String zhrpost;

    @ApiModelProperty(value = "职衔")
    @TableField("ZZX")
    private String zzx;

    @ApiModelProperty(value = "职务在岗状态（A-有效，I-无效）")
    @TableField("ZSTAT_PS")
    private String zstatPs;

    @ApiModelProperty(value = "删除标识符（X-已删除，空值-有效）")
    @TableField("ZDEL_PS")
    private String zdelPs;

    @ApiModelProperty(value = "备用字段1")
    @TableField("ZATTR1_PS")
    private String zattr1Ps;

    @ApiModelProperty(value = "备用字段2")
    @TableField("ZATTR2_PS")
    private String zattr2Ps;

    @ApiModelProperty(value = "备用字段3")
    @TableField("ZATTR3_PS")
    private String zattr3Ps;

    @ApiModelProperty(value = "备用字段4")
    @TableField("ZATTR4_PS")
    private String zattr4Ps;

    @ApiModelProperty(value = "备用字段5")
    @TableField("ZATTR5_PS")
    private String zattr5Ps;

    @ApiModelProperty(value = "备用字段6")
    @TableField("ZATTR6_PS")
    private String zattr6Ps;


}
