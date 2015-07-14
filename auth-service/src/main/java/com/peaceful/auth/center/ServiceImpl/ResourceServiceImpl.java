package com.peaceful.auth.center.ServiceImpl;

import com.peaceful.auth.center.Service.ResourceService;
import com.peaceful.auth.center.dao.ResourceDao;
import com.peaceful.auth.center.dao.SystemDao;
import com.peaceful.auth.center.domain.DJResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "resourceService")
public class ResourceServiceImpl implements ResourceService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SystemDao systemDao = null;
    @Autowired
    ResourceDao resourceDao = null;

    public void setSystemDao(SystemDao systemDao) {
        this.systemDao = systemDao;
    }

    public void setResourceDao(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }


    public DJResource findResourceByResourceId(Integer id) {
        DJResource resource = resourceDao.findResourceByResourceId(id);
        if (resource.system != null) {
            logger.info("load system id is {}", resource.system.id);
        }
        logger.info("load roles {}", resource.roles);
        return resource;
    }

    public void insertResource(DJResource resource) {
        resourceDao.inserte(resource);
    }

    public DJResource findResourceByResourceUrl(String url, Integer systemId) {
        return resourceDao.findResourceByResourceUrl(url,systemId);
    }

    public void updateResource(DJResource resource) {
        resourceDao.update(resource);
    }

}
