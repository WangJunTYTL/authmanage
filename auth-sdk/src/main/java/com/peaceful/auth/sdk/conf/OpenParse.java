package com.peaceful.auth.sdk.conf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.peaceful.auth.data.domain.JSONSystem;
import com.peaceful.auth.sdk.exception.CreateSessionException;
import com.peaceful.auth.sdk.util.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangJun <wangjuntytl@163.com>
 * @version 1.0 15/8/8
 * @since 1.6
 */

public class OpenParse {

    static Logger logger = LoggerFactory.getLogger(OpenParse.class);

    public static JSONSystem getSystemInfo() {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("appkey", SDKConf.appkey));
        pairs.add(new BasicNameValuePair("secret", SDKConf.secret));
        String res = HttpClient.post(SDKConf.system_basic_info,pairs);
        JSONObject data = JSON.parseObject(res);
        if (data.getInteger("code").equals(Integer.valueOf(0))) {
            return JSON.parseObject(data.getString("data"), JSONSystem.class);
        } else if (data.getInteger("code").equals(Integer.valueOf(1))) {
            throw new CreateSessionException("未在权限中心注册服务，有关配置信息请访问：" + SDKConf.config_info);
        } else {
            throw new CreateSessionException("不能与权限系统建立连接，也许权限系统正在维护中或你配置的权限服务地址是错误的，有关配置信息请访问：" + SDKConf.config_info);
        }
    }
}
