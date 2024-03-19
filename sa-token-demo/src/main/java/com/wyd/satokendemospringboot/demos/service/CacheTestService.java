package com.wyd.satokendemospringboot.demos.service;

import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.entity.MyUser;
import com.wyd.satokendemospringboot.demos.entity.dto.CacheDTO;

public interface CacheTestService {

    CacheDTO queryUserUseCaffineCache(Long userId);

    CacheDTO testQueryAfterDeleteCache(Long userId);

    CacheDTO udpateUserUseCaffineCache(Long userId);

    String removeUserCaffineCache(Long userId);

    CacheDTO cachingTest();

    MyResult<MyUser> queryUserWydUseGuavaCache();

    MyResult<String> queryDataByGuava(String key);

}
