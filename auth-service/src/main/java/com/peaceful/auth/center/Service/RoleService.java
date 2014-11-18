package com.peaceful.auth.center.Service;

import com.peaceful.auth.center.domain.DJRole;

/**
 * Created by wangjun on 14-4-17.
 */
public interface RoleService {


    void insertRole(DJRole role);

    abstract DJRole findRoleByName(String name, Integer systemId);

    /**
     * 不级联任何对象
     *
     * @param roleId
     * @return
     */
    DJRole findRoleByRoleId(Integer roleId);

    /**
     * 有选择性的级联对象
     * @param roleId
     * @param casecadeType
     * @return
     */
    DJRole findRoleByRoleId(Integer roleId,Integer casecadeType);

    void updateRole(DJRole role);

}
