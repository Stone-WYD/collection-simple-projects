package com.njxnet.asset_manage.model.query;

import com.njxnet.asset_manage.model.AssetDTO;
import com.njxnet.asset_manage.model.query.common.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

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

}

