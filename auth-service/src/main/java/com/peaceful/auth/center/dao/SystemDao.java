package com.peaceful.auth.center.dao;

import com.peaceful.auth.center.domain.DJSystem;

import java.util.List;


/**
 * Created by wangjun on 14-4-15.
 */
public interface SystemDao {
    List<DJSystem> findAllSystems();

    DJSystem findSystemById(Integer id);

    DJSystem findSystemByAppkay(String appkey);

    DJSystem findSystemByAppkayAndSecret(String appkey, String secret);

    DJSystem findLiveSystemById(Integer id);

    DJSystem findSystemByName(String name);

    void inserte(DJSystem system);

    void update(DJSystem system);

    void delate(DJSystem system);

}
