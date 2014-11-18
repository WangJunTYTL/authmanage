package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJMenu;
import com.peaceful.auth.center.domain.DJResource;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJUser;

import java.util.List;

/**
 * Created by wangjun on 14-4-15.
 */
public interface UserDao {

    abstract DJUser findUserByUserId(Integer id);
    abstract DJUser findUserByUserName(String name,Integer systemId);
    abstract DJUser findUserByUserNameAndPassword(String name,Integer systemId,String password);
    List<DJResource> findCanAccessResourcesOfUserOfSystem(Integer id, Integer systemId);
    List<DJMenu> findCanAccessMenusOfUserOfSystem(Integer id, Integer systemId);
    List<DJRole> findNowRolesOfUserOfSystem(Integer id, Integer systemId);
    abstract void insert(DJUser user);
    abstract void update(DJUser user);

}
