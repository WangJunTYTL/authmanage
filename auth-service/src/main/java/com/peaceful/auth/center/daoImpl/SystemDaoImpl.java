package com.peaceful.auth.center.daoImpl;

import com.peaceful.auth.center.dao.SystemDao;
import com.peaceful.auth.center.domain.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sun.security.provider.MD5;

import java.util.*;


/**
 * Created by wangjun on 14-4-15.
 */
@Component(value = "systemDao")
public class SystemDaoImpl implements SystemDao {
    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory = null;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SystemDaoImpl() {

    }


    public List findAllSystems() {
        return sessionFactory.getCurrentSession().createQuery("from DJSystem ").list();
    }

    public DJSystem findSystemById(Integer id) {
        return (DJSystem) sessionFactory.getCurrentSession().get(DJSystem.class, id);
    }

    @Override
    public DJSystem findSystemByAppkay(String appkey) {
        List result = sessionFactory.getCurrentSession().createQuery("from DJSystem where appkey = ?").setString(0, appkey).list();
        if (result != null && !result.isEmpty()) {
            return (DJSystem) result.get(0);
        }
        return null;
    }

    @Override
    public DJSystem findSystemByAppkayAndSecret(String appkey, String secret) {
        List<DJSystem> result = sessionFactory.getCurrentSession().createQuery("from DJSystem as a where a .appkey = ?  and a.secret = ?").setString(0, appkey).setString(1,secret).list();
        if (result != null && !result.isEmpty())
            return result.get(0);
        else
            return null;
    }

    public DJSystem findLiveSystemById(Integer id) {
        DJSystem system = (DJSystem) sessionFactory.getCurrentSession().get(DJSystem.class, id);
        if (system == null || system.isdel != 1)
            return null;
        else {
            Collection<DJRole> liveRoles = sessionFactory.getCurrentSession().createFilter(
                    system.roles,
                    ("where this.isdel = 1 ")
            ).list();
            Set<DJRole> roles = new HashSet<DJRole>();
            for (DJRole role : liveRoles) {
                Set<DJUser> users = new HashSet<DJUser>();
                Collection<DJUser> liveUsers = sessionFactory.getCurrentSession().createFilter(
                        role.users,
                        ("where this.isdel = 1 ")
                ).list();
                for (DJUser user : liveUsers) {
                    users.add(user);
                }
                role.users = users;
                roles.add(role);
            }
            for (DJRole role : liveRoles) {
                Set<DJMenu> menus = new HashSet<DJMenu>();
                Collection<DJMenu> liveMenus = sessionFactory.getCurrentSession().createFilter(
                        role.menus,
                        ("where this.isdel = 1 ")
                ).list();
                for (DJMenu menu : liveMenus) {
                    menus.add(menu);
                }
                role.menus = menus;
                roles.add(role);
            }
            system.roles = roles;
        }
        return system;
    }

    public DJSystem findSystemByName(String name) {
        List result = sessionFactory.getCurrentSession().createQuery("from DJSystem where name = ?").setString(0, name).list();
        if (result != null && !result.isEmpty()) {
            return (DJSystem) result.get(0);
        }
        return null;
    }

    public void inserte(DJSystem system) {
        sessionFactory.getCurrentSession().save(system);

    }

    public void update(DJSystem system) {
        sessionFactory.getCurrentSession().update(system);
    }

    public void delate(DJSystem system) {
        sessionFactory.getCurrentSession().delete(system);
    }
}
