package com.peaceful.auth.sdk.util;

import com.peaceful.auth.sdk.conf.SDKConf;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjun [wangjuntytl@163.com]
 * @version 1.0
 * @since 15/4/14.
 */

public class APIRequestProxy {

    static Logger logger = LoggerFactory.getLogger(APIRequestProxy.class);
    public static final String APP_SECRET = "02c389a8-adea-11e1-8fd8-8d5a196ddafd";


    public static <T> T getAPI(final Class<T> requiredType) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(requiredType);
        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                    throws Throwable {
                List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
                pairs.addAll(APIBasic.params);
                String url = SDKConf.add+"/sdk";
                com.peaceful.auth.sdk.util.Method method1 = method.getAnnotation(com.peaceful.auth.sdk.util.Method.class);
                if (method1 != null)
                    url += method1.value();
                    Annotation[][] params = method.getParameterAnnotations();
                for (int i = 0; i < params.length; i++) {
                    Param params1 = (Param) params[i][0];
                    pairs.add(new BasicNameValuePair(params1.name(), (String) args[i]));
                }
                String action = method1.action();
                if ("get".equals(action))
                    return HttpClient.get(url, pairs);
                else
                    return HttpClient.post(url, pairs);
            }
        });
        return (T) enhancer.create();
    }


}
