package com.peaceful.auth.api;


import com.peaceful.auth.data.domain.*;
import com.peaceful.auth.data.response.Response;

import java.util.List;

/**
 * <h3>
 * 所有对外可调用接口
 * </h3>
 * <p>QQ:867516002</p>
 * <p> <a href="mailto:jun.wang@dajie.com">邮件联系</a></p>
 * Created by wangjun on 14-4-23.
 */
public interface AuthService {
    /**
     * 获取你系统的全部有效(没有下线的)配置信息
     *
     * @return  JSONSystem
     */
    JSONSystem getSystem();


    /**
     * 获取指定用户的全部有效(没有下线的)配置信息
     *
     * @param email
     */
    JSONUser getUser(String email);

    /**
     * 根据指定用户渲染菜单（即生成一段html标记语言），该方法已弃用，为了更灵活的生成菜单样式，建议自己去实现生成html
     *
     * @param menukey
     * @param attribute
     * @param type
     * @param email
     * @return
     */
    @Deprecated
    String getMenu(String menukey, String attribute, int type, String email);

    /**
     * 根据当前用户获得菜单对象
     *
     * @param menukey
     * @param email
     * @return
     */
    JSONMenu getMenu(String menukey, String email);

    /**
     * 检测访问某个url的权限
     *
     * @param url
     * @param email
     * @return
     */
    boolean requestCheck(String url, String email);

    /**
     * 清除当前用户的数据（这些数据是用户在你自己系统上缓存的数据）
     *
     * @param email
     */
    void clearSession(String email);


    /**
     * 统一验证账号、密码
     *
     * @param email
     * @param password
     * @return
     */
    boolean identificationEmail(String email, String password);

    /**
     * 添加用户，可以级联添加用户的角色信息
     *
     * @param user
     * @return 返回操作结果对象Response
     */
    Response insertUser(JSONUser user);


    /**
     * 更新用户：包括状态，名字，账号，不级联更新角色信息
     *
     * @param user
     * @param cascade_user
     * @param roleIds
     * @return
     */
    Response updateUser(JSONUser user, boolean cascade_user, Integer[] roleIds);

    /**
     * 添加角色，不级联添加角色和菜单资源用户之间的关系
     *
     * @param role
     * @return
     */
    Response insertRole(JSONRole role);

    /**
     * 更新角色，级联更新菜单和角色之间的关系
     *
     * @param role
     * @param cascade_role
     * @param menuIds
     * @return
     */
    Response updateRole(JSONRole role, boolean cascade_role, Integer[] menuIds);


    /**
     * 得到系统内所有的（包括下线的）菜单信息，请注意本方法取得的数据不会被缓存
     *
     * @return
     */
    List<JSONMenu> getMenusOfSystem();

    /**
     * 得到系统内所有的（包括下线的）角色信息，请注意本方法取得的数据不会被缓存，
     *
     * @return
     */
    List<JSONRole> getRolesOfSystem();

    /**
     * 得到系统内所有的（包括下线的）用户信息，请注意本方法取得的数据不会被缓存
     *
     * @return
     */
    List<JSONUser> getUsersOfSystem();


    /**
     * 得到系统内所有的（包括下线的）资源信息，请注意本方法取得的数据不会被缓存
     *
     * @return
     */
    List<JSONResource> getResourcesOfSystem();

}
