package com.wyd.redisapp.demos.web.cachetest.v3;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoubleCache {

    String cacheName();

    String key();

    long l2TimeOut() default 120;

    CacheType type() default CacheType.FULL;

}
