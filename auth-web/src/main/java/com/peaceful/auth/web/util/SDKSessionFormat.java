package com.peaceful.auth.web.util;

import java.io.Serializable;
import java.util.Map;

/**
 * @author WangJun <wangjuntytl@163.com>
 * @version 1.0 15/8/8
 * @since 1.6
 */

public class SDKSessionFormat implements Serializable{

    public int code;
    public String message;
    public Object data;

    public SDKSessionFormat(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public SDKSessionFormat(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
