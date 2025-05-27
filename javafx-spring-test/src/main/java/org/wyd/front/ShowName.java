package org.wyd.front;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xh
 * @date 2025-02-14
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ShowName {
    String value() default "";
}
