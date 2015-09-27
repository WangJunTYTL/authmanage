package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJAdministrator;

import java.util.List;

/**
 * Created by wangjun on 14-4-15.
 */
public interface AdministratorDao {

    DJAdministrator findAdministratorByAdministratorId(Integer id);

    DJAdministrator findAdministratorByUsernameAndPass(String username, String password);

    void inserte(DJAdministrator djAdministrator);

    void update(DJAdministrator djAdministrator);

    List<DJAdministrator> findAll();


}
