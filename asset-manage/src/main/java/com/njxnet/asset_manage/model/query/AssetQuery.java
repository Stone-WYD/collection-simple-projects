package com.njxnet.asset_manage.model.query;

import com.njxnet.asset_manage.model.query.common.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: asset-manage
 * @description: 资产查询query类
 * @author: Stone
 * @create: 2024-03-19 11:49
 **/
@Data
public class AssetQuery extends CommonQuery {

    // 项目名称
    @ApiModelProperty(notes = "项目名称", name = "projectName")
    private String projectName;

    // 客户名称
    @ApiModelProperty(notes = "客户名称", name = "customName")
    private String customName;

    @ApiModelProperty(notes = "是否存在应收账款：1.存在，0.不存在", name = "accountsReceivable")
    private Integer accountsReceivable;

    @ApiModelProperty(notes = "项目状态：1. 在保，0. 过保", name = "projectStatus")
    private Integer projectStatus;


}

