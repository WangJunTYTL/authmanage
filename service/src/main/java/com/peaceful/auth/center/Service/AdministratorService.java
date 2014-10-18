package com.peaceful.auth.center.Service;

import com.peaceful.auth.center.domain.DJAdministrator;

/**
 * Created by WangJun on 14-4-19.
 */
public interface AdministratorService {

    DJAdministrator findAdministratorByAdministratorId(Integer id);

    DJAdministrator findAdministratorByUsernameAndPass(String username,String password);

    abstract void inserte(DJAdministrator djAdministrator);

    abstract void update(DJAdministrator djAdministrator);
}
