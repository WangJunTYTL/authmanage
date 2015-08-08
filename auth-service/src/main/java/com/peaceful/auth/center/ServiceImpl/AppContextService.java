package com.peaceful.auth.center.ServiceImpl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 注册spring applicationContext 到EdaijiaContext
 * <p/>
 * Created by wangjun on 15/3/17.
 */
@Component
public class AppContextService implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
