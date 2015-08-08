package com.peaceful.auth.web.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wangjun on 14-4-23.
 */
public class JSONUtil {

    public static String State(String key,Object value){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key,value);
        return jsonObject.toJSONString();
    }
}
