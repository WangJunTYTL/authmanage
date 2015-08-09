package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJResource;

/**
 * Created by wangjun on 14-4-15.
 */
public interface ResourceDao {

    DJResource findResourceByResourceId(Integer id);

    DJResource findResourceByResourceUrl(String url, Integer systemId);

    void inserte(DJResource resource);

    void update(DJResource resource);

    void delate(DJResource resource);

}
