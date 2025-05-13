package com.jgdsun.ba.framework.util;

/**
 * @author Stone
 * @since 2025-04-30
 */
public class SimpleCache<T> {

    private T data;
    private long lastUpdateTime;
    private final long expireMiniteTime;

    public SimpleCache(long expireMiniteTime) {
        this.expireMiniteTime = expireMiniteTime;
    }

    public T getData() {
        synchronized (this) {
            if (data == null) return null;
            if (System.currentTimeMillis() - lastUpdateTime > expireMiniteTime * 60 * 1000) {
                data = null;
                return null;
            }
            return data;
        }
    }

    public void setData(T data) {
        synchronized (this) {
            this.data = data;
            this.lastUpdateTime = System.currentTimeMillis();
        }
    }


}
