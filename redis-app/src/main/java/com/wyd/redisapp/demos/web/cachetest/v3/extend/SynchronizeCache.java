package com.wyd.redisapp.demos.web.cachetest.v3.extend;

import com.wyd.redisapp.demos.utils.AsyncUtil;
import com.wyd.redisapp.demos.utils.IpUtil;
import com.wyd.redisapp.demos.utils.SpringUtil;

/**
 * @program: redis-app
 * @author: Stone
 * @create: 2024-05-10 14:35
 * @des: 同步数据
 */
public abstract class SynchronizeCache {

    protected static final String localIp;

    static {
        try {
            localIp = IpUtil.getLocalIp4Address() + ":" + SpringUtil.getConfig("server.port");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void init() {
        AsyncUtil.execute(this::comsumeMessage);
    }

    public abstract void produceMessage(String localCacheId);

    protected abstract void comsumeMessage();
}