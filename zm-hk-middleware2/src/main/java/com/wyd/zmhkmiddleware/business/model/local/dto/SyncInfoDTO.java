package com.wyd.zmhkmiddleware.business.model.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Stone
 * @since 2025-05-12
 */
@Data
public class SyncInfoDTO {


    @ApiModelProperty(value = "智慧人事系统人员编码", required = true)
    @NotBlank(message = "智慧人事系统人员编码不能为空")
    private String zhrempl;

    @ApiModelProperty(value = "姓名")
    private String zemplnm;

    @ApiModelProperty(value = "国籍")
    private String refPosta;

    @ApiModelProperty(value = "性别")
    private String zxb;

    @ApiModelProperty(value = "出生日期")
    private String gbdat;

    @ApiModelProperty(value = "手机")
    private String zphoneno;

    @ApiModelProperty(value = "工作电话")
    private String ztelno;

    @ApiModelProperty(value = "证件国家")
    private String zzjgj;

    @ApiModelProperty(value = "证件类型")
    private String zzjlx;

    @ApiModelProperty(value = "证件号码")
    private String zzjhm;

    @ApiModelProperty(value = "电子邮件地址")
    private String zemail;

    @ApiModelProperty(value = "人员状态（A-有效，T-离职）")
    private String zstasE;


    // 岗位信息 通过 zhrempl 在 etemplpost 中获取
    @ApiModelProperty(value = "岗位编码", required = true)
    @NotBlank(message = "岗位编码不能为空")
    private String zhrpost;

    @ApiModelProperty(value = "职衔")
    private String zzx;

    @ApiModelProperty(value = "职务在岗状态（A-有效，I-无效）")
    private String zstatPs;

    // 岗位具体信息 通过 zhrpost 在 itpostbasic 中获取
    @ApiModelProperty(value = "岗位名称")
    private String zpostnam;

    @ApiModelProperty(value = "所属组织编码", required = true)
    @NotBlank(message = "所属组织编码不能为空")
    private String zpsOrg;

    // 组织名称 通过 zpsOrg 在 itorgbasic（与zhrorg字段对应）中获取
    @ApiModelProperty(value = "组织名称")
    private String zorgnm;

    @ApiModelProperty(value = "是否同步")
    private boolean syncFlag;



}
