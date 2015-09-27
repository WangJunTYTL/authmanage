package com.peaceful.auth.center.ServiceImpl;

import com.peaceful.auth.center.Service.AdministratorService;
import com.peaceful.auth.center.dao.AdministratorDao;
import com.peaceful.auth.center.domain.DJAdministrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangJun on 14-4-19.
 */
@Component(value = "administratorService")
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    AdministratorDao administratorDao;

    @Override
    public List<DJAdministrator> finAllAdmin() {
        return administratorDao.findAll();
    }

    public DJAdministrator findAdministratorByAdministratorId(Integer id) {
        return administratorDao.findAdministratorByAdministratorId(id);
    }


    public DJAdministrator findAdministratorByUsernameAndPass(String username,String password) {
        return administratorDao.findAdministratorByUsernameAndPass(username,password);
    }

    public void inserte(DJAdministrator djAdministrator) {
        administratorDao.inserte(djAdministrator);
    }

    public void update(DJAdministrator djAdministrator) {
        administratorDao.update(djAdministrator);
    }
}
