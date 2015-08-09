package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJRole;

/**
 * Created by wangjun on 14-4-15.
 */
public interface RoleDao {

    DJRole findRoleById(Integer id);

    DJRole findRoleByName(String name, Integer systemId);

    void inserte(DJRole djJRole);

    void update(DJRole djJRole);

    void delate(DJRole djJRole);

}
