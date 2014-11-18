package com.peaceful.auth.util;

/**
 * Created by wj on 14-4-26.
 */
public class Response {

    public int defineState;
    public int realityState;
    public String level;
    public String detailDesc;

    public Response(int defineState,int realityState, String level, String detail) {
        this.defineState = defineState;
        this.realityState=realityState;
        this.level = level;
        this.detailDesc = detail;
    }
}
