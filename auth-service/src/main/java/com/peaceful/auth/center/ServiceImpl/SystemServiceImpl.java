package com.peaceful.auth.center.ServiceImpl;

import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.dao.SystemDao;
import com.peaceful.auth.center.domain.DJFunction;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJSystem;
import com.peaceful.auth.data.util.MD5Utils;
import com.peaceful.auth.center.util.HibernateSystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "systemService")
public class SystemServiceImpl implements SystemService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SystemDao systemDao = null;

    public void setSystemDao(SystemDao systemDao) {
        this.systemDao = systemDao;
    }

    @Override
    public Map<Integer, String> refreshTokensOfAllSystem() {
        List<DJSystem> systems = systemDao.findAllSystems();
        for (DJSystem system : systems) {
            allTokens.put(system.getId(), system.token);
        }
        return allTokens;
    }

    public DJSystem findSystemByName(String name) {
        return systemDao.findSystemByName(name);
    }

    @Override
    public boolean systemIsExist(Integer systemId) {
        return systemDao.findSystemById(systemId) == null ? false : true;
    }

    public List<DJSystem> findRolesSortBySystem() {
        List<DJSystem> djSystems = systemDao.findAllSystems();
        HibernateSystemUtil.systemLoad(djSystems, HibernateSystemUtil.ROLE);
        return djSystems;
    }

    public List<DJRole> findRolesBySystemId(Integer systemId) {
        DJSystem system = systemDao.findSystemById(systemId);
        HibernateSystemUtil.load(system, HibernateSystemUtil.ROLE);
        if (system == null || system.roles == null)
            return null;
        Set<DJRole> roles = system.roles;
        List<DJRole> roleList = new ArrayList<DJRole>();
        for (DJRole role : roles) {
            roleList.add(role);
        }
        return roleList;
    }


    @Override
    public List<DJFunction> findFunctionsBySystemId(Integer systemId) {
        Set<DJFunction> functions = systemDao.findSystemById(systemId).functions;
        List<DJFunction> result = new ArrayList<DJFunction>();
        for (DJFunction function : functions) {
            logger.info("load parent function {}", function.parentFunction == null ? null : function.parentFunction.id);
            result.add(function);
        }
        return result;
    }


    public DJSystem findLiveSystemBySystemId(Integer systemId) {
        return systemDao.findLiveSystemById(systemId);
    }

    public List<DJSystem> findUsersSortBySystem() {
        List<DJSystem> djSystems = systemDao.findAllSystems();
        HibernateSystemUtil.systemLoad(djSystems, HibernateSystemUtil.USER);
        return djSystems;
    }

    public List<DJSystem> findResourcesSortBySystem() {

        List<DJSystem> djSystems = systemDao.findAllSystems();
        HibernateSystemUtil.systemLoad(djSystems, HibernateSystemUtil.RESOURCE);
        return djSystems;
    }

    public List<DJSystem> findFunctionsSortBySystem() {

        List<DJSystem> djSystems = systemDao.findAllSystems();
        HibernateSystemUtil.systemLoad(djSystems, HibernateSystemUtil.FUNCTION);
        return djSystems;
    }


    public List<DJSystem> findAllSystems() {
        return systemDao.findAllSystems();
    }

    public void insertSystem(DJSystem system) {
        system.setAppKey(MD5Utils.string2MD5(String.valueOf(System.nanoTime())));
        systemDao.inserte(system);
    }

    public DJSystem findSystemBySystemId(Integer systemId) {
        return systemDao.findSystemById(systemId);
    }

    @Override
    public DJSystem findSystemByAppkey(String appkey) {
        return systemDao.findSystemByAppkay(appkey);
    }

    @Override
    public DJSystem findSystemByAppkeyAndSecret(String appkey, String secret) {
        return systemDao.findSystemByAppkayAndSecret(appkey, secret);
    }

    public DJSystem findSystemBySystemId(Integer systemId, Integer loadType) {
        DJSystem system = systemDao.findLiveSystemById(systemId);
        if (loadType == HibernateSystemUtil.ROLE) {
            logger.info("load roles {}", system.roles);
        } else if (loadType == HibernateSystemUtil.ROLEANDUSER) {
            logger.info("load roles {} and users", system.roles.size(), system.roles.iterator().hasNext());
        } else if (loadType == HibernateSystemUtil.FUNCTIONANDPARENT) {
            logger.info("load functions size {}", system.functions.size());
            if (system.functions != null) {
                for (DJFunction function : system.functions) {
                    logger.info("load function {}", function.parentFunction);
                }
            }
        } else if (loadType == HibernateSystemUtil.FUNCTION) {
            logger.info("load functions size {}", system.functions.size());
        } else if (loadType == HibernateSystemUtil.USER) {
            logger.info("load users size {}", system.users.size());
        } else if (loadType == HibernateSystemUtil.RESOURCE) {
            logger.info("load users size {}", system.resources.size());
        }

        return system;
    }

    public void updateSystem(DJSystem system) {
        systemDao.update(system);
    }


}
