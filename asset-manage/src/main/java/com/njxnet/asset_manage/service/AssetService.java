package com.njxnet.asset_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.entity.Asset;
import com.njxnet.asset_manage.model.AssetDTO;
import com.njxnet.asset_manage.model.query.AssetQuery;

import java.util.List;

/**
 * (Asset)表服务接口
 *
 * @author makejava
 * @since 2024-03-19 10:56:59
 */
public interface AssetService extends IService<Asset> {

    AjaxResult<?> insertAsset(AssetDTO assetDTO);

    AjaxResult<?> updateAsset(AssetDTO assetDTO);

    AjaxResult<List<Asset>> queryAsset(AssetQuery query);
}

