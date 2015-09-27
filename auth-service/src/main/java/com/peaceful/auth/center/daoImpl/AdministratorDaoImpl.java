package com.peaceful.auth.center.daoImpl;

import com.peaceful.auth.center.dao.AdministratorDao;
import com.peaceful.auth.center.domain.DJAdministrator;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangJun on 14-4-19.
 */
@Component(value = "administratorDao")
public class AdministratorDaoImpl implements AdministratorDao {
    @Autowired
    private SessionFactory sessionFactory;

    public DJAdministrator findAdministratorByAdministratorId(Integer id) {
        return (DJAdministrator) sessionFactory.getCurrentSession().get(DJAdministrator.class, id);
    }

    @Override
    public List<DJAdministrator> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from adminatrator ").list();
    }

    public DJAdministrator findAdministratorByUsernameAndPass(String username,String password) {
        List<DJAdministrator> result = sessionFactory.getCurrentSession().createQuery("from adminatrator as a where a .name = ?  and a.password = ?").setString(0, username).setString(1,password).list();
        if (result != null && result.size() > 0)
            return result.get(0);
        else
            return null;
    }

    public void inserte(DJAdministrator djAdministrator) {
        sessionFactory.getCurrentSession().save(djAdministrator);
    }

    public void update(DJAdministrator djAdministrator) {
        sessionFactory.getCurrentSession().update(djAdministrator);
    }
}
