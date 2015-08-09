package com.peaceful.auth.data.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * sdk与service center 通信时，需要发送sdk的缓存数据版本currentVersion  sdk的token sdk的systemId
 * <p/>
 * 同时系统更新时也要更新服务端的数据版本信息
 * <p/>
 * Date 14-9-23.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class VCS implements Serializable {

    // 系统唯一标识
    private static Integer systemId;
    // 数据版本
    private static long currentVersion;
    // 通信token认证
    private static long token;
    private static Logger logger = LoggerFactory.getLogger(VCS.class);

    static {
        currentVersion = System.currentTimeMillis();
    }

    public static long getCurrentVersion() {
        return currentVersion;
    }

    public static synchronized void setCurrentVersion(Long currentVersion) {
        VCS.currentVersion = currentVersion;
        logger.info("client sdk update vcs, new vcs value is {}", currentVersion);
    }


    public static synchronized void updateCurrentVersion() {
        currentVersion = System.currentTimeMillis();
        logger.info("auth service center update vcs, new  value is {}", currentVersion);
    }

    public static void setCurrentVersion(long currentVersion) {
        VCS.currentVersion = currentVersion;
    }

    public static long getToken() {
        return token;
    }

    public static void setToken(long token) {
        VCS.token = token;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        VCS.logger = logger;
    }
}
