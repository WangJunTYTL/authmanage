package com.peaceful.auth.center.daoImpl;

import com.peaceful.auth.center.dao.MenuDao;
import com.peaceful.auth.center.domain.DJMenu;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "menuDao")
public class MenuDaoImpl implements MenuDao {

    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory = null;

    public DJMenu findMenuByMenuId(Integer id) {
        return (DJMenu) sessionFactory.getCurrentSession().get(DJMenu.class, id);
    }

    public DJMenu findMenuByMenukey(String key, Integer systemId) {
        List result = sessionFactory.getCurrentSession().createQuery("from menu  where menukey = ? and system.id = ?").setString(0, key).setInteger(1, systemId).list();
        if (result != null && !result.isEmpty()) {
            return (DJMenu) result.get(0);
        }
        return null;
    }

    public void inserte(DJMenu djMenu) {
        sessionFactory.getCurrentSession().save(djMenu);
    }

    public void update(DJMenu djMenu) {
        sessionFactory.getCurrentSession().update(djMenu);
    }

    public void delate(DJMenu djMenu) {
        sessionFactory.getCurrentSession().delete(djMenu);
    }
}
