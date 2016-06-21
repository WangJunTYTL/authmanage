package com.peaceful.auth.center.daoImpl;

import com.peaceful.auth.center.domain.DJResource;
import com.peaceful.auth.center.dao.ResourceDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "resourceDao")
public class ResourceDaoImpl implements ResourceDao {
    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory = null;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DJResource findResourceByResourceId(Integer id) {
        return (DJResource) sessionFactory.getCurrentSession().get(DJResource.class, id);
    }

    @Override
    public DJResource findResourceByResourceUrl(String url,Integer systemId) {
        List result = sessionFactory.getCurrentSession().createQuery("from resource where pattern = ? and system.id = ?").setString(0, url).setInteger(1,systemId).list();
        if (result != null && !result.isEmpty()) {
            return (DJResource) result.get(0);
        }
        return null;
    }

    public void inserte(DJResource resource) {
        sessionFactory.getCurrentSession().save(resource);
    }

    public void update(DJResource resource) {
        sessionFactory.getCurrentSession().update(resource);
    }

    public void delate(DJResource resource) {
        sessionFactory.getCurrentSession().delete(resource);
    }
}
