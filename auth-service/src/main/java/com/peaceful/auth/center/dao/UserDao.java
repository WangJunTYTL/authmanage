package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJFunction;
import com.peaceful.auth.center.domain.DJResource;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJUser;

import java.util.List;

/**
 * Created by wangjun on 14-4-15.
 */
public interface UserDao {

    DJUser findUserByUserId(Integer id);

    DJUser findUserByUserName(String name, Integer systemId);

    DJUser findUserByUserNameAndPassword(String name, Integer systemId, String password);

    List<DJResource> findCanAccessResourcesOfUserOfSystem(Integer id, Integer systemId);

    List<DJFunction> findCanAccessFunctionsOfUserOfSystem(Integer id, Integer systemId);

    List<DJRole> findNowRolesOfUserOfSystem(Integer id, Integer systemId);

    void insert(DJUser user);

    void update(DJUser user);

}
