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
 * 员工银行信息
 * </p>
 *
 * @author wyd
 * @since 2025-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("et_empl_bank")
@ApiModel(value="EtEmplBankDTO对象", description="员工银行信息")
public class EtEmplBankDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "智慧人事系统人员编码")
    @TableId("ZHREMPL")
    private String zhrempl;

    @ApiModelProperty(value = "银行信息流水码")
    @TableField("BANK_ID")
    private String bankId;

    @ApiModelProperty(value = "雇佣记录编号")
    @TableField("ZPOSTTY")
    private String zpostty;

    @ApiModelProperty(value = "银行国家")
    @TableField("LAND1")
    private String land1;

    @ApiModelProperty(value = "银行名称")
    @TableField("ZBANKA")
    private String zbanka;

    @ApiModelProperty(value = "银行代码")
    @TableField("BANKL")
    private String bankl;

    @ApiModelProperty(value = "银行账号")
    @TableField("ZBANKN")
    private String zbankn;

    @ApiModelProperty(value = "帐户持有人姓名")
    @TableField("KOINH")
    private String koinh;

    @ApiModelProperty(value = "是否主要账户（X-是，空值-否）")
    @TableField("ZMAIN")
    private String zmain;

    @ApiModelProperty(value = "删除标识符（X-已删除，空值-有效）")
    @TableField("ZDEL_BK")
    private String zdelBk;

    @ApiModelProperty(value = "备用字段1")
    @TableField("ZATTR1_BK")
    private String zattr1Bk;

    @ApiModelProperty(value = "备用字段2")
    @TableField("ZATTR2_BK")
    private String zattr2Bk;

    @ApiModelProperty(value = "备用字段3")
    @TableField("ZATTR3_BK")
    private String zattr3Bk;

    @ApiModelProperty(value = "备用字段4")
    @TableField("ZATTR4_BK")
    private String zattr4Bk;

    @ApiModelProperty(value = "备用字段5")
    @TableField("ZATTR5_BK")
    private String zattr5Bk;

    @ApiModelProperty(value = "备用字段6")
    @TableField("ZATTR6_BK")
    private String zattr6Bk;


}
