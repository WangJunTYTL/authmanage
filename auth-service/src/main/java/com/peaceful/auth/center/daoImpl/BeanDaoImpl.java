package com.peaceful.auth.center.daoImpl;

import com.peaceful.auth.center.dao.BeanDao;
import com.peaceful.auth.center.domain.DJBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "beanDao")
public class BeanDaoImpl implements BeanDao {

    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory = null;

    public DJBean findBeanByBeanId(Integer id) {
        return (DJBean) sessionFactory.getCurrentSession().get(DJBean.class, id);
    }

    @Override
    public DJBean findBeanByBeankey(String key, Integer systemId) {
        List result = sessionFactory.getCurrentSession().createQuery("from t_bean  where bean_key = ? and system.id = ?").setString(0, key).setInteger(1, systemId).list();
        if (result != null && result.size() > 0) {
            return (DJBean) result.get(0);
        }
        return null;
    }

    public void inserte(DJBean bean) {
        sessionFactory.getCurrentSession().save(bean);
    }

    public void update(DJBean bean) {
        sessionFactory.getCurrentSession().update(bean);
    }

    public void delate(DJBean bean) {
        sessionFactory.getCurrentSession().delete(bean);
    }
}
