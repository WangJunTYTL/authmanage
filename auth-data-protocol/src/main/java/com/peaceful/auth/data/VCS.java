package com.peaceful.auth.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Date 14-9-23.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class VCS implements Serializable {

    private static long currentVersion;
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
}
