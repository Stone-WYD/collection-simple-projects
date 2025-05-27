package org.wyd.front.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.wyd.back.bean.MonitorCamera;
import org.wyd.back.mapper.MonitorCameraMapper;
import org.wyd.common.BusinessException;
import org.wyd.front.DataHandler;
import org.wyd.front.ShowName;
import org.wyd.front.bean.BaseExcelData;
import org.wyd.util.DataUtils;

import javax.management.monitor.Monitor;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xh
 * @date 2025-02-07
 * @Description: 默认策略，原坐标和目标坐标都是以东西南北为坐标系确定的点位
 */
@Component
@ShowName("坐标线性映射")
public class BaseDataHandler extends DataHandler {

    // fixme 处理的文件不应该过大，因为没有清除线程变量。但是当前客户端没有使用线程池，所以影响不大，如果改用线程池，这里要考虑改进
    private static final ThreadLocal<List<BaseExcelData>> excelDataListHolder = new ThreadLocal<>();

    @Override
    public void fileHandle(String inputFile) {
        // 重置线程变量
        excelDataListHolder.set(new ArrayList<>());
        List<BaseExcelData> excelDataList = excelDataListHolder.get();
        // 从 excel 表中读取数据
        EasyExcel.read(inputFile, BaseExcelData.class, new PageReadListener<BaseExcelData>(dataList -> excelDataList.addAll(dataList))).sheet().doRead();
        // 处理数据
        if (excelDataList.size() <= 1 &&
                (StringUtils.isEmpty(excelDataList.get(0).getOriLocation()) ||
                        StringUtils.isEmpty(excelDataList.get(0).getTargetLocation()))) {
            throw new BusinessException("输入文件的第一行需要有完整数据");
        }
        BaseExcelData firstData = excelDataList.get(0);
        String oriLocation = firstData.getOriLocation();
        String targetLocation = firstData.getTargetLocation();
        Double[] firstOriArray = DataUtils.location2Array(oriLocation);
        Double[] firstTargetArray = DataUtils.location2Array(targetLocation);
        double k = firstTargetArray[0] / firstOriArray[0];
        // 生成目标坐标
        for (BaseExcelData excelData : excelDataList) {
            if (!StringUtils.isEmpty(excelData.getOriLocation()) &&
                    StringUtils.isEmpty(excelData.getTargetLocation())) {
                // 有源坐标则进行处理，没有则忽略
                Double[] oriArray = DataUtils.location2Array(excelData.getOriLocation());
                Double[] targetArray = new Double[oriArray.length];
                for (int i = 0; i < oriArray.length; i++) {
                    Double ori = oriArray[i];
                    Double fori = firstOriArray[i];
                    targetArray[i] = firstTargetArray[i] + k * (ori - fori);
                }
                excelData.setTargetLocation(DataUtils.array2Location(targetArray));
            }
        }
    }

    @Override
    public String outputFile(String inputFile, String outputDir) {
        List<BaseExcelData> excelDataList = excelDataListHolder.get();
        String inputFileName = Paths.get(inputFile).getFileName().toString();
        String outputFileName = "output-" + inputFileName;
        String outputFile = outputDir + File.separator + outputFileName;
        EasyExcel.write(outputFile, BaseExcelData.class)
                .sheet("点位数据")
                .doWrite(() -> excelDataList);
        return outputFile;
    }

    @Override
    protected void handleDB() {
        List<MonitorCamera> dbData = convertToDB();
        MonitorCameraMapper mapper = springContext.getBean(MonitorCameraMapper.class);
        mapper.updateByIdOrName(dbData);
    }

    public List<MonitorCamera> convertToDB() {
        // 将 BaseExcelData 转换为 MonitorCamera
        List<BaseExcelData> excelDataList = excelDataListHolder.get();
        List<MonitorCamera> result = new ArrayList<>();
        if (excelDataList != null) {
            // 将坐标转换一下
            excelDataList.forEach(excelData -> {
                MonitorCamera camera = new MonitorCamera();
                result.add(camera);
                camera.setId(excelData.getId());
                camera.setName(excelData.getName());
                String targetLocation = excelData.getTargetLocation();
                Double[] targetArray = DataUtils.location2Array(targetLocation);
                if (targetArray.length >= 2) {
                    camera.setPositionX((int) Math.round(targetArray[0]));
                    camera.setPositionY((int) Math.round(targetArray[1]));
                    camera.setPositionZ(0);
                }
            });
        }
        return result;
    }

}
