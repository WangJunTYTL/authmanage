package com.peaceful.auth.data;


import java.io.Serializable;

/**
 * Date 14-9-23.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class VCS implements Serializable {

    private static long currentVersion;

    static {
        currentVersion = System.currentTimeMillis();
    }

    public static long getCurrentVersion() {
        return currentVersion;
    }

    public static synchronized void setCurrentVersion(Long currentVersion) {
        VCS.currentVersion = currentVersion;
        System.out.println("客户端VCS修改成功，当前版本；"+currentVersion);
    }


    public static synchronized void updateCurrentVersion() {
        currentVersion = System.currentTimeMillis();
        System.out.println("服务中心VCS修改成功，当前版本；"+currentVersion);
    }
}
