package com.wyd.feignsimpletest;

import com.wyd.feignsimpletest.kaiheyun.client.KhFeignClient;
import com.wyd.feignsimpletest.kaiheyun.model.result.CommonResult;
import com.wyd.feignsimpletest.kaiheyun.model.result.device.DeviceContent;
import com.wyd.feignsimpletest.kaiheyun.model.result.employee.EmployeeContent;
import com.wyd.feignsimpletest.kaiheyun.model.result.point.PointContent;
import com.wyd.feignsimpletest.kaiheyun.model.result.record.RecordContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FeignSimpleTestApplicationTests {

    @Autowired
    private KhFeignClient feignClient;

    @Test
    void contextLoads() {
        System.out.println("=========调用人员信息接口==================");
        CommonResult<List<EmployeeContent>> employees = feignClient.getEmployees();
        List<EmployeeContent> contents = employees.getContent();
        if (contents!=null) {
            System.out.println(contents.get(0).getStation());
        }
        System.out.println("=========调用人员信息接口==================");

        System.out.println("=========调用设备信息接口==================");
        CommonResult<List<DeviceContent>> devices = feignClient.getDevices();
        List<DeviceContent> deviceContents = devices.getContent();
        if (deviceContents!=null) {
            System.out.println(deviceContents.get(0).getStation());
        }
        System.out.println("=========调用设备信息接口==================");

        System.out.println("=========调用获取巡更记录接口==================");
        CommonResult<List<RecordContent>> records = feignClient.getRecords("2025-03-01 10:34:24", "2025-04-01 14:34:24");
        List<RecordContent> recordContents = records.getContent();
        if (recordContents!=null) {
            System.out.println(recordContents.get(0).getStationName());
        }
        System.out.println("=========调用获取巡更记录接口==================");


        System.out.println("=========调用获取巡更点接口==================");
        CommonResult<List<PointContent>> points = feignClient.getPoints();
        List<PointContent> pointContents = points.getContent();
        if (pointContents!=null) {
            System.out.println(pointContents.get(0).getStation());
        }
        System.out.println("=========调用获取巡更点接口==================");


    }

}
