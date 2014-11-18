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
     * 所有有关于你系统的有效配置信息，这些配置都是未被注销的，比如返回的JSONSystem，对象system.roles 是所有未被注销的角色，
     * 而role 对象下是所有未被注销的菜单，用户
     *
     * @return JSONSystem
     */
    JSONSystem getSystem();


    /**
     * 所有关于当前用户的所有配置信息
     *
     * @param email
     * @return
     */
    JSONUser getUser(String email);

    /**
     * 根据当前用户渲染菜单，该方法已弃用，鼓励自己拿到菜单对象后自己实现渲染效果
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
     * 清除当前用户的数据（这些数据是用户登陆到你自己系统上留下的数据）
     *
     * @param email
     */
    void clearSession(String email);


    /**
     * 统一验证大街账号、密码
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
     * 得到系统内所有的菜单信息，请注意本方法取得的数据不会被缓存，因为它只会在你配置权限信息时才会用到该部分信息，
     * 如果你想取到当前有意义（未被注销，且和角色已经建立关系）的菜单请用getSystem拿到全局有效的配置信息
     *
     * @return
     */
    List<JSONMenu> getMenusOfSystem();

    /**
     * 得到系统内所有的角色信息，请注意本方法取得的数据不会被缓存，因为它只会在你配置权限信息时才会用到该部分信息，
     * 如果你想取到当前有意义（未被注销，且和角色已经建立关系）的菜单请用getSystem拿到全局有效的配置信息
     *
     * @return
     */
    List<JSONRole> getRolesOfSystem();

    /**
     * 得到系统内所有的用户信息，请注意本方法取得的数据不会被缓存，因为它只会在你配置权限信息时才会用到该部分信息，
     * 如果你想取到当前有意义（未被注销，且和角色已经建立关系）的用户请用getSystem拿到全局有效的配置信息
     *
     * @return
     */
    List<JSONUser> getUsersOfSystem();


    /**
     * 得到系统内所有的用户信息，请注意本方法取得的数据不会被缓存，因为它只会在你配置权限信息时才会用到该部分信息，
     * 如果你想取到当前有意义（未被注销，且和角色已经建立关系）的资源请用getSystem拿到全局有效的配置信息
     *
     * @return
     */
    List<JSONResource> getResourcesOfSystem();

}
