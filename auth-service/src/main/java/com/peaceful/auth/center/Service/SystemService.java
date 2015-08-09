package com.peaceful.auth.center.Service;

import com.peaceful.auth.center.domain.DJFunction;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJSystem;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjun on 14-4-17.
 */
public interface SystemService {

    Map<Integer,String> allTokens = new IdentityHashMap<Integer, String>();

    Map<Integer,String> refreshTokensOfAllSystem();

    DJSystem findSystemByName(String name);

    boolean systemIsExist(Integer systemId);

    List<DJSystem> findRolesSortBySystem();


    List<DJRole> findRolesBySystemId(Integer systemId);

    /**
     * 所有的function，并级联其父对象
     *
     * @param systemId
     * @return
     */
    List<DJFunction> findFunctionsBySystemId(Integer systemId);

    DJSystem findLiveSystemBySystemId(Integer systemId);

    List<DJSystem> findUsersSortBySystem();

    List<DJSystem> findResourcesSortBySystem();

    List<DJSystem> findFunctionsSortBySystem();


    List<DJSystem> findAllSystems();

    void insertSystem(DJSystem system);

    DJSystem findSystemBySystemId(Integer systemId);

    DJSystem findSystemByAppkey(String appkey);

    DJSystem findSystemByAppkeyAndSecret(String appkey, String secret);

    DJSystem findSystemBySystemId(Integer systemId, Integer loadType);

    void updateSystem(DJSystem system);
}
