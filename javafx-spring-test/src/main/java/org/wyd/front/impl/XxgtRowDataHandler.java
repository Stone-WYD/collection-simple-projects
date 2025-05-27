package org.wyd.front.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;
import org.wyd.back.bean.MonitorCamera;
import org.wyd.front.ShowName;
import org.wyd.front.impl.row.LocationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xh
 * @date 2025-02-14
 * @Description:
 */
@Component
@ShowName("横纵处理点位-信息高铁")
public class XxgtRowDataHandler extends RowDataHandler{

    protected List<MonitorCamera> convertToDB() {
        // 针对信息高铁项目进行了适应性的调整
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
                camera.setPositionY(400);
                camera.setPositionZ(Integer.parseInt("-" + locationInfo.getLocationY()));
            });
        }
        return result;
    }
}
