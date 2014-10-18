package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJRole;

/**
 * Created by wangjun on 14-4-15.
 */
public interface RoleDao {

    abstract DJRole findRoleById(Integer id);
    abstract DJRole findRoleByName(String name,Integer systemId);

    abstract void inserte(DJRole djJRole);

    abstract void update(DJRole djJRole);

    abstract void delate(DJRole djJRole);

}
