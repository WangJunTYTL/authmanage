package com.peaceful.auth.center.dao;

import com.peaceful.auth.center.domain.DJSystem;

import java.util.List;


/**
 * Created by wangjun on 14-4-15.
 */
public interface SystemDao {
    abstract List<DJSystem> findAllSystems();
    abstract DJSystem findSystemById(Integer id);
    abstract DJSystem findLiveSystemById(Integer id);
    abstract DJSystem findSystemByName(String name);
    abstract void inserte(DJSystem system);
    abstract void update(DJSystem system);
    abstract void delate(DJSystem system);

}
