package com.peaceful.util;

import java.util.Date;

/**
 * Date 14-10-11.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class PasswordUtils {

    public static PasswordObject getPasswordObject(Date lastUpdateTime,int expireDay) {
        PasswordObject passwordObject = new PasswordObject();
        passwordObject.setExpireRemainDay(expireDay - (int) ((System.currentTimeMillis() - lastUpdateTime.getTime()) / (24 * 60 * 60 * 1000)));
        return passwordObject;
    }

}
