package com.wyd.asset_manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyd.asset_manage.common.AjaxResult;
import com.wyd.asset_manage.entity.Asset;
import com.wyd.asset_manage.model.AssetDTO;
import com.wyd.asset_manage.model.query.AssetQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * (Asset)表服务接口
 *
 * @author makejava
 * @since 2024-03-19 10:56:59
 */
public interface AssetService extends IService<Asset> {

    AjaxResult<?> insertAsset(AssetDTO assetDTO);

    AjaxResult<?> updateAsset(AssetDTO assetDTO);

    AjaxResult<Page<AssetDTO>> queryAsset(AssetQuery query);

    AjaxResult<?> deleteAsset(AssetDTO assetDTO);

    void getExcelTemplate(HttpServletResponse response);

    AjaxResult<?> importDataFromExcel(MultipartFile excelFile);
}

