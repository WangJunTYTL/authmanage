package com.peaceful.auth.center.Service;

import com.peaceful.auth.center.domain.DJBean;

/**
 * Created by wangjun on 14-4-17.
 */
public interface BeanService {
    void insertBean(DJBean bean);

    DJBean findBeanByBeankey(String key, Integer systemId);

    DJBean findBeanByBeanId(Integer beanId);

    void updateBean(DJBean bean);
}
