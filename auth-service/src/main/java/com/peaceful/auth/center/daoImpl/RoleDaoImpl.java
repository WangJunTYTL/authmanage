package com.peaceful.auth.center.daoImpl;

import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.dao.RoleDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "roleDao")
public class RoleDaoImpl implements RoleDao {
    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory = null;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<DJRole> findAllRoleSortBySystem() {
        return sessionFactory.getCurrentSession().createQuery("from role").list();
    }

    public DJRole findRoleById(Integer id) {
        return (DJRole) sessionFactory.getCurrentSession().get(DJRole.class,id);
    }

    @Override
    public DJRole findRoleByName(String name,Integer systemId) {
        List result =  sessionFactory.getCurrentSession().createQuery("from role where name = ? and system.id = ?").setString(0,name).setInteger(1,systemId).list();
        if (result!=null&& !result.isEmpty()){
            return (DJRole) result.get(0);
        }
        return null;
    }

    public void inserte(DJRole djJRole) {
        sessionFactory.getCurrentSession().save(djJRole);
    }

    public void update(DJRole djJRole) {
        sessionFactory.getCurrentSession().update(djJRole);
    }

    public void delate(DJRole djJRole) {
        sessionFactory.getCurrentSession().delete(djJRole);
    }
}
