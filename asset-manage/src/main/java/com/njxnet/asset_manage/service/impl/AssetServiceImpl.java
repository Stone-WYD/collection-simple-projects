package com.njxnet.asset_manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.asset_manage.dao.AssetDao;
import com.njxnet.asset_manage.entity.Asset;
import com.njxnet.asset_manage.service.AssetService;
import org.springframework.stereotype.Service;

/**
 * (Asset)表服务实现类
 *
 * @author makejava
 * @since 2024-03-19 10:57:00
 */
@Service("assetService")
public class AssetServiceImpl extends ServiceImpl<AssetDao, Asset> implements AssetService {

}

