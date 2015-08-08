package com.peaceful.auth.sdk.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangjun [wangjuntytl@163.com]
 * @version 1.0
 * @since 15/4/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Method {
    String name() default "method";

    String value();

    String action() default "post";
}
