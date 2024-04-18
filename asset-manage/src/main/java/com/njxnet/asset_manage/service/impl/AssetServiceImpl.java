package com.njxnet.asset_manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.asset_manage.common.AjaxResult;
import com.njxnet.asset_manage.common.AjaxResultUtil;
import com.njxnet.asset_manage.common.BaseException;
import com.njxnet.asset_manage.common.ResultStatusCode;
import com.njxnet.asset_manage.dao.AssetDao;
import com.njxnet.asset_manage.dao.WbInfoDao;
import com.njxnet.asset_manage.entity.Asset;
import com.njxnet.asset_manage.entity.WbInfo;
import com.njxnet.asset_manage.model.AssetDTO;
import com.njxnet.asset_manage.model.query.AssetQuery;
import com.njxnet.asset_manage.service.AssetService;
import com.njxnet.asset_manage.service.WbInfoService;
import liquibase.pro.packaged.L;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.njxnet.asset_manage.service.common.MyCommonService.queryForPage;
import static com.njxnet.asset_manage.util.PioUtil.getCellStringValue;

/**
 * (Asset)表服务实现类
 *
 * @author makejava
 * @since 2024-03-19 10:57:00
 */
@Slf4j
@Service("assetService")
public class AssetServiceImpl extends ServiceImpl<AssetDao, Asset> implements AssetService {

    @Resource
    private WbInfoService wbInfoService;

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
    public AjaxResult<Page<AssetDTO>> queryAsset(AssetQuery query) {
        AjaxResult<Page<AssetDTO>> result = queryForPage(AssetDTO.class, () ->
                page(new Page<>(query.getPage(), query.getSize()),
                        query().eq(query.getAccountsReceivable() != null, "accounts_receivable", query.getAccountsReceivable())
                                .eq(query.getProjectStatus() != null, "project_status", query.getProjectStatus())
                                .ge(query.getBeginDate() != null, "begin_time", query.getBeginDate())
                                .le(query.getEndDate() != null, "end_time", query.getEndDate())
                                .like(StrUtil.isNotEmpty(query.getProjectName()), "project_name", "%" + query.getProjectName() + "%")
                                .like(StrUtil.isNotEmpty(query.getCustomName()), "custom_name", "%" + query.getCustomName() + "%")
                                .getWrapper()));
        if (result.getData() != null) {
            List<AssetDTO> records = result.getData().getRecords();
            if (records != null) {
                // 添加维保信息
                for (AssetDTO record : records) {
                    Integer assetId = record.getId();
                    AjaxResult<List<WbInfo>> wbInfoResult = wbInfoService.queryByAssetId(assetId);
                    record.setWbInfoList(wbInfoResult.getData());
                }
            }
        }

        return result;
    }

    @Override
    public AjaxResult<?> deleteAsset(AssetDTO assetDTO) {
        removeById(assetDTO.getId());
        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }

    @Override
    public void getExcelTemplate(HttpServletResponse response) {

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + UUID.randomUUID() + ".xlsx");

        // 创建一个新的Excel工作簿
        Workbook workbook = new XSSFWorkbook();

        // 在工作簿中创建一个新的工作表
        Sheet sheet = workbook.createSheet("template");

        // 向工作表中添加表头
        Row row = sheet.createRow(0);
        for (int i = 0; i < 8; i++) {
            Cell cell = row.createCell(i);
        }
        row.getCell(0).setCellValue("项目名称");
        row.getCell(1).setCellValue(" 客户");
        row.getCell(2).setCellValue("项目状态（输入1：在保，0：过保）");
        row.getCell(3).setCellValue("项目开始时间（输入yyyy-MM-dd hh:mm:ss的日期格式）");
        row.getCell(4).setCellValue("过保时间（输入yyyy-MM-dd hh:mm:ss的日期格式）");
        row.getCell(5).setCellValue("是否存在应收账款（输入1:存在，0：不存在）");
        row.getCell(6).setCellValue("应收账款金额（单位元，最多保留两位小数）");
        row.getCell(7).setCellValue("合同额（单位元，最多保留两位小数）");

        // 将工作簿写入响应输出流
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BaseException(ResultStatusCode.ERROR.getCode(), ResultStatusCode.ERROR.getName());
        }
    }

    @Override
    public AjaxResult<?> importDataFromExcel(MultipartFile excelFile) {
        List<Asset> assets = new ArrayList<>();
        try {
            InputStream inputStream = excelFile.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            // 跳过第一行
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                List<Asset> record = new ArrayList<>();
                XSSFRow row = sheet.getRow(i);
                // 校验表格中每一行数据是否过多或过少
                if (row != null) {
                    Asset asset = new Asset();
                    assets.add(asset);
                    // 最大单元格位置
                    short lastCellNum = row.getLastCellNum();
                    for (int j = 0; j < 8; j++) {
                        if (j <= lastCellNum) {
                            fillAssetWithCell(asset, j, row.getCell(j));
                        }
                    }
                }
            }
            saveBatch(assets);
            return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(ResultStatusCode.ERROR.getCode(), "获取文件流内容出错！");
        }
    }

    private void fillAssetWithCell(Asset asset, int j, XSSFCell cell) {
        if (cell == null) return;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        switch (j) {
            case 0:
                // 项目名称
                asset.setProjectName(getCellStringValue(cell));
                break;
            case 1:
                // 客户名
                asset.setCustomName(getCellStringValue(cell));
                break;
            case 2:
                // 项目状态
                asset.setProjectStatus(Integer.valueOf(getCellStringValue(cell)));
                break;
            case 3:
                // 项目开始时间
                asset.setBeginTime(LocalDateTime.parse(getCellStringValue(cell), formatter));
                break;
            case 4:
                // 项目结束时间
                asset.setEndTime(LocalDateTime.parse(getCellStringValue(cell), formatter));
                break;
            case 5:
                // 是否存在应收账款
                asset.setAccountsReceivable(Integer.valueOf(getCellStringValue(cell)));
                break;
            case 6:
                // 应收账款
                asset.setAmountReceivable(Double.valueOf(getCellStringValue(cell)));
                break;
            case 7:
                // 合同金额
                asset.setAmountContract(Double.valueOf(getCellStringValue(cell)));
                break;
        }
    }


}

