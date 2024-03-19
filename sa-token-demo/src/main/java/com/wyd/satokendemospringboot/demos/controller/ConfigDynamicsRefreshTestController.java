package com.wyd.satokendemospringboot.demos.controller;

import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.common.result.MyResultUtil;
import com.wyd.satokendemospringboot.demos.model.dto.DynamicsConfigDTO;
import com.wyd.satokendemospringboot.demos.service.DynamicsConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @program: sa-token-demo-springboot
 * @description: 动态刷新配置测试类
 * @author: Stone
 * @create: 2023-07-19 11:36
 **/
@RefreshScope
@RestController
@Api(tags = "动态刷新配置测试模块")
@RequestMapping("/dynamic/fresh")
public class ConfigDynamicsRefreshTestController {

    @Resource
    private DynamicsConfigService dynamicsConfigService;

    @Value("${config.exposure.name}")
    private String name;

    @Value("${config.exposure.version}")
    private String version;


    /**
    * @Description: 系统刚启动时从数据库获取配置数据，刷新配置
    * @Author: Stone
    * @Date: 2023/7/19
    */
    @PostConstruct
    public void init(){
        dynamicsConfigService.init();
    }


    @ApiOperation("配置获取")
    @PostMapping("/config/get")
    public MyResult getConfig(){
        return dynamicsConfigService.getConfig();
    }

    @ApiOperation("修改配置")
    @PostMapping("/config/update")
    public MyResult updateConfig(@RequestBody List<DynamicsConfigDTO> dtoList){
        return dynamicsConfigService.updateConfig(dtoList);
    }

    @ApiOperation("测试修改效果")
    @PostMapping("/config/show")
    public MyResult showConfig(){
        MyResult<Object> myResult = new MyResult<>();
        myResult.setData("name: " + name + ";" + "version: " + version );
        return MyResultUtil.getTrueResult(myResult);
    }

    @ApiOperation("测试修改效果2")
    @PostMapping("/config/show2")
    public MyResult showConfig2(){
        return dynamicsConfigService.showConfig2();
    }

}
