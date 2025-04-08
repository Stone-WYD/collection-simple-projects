package com.wyd.feignsimpletest.kaiheyun.client;


import com.wyd.feignsimpletest.kaiheyun.annotation.KhLoginCheck;
import com.wyd.feignsimpletest.kaiheyun.model.param.KhLoginParam;
import com.wyd.feignsimpletest.kaiheyun.model.result.CommonResult;
import com.wyd.feignsimpletest.kaiheyun.model.result.device.DeviceContent;
import com.wyd.feignsimpletest.kaiheyun.model.result.employee.EmployeeContent;
import com.wyd.feignsimpletest.kaiheyun.model.result.login.LoginContent;
import com.wyd.feignsimpletest.kaiheyun.model.result.point.PointContent;
import com.wyd.feignsimpletest.kaiheyun.model.result.record.RecordContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xh
 * @date 2025-01-05
 * @Description:
 */
@FeignClient(name = "test", url = "${feign.kaihe.url}", configuration = KhFeignConfig.class)
public interface KhFeignClient {


    @PostMapping("/api/v2/users/login")
    CommonResult<LoginContent> login(@RequestBody KhLoginParam param);

    // 获取员工信息
    @KhLoginCheck
    @GetMapping("api/v2/employees")
    CommonResult<List<EmployeeContent>> getEmployees();

    // 获取巡更设备
    @KhLoginCheck
    @GetMapping("api/v2/Devices")
    CommonResult<List<DeviceContent>> getDevices();

    // 获取巡更记录
    @KhLoginCheck
    @GetMapping("api/v2/records")
    CommonResult<List<RecordContent>> getRecords(@RequestParam("dateQueryDto.begin") String begin, @RequestParam("dateQueryDto.end") String end);

    // 获取巡更点
    @KhLoginCheck
    @GetMapping("api/v2/places")
    CommonResult<List<PointContent>> getPoints();



}
