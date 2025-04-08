package com.wyd.feignsimpletest.kaiheyun.annotation;

import java.lang.annotation.*;

/**
 * @author xh
 * @date 2025-04-08
 * @Description: 不要把这个注解加到登录接口上，否则会循环登录导致栈溢出
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KhLoginCheck {
}
