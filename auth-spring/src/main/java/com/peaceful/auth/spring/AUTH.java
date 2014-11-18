package com.peaceful.auth.spring;

import java.lang.annotation.*;

/**
 * 权限有关
 * Created by wangjun on 14-8-28.
 */


public class AUTH {

    /**
     * 通过检测用户是否含有给定的功能点控制
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public static @interface Function {
        public String[] value() default {};

    }

    /**
     * 通过检测用户是否含有给定的角色控制访问
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public static @interface Role {
        public String[] value() default {};

    }
}
