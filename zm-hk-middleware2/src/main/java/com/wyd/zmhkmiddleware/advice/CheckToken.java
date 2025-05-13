package com.wyd.zmhkmiddleware.advice;

import java.lang.annotation.*;

/**
 * @author Stone
 * @since 2025-05-12
 */

@Target(ElementType.METHOD) // 只作用于方法
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckToken {
}
