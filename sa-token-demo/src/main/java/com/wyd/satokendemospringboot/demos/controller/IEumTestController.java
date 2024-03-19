package com.wyd.satokendemospringboot.demos.controller;

import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.entity.dto.IEumContainerDTO;
import com.wyd.satokendemospringboot.demos.service.IeumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SuppressWarnings("rawtypes")
@Api(tags = "枚举类转换测试模块")
@RestController("/ieum")
public class IEumTestController {

    @Resource
    private IeumService ieumService;

    @PostMapping("/test")
    @ApiOperation(value = "获取SpringMVC处理后的传参，枚举类更改一下，然后返回")
    MyResult testIEum(@RequestBody IEumContainerDTO iEumContainerDTO){
        return ieumService.testIEum(iEumContainerDTO);
    }

    @PostMapping("/testWithDataBaseOps")
    @ApiOperation(value = "包含数据库操作")
    MyResult testIEumWithDataBaseOps(@RequestBody IEumContainerDTO iEumContainerDTO){
        return ieumService.testIEumWithDataBaseOps(iEumContainerDTO);
    }
}
