package org.wyd.front.impl.row;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.wyd.front.impl.RowDataHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xh
 * @date 2025-02-11
 * @Description:
 */
@Component
@ConditionalOnBean(RowDataHandler.class)
public class PreTreatHandler {

    private Letter2NameMap letter2NameMap;

    public List<LocationInfo> handle(String filePath) {

        Map<String, LocationInfo> locationInfoMap = new HashMap<>();
        // 读取 code 到 name 的映射文件，这部分内容会放在 excel 文件的 sheet2 部分中
        letter2NameMap = new Letter2NameMap(filePath);

        // 读取预处理文件
        EasyExcel.read(filePath, PreTreatInfo.class, new PageReadListener<PreTreatInfo>(dataList -> dataList.forEach(preTreatInfo -> {
            try {
                List<LocationInfo> infoFromData = getLocationInfoFromData(preTreatInfo);

                for (LocationInfo ifd : infoFromData) {
                    if (StrUtil.isEmpty(ifd.getCode())) continue;
                    LocationInfo info = locationInfoMap.get(ifd.getCode());
                    if (info == null) {
                        locationInfoMap.put(ifd.getCode(), ifd);
                    } else {
                        // 完善点位
                        Integer type = preTreatInfo.getType();
                        if (type == 1) {
                            if (StrUtil.isNotEmpty(info.getLocationX())) {
                                // 重复情况
                                info.setLocationX(info.getLocationX() + "，重复：" + ifd.getLocationX());
                            } else info.setLocationX(ifd.getLocationX());

                        } else if (type == 2) {
                            if (StrUtil.isNotEmpty(info.getLocationY())) {
                                info.setLocationY(info.getLocationY() + "，重复：" + ifd.getLocationY());
                            } else info.setLocationY(ifd.getLocationY());
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }))).sheet().doRead();
        // 筛选x,y完整的点放前面
        List<LocationInfo> result = new ArrayList<>();
        List<LocationInfo> lackInfo = new ArrayList<>();
        List<LocationInfo> noInfo = new ArrayList<>();

        Set<String> allCode = letter2NameMap.getAllCode();

        if (CollectionUtil.isEmpty(allCode)) {
            return new ArrayList<>(locationInfoMap.values());
        }

        for (String code : allCode) {
            LocationInfo locationInfo = locationInfoMap.get(code);
            if (locationInfo == null) {
                // 排查缺失的点
                LocationInfo info = new LocationInfo();
                info.setCode(code);
                info.setName(letter2NameMap.getName(code));
                noInfo.add(info);
                continue;
            }
            // 找出不完整的点
            if (StrUtil.isEmpty(locationInfo.getLocationX()) || StrUtil.isEmpty(locationInfo.getLocationY())) {
                lackInfo.add(locationInfo);
                continue;
            }
            result.add(locationInfo);
        }
        // 不完整的点放后面
        if (CollectionUtil.isNotEmpty(lackInfo)) {
            result.addAll(lackInfo);
        }
        if (CollectionUtil.isNotEmpty(noInfo)) {
            result.addAll(noInfo);
        }
        return result;
    }

    private List<LocationInfo> getLocationInfoFromData(PreTreatInfo preTreatInfo) {
        // 从一条记录中提取出 LocationInfo
        List<LocationInfo> result = new ArrayList<>();
        if (StrUtil.isEmpty(preTreatInfo.getCode())) return result;
        Integer type = preTreatInfo.getType();
        String code = preTreatInfo.getCode().trim();
        String location = preTreatInfo.getLocation();
        result.add(getLocationInfo(code, type, location));
        // 其他点
        String otherCodes = preTreatInfo.getOtherCodes();
        if (StrUtil.isNotEmpty(otherCodes)) {
            String[] split = otherCodes.split(",");
            for (String o : split) {
                String oc = o.trim();
                result.add(getLocationInfo(oc, type, location));
            }
        }
        return result;
    }

    private LocationInfo getLocationInfo(String code, Integer type, String location) {
        LocationInfo codeInfo = new LocationInfo();
        codeInfo.setName(letter2NameMap.getName(code));
        codeInfo.setCode(code);
        String locationNum = String.valueOf((int) (Double.parseDouble(location) * 100));
        if (type == 1) {
            codeInfo.setLocationX(locationNum);
        } else if (type == 2){
            codeInfo.setLocationY(locationNum);
        }
        return codeInfo;
    }
}
