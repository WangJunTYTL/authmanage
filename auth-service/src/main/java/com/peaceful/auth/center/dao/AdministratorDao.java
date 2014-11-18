package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJAdministrator;

/**
 * Created by wangjun on 14-4-15.
 */
public interface AdministratorDao {

    DJAdministrator findAdministratorByAdministratorId(Integer id);
    DJAdministrator findAdministratorByUsernameAndPass(String username,String password);

    abstract void inserte(DJAdministrator djAdministrator);

    abstract void update(DJAdministrator djAdministrator);


}
