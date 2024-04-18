package com.njxnet.asset_manage.model;

import com.njxnet.asset_manage.entity.WbInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: asset-manage
 * @description: assert实体类对应的vo类
 * @author: Stone
 * @create: 2024-03-19 11:11
 **/
@Data
public class AssetDTO implements Serializable {
    //主键
    @ApiModelProperty(notes = "主键", name = "id")
    @NotNull(message = "更新时id不能为空", groups = {update.class})
    private Integer id;
    //项目名称
    @ApiModelProperty(notes = "项目名称", name = "projectName")
    @NotBlank(message = "项目名称不能为空", groups = {insert.class})
    private String projectName;
    //客户名称
    @ApiModelProperty(notes = "客户名称", name = "customName")
    private String customName;
    //项目状态：1. 在保，0. 过保
    @ApiModelProperty(notes = "项目状态：1. 在保，0. 过保", name = "projectStatus")
    private Integer projectStatus;
    //项目开始时间
    @ApiModelProperty(notes = "项目开始时间", name = "beginTime")
    private LocalDateTime beginTime;
    //项目结束时间
    @ApiModelProperty(notes = "项目结束时间", name = "endTime")
    private LocalDateTime endTime;
    //是否存在应收账款：1.存在，0.不存在
    @ApiModelProperty(notes = "是否存在应收账款：1.存在，0.不存在", name = "accountsReceivable")
    private Integer accountsReceivable;
    //应收账款金额
    @ApiModelProperty(notes = "应收账款金额", name = "amountReceivable")
    private Double amountReceivable;
    //合同额
    @ApiModelProperty(notes = "合同额", name = "amountContract")
    private Double amountContract;

    private List<WbInfo> wbInfoList;

    public interface insert{}
    public interface update{}
}

