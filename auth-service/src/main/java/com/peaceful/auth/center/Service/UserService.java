package com.peaceful.auth.center.Service;

import com.peaceful.auth.center.domain.DJResource;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJFunction;
import com.peaceful.auth.center.domain.DJUser;

import java.util.List;

/**
 * Created by wangjun on 14-4-17.
 */
public interface UserService {
    abstract DJUser findUserByUserName(String name, Integer systemId);

    /**
     * 可以访问的资源，去除下线的资源
     *
     * @param userId
     * @param systemId
     * @return
     */
    List<DJResource> findCanAccressResourcesOfCerrentSystemAndUser(Integer userId, Integer systemId);


    /**
     * 可以访问的菜单，去除下线的菜单
     *
     * @param userId
     * @param systemId
     * @return
     */
    List<DJFunction> findCanAccressFunctionsOfCerrentSystemAndUser(Integer userId, Integer systemId);

    /**
     * 拥有的角色，去除下线的角色
     *
     * @param userId
     * @param systemId
     * @return
     */
    List<DJRole> findHasAuthOfUser(Integer userId, Integer systemId);

    /**
     * 返回用户的信息包括角色信息
     *
     * @param userId
     * @return
     */
    DJUser findUserByUserId(Integer userId);
    DJUser findUserByUserNameAndPassword(String username,Integer systemId,String password);

    boolean findUserIsDel(Integer userId, Integer systemId);

    /**
     * 每个用户可以在多个系统下注册，如果用户在该系统下已注册，不允许重复注册
     *
     * @param user
     */
    void insertUser(DJUser user);

    /**
     * 注意删除用户的方法是直接将isdel改为0
     *
     * @param user
     */
    void updateUser(DJUser user);



}
