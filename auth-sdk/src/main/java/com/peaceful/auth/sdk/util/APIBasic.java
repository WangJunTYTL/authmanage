package com.peaceful.auth.sdk.util;

import com.peaceful.auth.sdk.conf.SDKConf;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjun [wangjuntytl@163.com]
 * @version 1.0
 * @since 15/4/14.
 */

public class APIBasic {


    /**
     * 请求参数
     */
    public static List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

    static {
        params.add(new BasicNameValuePair("systemId", SDKConf.system_id));
        params.add(new BasicNameValuePair("appkey", SDKConf.appkey));
        params.add(new BasicNameValuePair("secret", SDKConf.secret));
        params.add(new BasicNameValuePair("token", SDKConf.token));
    }
}
