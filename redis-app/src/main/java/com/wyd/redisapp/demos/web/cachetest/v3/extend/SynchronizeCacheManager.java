package com.wyd.redisapp.demos.web.cachetest.v3.extend;

import com.wyd.redisapp.demos.utils.SpringUtil;

/**
 * @program: redis-app
 * @author: Stone
 * @create: 2024-05-11 10:05
 */
public class SynchronizeCacheManager {

    public SynchronizeCacheManager() {
        SynchronizeCache synchronizeCache = SpringUtil.getBean(SynchronizeCache.class);
        synchronizeCache.init();
    }
}