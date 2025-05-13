package com.jgdsun.ba.framework.logincheck.annotation;

import cn.hutool.core.annotation.Alias;

import java.lang.annotation.*;

/**
 * @author xh
 * @date 2025-04-08
 * @Description: 不要把这个注解加到登录接口上，否则会循环登录导致栈溢出
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginCheck {

    @Alias("brandName")
    String value() default "";

}
