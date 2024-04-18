package com.njxnet.asset_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.entity.WbInfo;

import java.util.List;

public interface WbInfoService extends IService<WbInfo> {

    AjaxResult change(WbInfo wbInfo);

    AjaxResult<List<WbInfo>> queryByAssetId(Integer assetId);

}
