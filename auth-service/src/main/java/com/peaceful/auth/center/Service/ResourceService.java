package com.peaceful.auth.center.Service;

import com.peaceful.auth.center.domain.DJResource;

/**
 * Created by wangjun on 14-4-17.
 */
public interface ResourceService {
    void insertResource(DJResource resource);
    DJResource findResourceByResourceUrl(String url,Integer systemId);
    DJResource findResourceByResourceId(Integer resourceId);
    void updateResource(DJResource resource);

}
