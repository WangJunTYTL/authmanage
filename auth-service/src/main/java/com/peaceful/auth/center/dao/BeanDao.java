package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJBean;

/**
 * Created by wangjun on 14-4-15.
 */
public interface BeanDao {

    DJBean findBeanByBeanId(Integer id);

    DJBean findBeanByBeankey(String key, Integer systemId);

    void inserte(DJBean bean);

    void update(DJBean bean);

    void delate(DJBean bean);

}
