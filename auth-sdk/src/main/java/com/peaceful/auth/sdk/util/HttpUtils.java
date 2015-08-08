package com.peaceful.auth.sdk.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于httpClient 实现sdk 与 权限中心通信
 * <p/>
 * Date 14-9-28.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class HttpUtils {

    final static private Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String get(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        String result = null;
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            logger.error("get:{}", e);
        } finally {
            try {
                httpclient.close();
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.debug("get url is {} response data is {}", url, result);
        return result == null ? "" : result.trim();
    }


    public static String post(String url, Map<String, String> data) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (data != null) {
            for (String key : data.keySet()) {
                nvps.add(new BasicNameValuePair(key, data.get(key)));
            }
        }
        CloseableHttpResponse response = null;
        String result = null;
        try {
            UrlEncodedFormEntity entity_ = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
            httpPost.setEntity(entity_);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (UnsupportedEncodingException e) {
            logger.error("post:{}", e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.debug("post url is {} response data is {}", url, result);
        return result == null ? "" : result.trim();

    }


    public static String get(String url, Map<String, String> data) {
        return null;
    }

    public static String post(String url) {
        return null;
    }


}
