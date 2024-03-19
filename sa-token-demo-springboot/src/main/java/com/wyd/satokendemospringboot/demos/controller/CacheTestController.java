package com.wyd.satokendemospringboot.demos.controller;

import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.common.result.MyResultUtil;
import com.wyd.satokendemospringboot.demos.entity.dto.CacheDTO;
import com.wyd.satokendemospringboot.demos.service.CacheTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "cache测试模块")
@RestController("/cacheTest")
public class CacheTestController {

    @Resource
    private CacheTestService cacheTestService;

    // 本地缓存 Caffeine 相关接口
    @GetMapping("/caffine/get")
    @ApiOperation(value = "caffine缓存获取接口")
    MyResult<CacheDTO> queryUserUseCaffineCache(Long userId){
        MyResult<CacheDTO> result = new MyResult<>();
        CacheDTO data = cacheTestService.queryUserUseCaffineCache(userId);
        result.setData(data);
        return MyResultUtil.getTrueResult(result);
    }

    @GetMapping("/caffine/getAfterDelete")
    @ApiOperation(value = "caffine: 获取对象，获取前删除缓存")
    MyResult<CacheDTO> testQueryAfterDeleteCache(Long userId){
        MyResult<CacheDTO> result = new MyResult<>();
        CacheDTO data = cacheTestService.testQueryAfterDeleteCache(userId);
        result.setData(data);
        return MyResultUtil.getTrueResult(result);
    }

    @GetMapping("/caffine/update")
    @ApiOperation(value = "caffine: 缓存更新接口")
    MyResult<CacheDTO> udpateUserUseCaffineCache(Long userId){
        MyResult<CacheDTO> result = new MyResult<>();
        CacheDTO cacheDTO = cacheTestService.udpateUserUseCaffineCache(userId);
        result.setData(cacheDTO);
        return MyResultUtil.getTrueResult(result);
    }

    @GetMapping("/caffine/remove")
    @ApiOperation(value = "caffine: 缓存删除接口")
    MyResult<String> removeUserCaffineCache(Long userId){
        MyResult<String> result = new MyResult<>();
        String data = cacheTestService.removeUserCaffineCache(userId);
        result.setData(data);
        return MyResultUtil.getTrueResult(result);
    }

    @GetMapping("/caffine/updateAndRemove")
    @ApiOperation(value = "caffine: 缓存更新和删除接口")
    MyResult cachingTest(){
        MyResult<String> result = new MyResult<>();
        cacheTestService.cachingTest();
        result.setData("caffine缓存更新userId==1和删除缓存userId==2");
        return MyResultUtil.getTrueResult(result);
    }

    // 本地缓存 Guava 相关接口
    @GetMapping("/guava/get")
    @ApiOperation(value = "guava: 缓存获取数据")
    MyResult queryDataByGuava(String key){
        return cacheTestService.queryDataByGuava(key);
    }
}
