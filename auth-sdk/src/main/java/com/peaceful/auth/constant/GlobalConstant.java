package com.peaceful.auth.constant;

/**
 * 从classpath:auth.properties读取配置信息，初始化下面常量
 * <p/>
 * Created by wj on 14-4-23.
 */
public class GlobalConstant {
    /**
     * 在权限中心注册服务id
     */
    public static final String SYSTEM_ID = "auth.app.id";
    /**
     * 权限中心地址
     */
    public static final String AUTH_SERVICE_ADDRESS = "auth.service.address";
    /**
     * 获取用户信息时缓存时间
     */
    public static final String USER_SESSION_OUT_TIME = "auth.user.session.out.time";
    /**
     * 获取系统信息时缓存时间
     */
    public static final String SYSTEM_SESSION_OUT_TIME = "auth.system.session.out.time";
}
