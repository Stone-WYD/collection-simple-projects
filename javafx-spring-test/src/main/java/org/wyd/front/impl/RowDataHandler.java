package org.wyd.front.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import org.springframework.stereotype.Component;
import org.wyd.back.bean.MonitorCamera;
import org.wyd.back.mapper.MonitorCameraMapper;
import org.wyd.front.DataHandler;
import org.wyd.front.ShowName;
import org.wyd.front.impl.row.LocationInfo;
import org.wyd.front.impl.row.PreTreatHandler;
import org.wyd.util.DataUtils;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xh
 * @date 2025-02-12
 * @Description:
 */
@Component
@ShowName("横纵处理点位")
public class RowDataHandler extends DataHandler {

    // fixme 处理的文件不应该过大，因为没有清除线程变量。但是当前客户端没有使用线程池，所以影响不大，如果改用线程池，这里要考虑改进
    protected static final ThreadLocal<List<LocationInfo>> locationInfoListHolder = new ThreadLocal<>();

    @Resource
    private PreTreatHandler preTreatHandler;

    @Override
    protected void fileHandle(String inputFile) {
        List<LocationInfo> locationInfoList = preTreatHandler.handle(inputFile);
        locationInfoListHolder.set(locationInfoList);
    }

    @Override
    protected String outputFile(String inputFile, String outputDir) {
        List<LocationInfo> locationInfoList = locationInfoListHolder.get();
        String inputFileName = Paths.get(inputFile).getFileName().toString();
        String outputFileName = "output-" + inputFileName;
        String outputFile = outputDir + File.separator + outputFileName;
        EasyExcel.write(outputFile, LocationInfo.class)
                .sheet("点位数据")
                .doWrite(() -> locationInfoList);
        return outputFile;
    }

    @Override
    protected void handleDB() {
        List<MonitorCamera> dbData = convertToDB();
        MonitorCameraMapper mapper = springContext.getBean(MonitorCameraMapper.class);
        mapper.updateByIdOrName(dbData);
    }

    protected List<MonitorCamera> convertToDB() {
        List<LocationInfo> locationInfoList = locationInfoListHolder.get();
        List<MonitorCamera> result = new ArrayList<>();
        if (locationInfoList != null) {
            locationInfoList.forEach(locationInfo -> {
                // 如果为空则跳过
                if (StrUtil.isEmpty(locationInfo.getLocationX()) || StrUtil.isEmpty(locationInfo.getLocationY())) return;
                // 将数据添加到 result 中
                MonitorCamera camera = new MonitorCamera();
                result.add(camera);
                camera.setName(locationInfo.getName());
                camera.setPositionX(Integer.parseInt(locationInfo.getLocationX()));
                camera.setPositionY(Integer.parseInt("-" + locationInfo.getLocationY()));
                camera.setPositionZ(400);
            });
        }
        return result;
    }
}
