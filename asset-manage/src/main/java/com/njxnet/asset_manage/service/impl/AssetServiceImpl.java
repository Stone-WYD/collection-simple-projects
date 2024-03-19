package com.njxnet.asset_manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.common.AjaxResultUtil;
import com.njxnet.asset_manage.dao.AssetDao;
import com.njxnet.asset_manage.entity.Asset;
import com.njxnet.asset_manage.model.AssetDTO;
import com.njxnet.asset_manage.model.query.AssetQuery;
import com.njxnet.asset_manage.service.AssetService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Asset)表服务实现类
 *
 * @author makejava
 * @since 2024-03-19 10:57:00
 */
@Service("assetService")
public class AssetServiceImpl extends ServiceImpl<AssetDao, Asset> implements AssetService {

    @Override
    public AjaxResult<?> insertAsset(AssetDTO assetDTO) {
        Asset asset = BeanUtil.copyProperties(assetDTO, Asset.class);
        save(asset);
        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }

    @Override
    public AjaxResult<?> updateAsset(AssetDTO assetDTO) {
        Asset asset = BeanUtil.copyProperties(assetDTO, Asset.class);
        updateById(asset);
        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }

    @Override
    public AjaxResult<List<Asset>> queryAsset(AssetQuery query) {
        return null;
    }


}

