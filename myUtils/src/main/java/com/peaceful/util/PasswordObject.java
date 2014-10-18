package com.peaceful.util;

/**
 * Date 14-10-11.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class PasswordObject {

    public int expireRemainDay = 0;
    public  boolean isExpire;

    PasswordObject() {

    }

    void setExpireRemainDay(int expireRemainDay) {
        this.expireRemainDay = expireRemainDay;
        if (this.expireRemainDay < 1)
            this.isExpire = true;
        else
            this.isExpire = false;
    }
}
