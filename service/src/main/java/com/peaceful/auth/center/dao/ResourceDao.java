package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJResource;

/**
 * Created by wangjun on 14-4-15.
 */
public interface ResourceDao {

    DJResource findResourceByResourceId(Integer id);
    DJResource findResourceByResourceUrl(String url,Integer systemId);

    abstract void inserte(DJResource resource);

    abstract void update(DJResource resource);

    abstract void delate(DJResource resource);

}
