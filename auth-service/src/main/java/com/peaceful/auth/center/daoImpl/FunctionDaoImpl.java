package com.peaceful.auth.center.daoImpl;

import com.peaceful.auth.center.dao.FunctionDao;
import com.peaceful.auth.center.domain.DJFunction;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "functionDao")
public class FunctionDaoImpl implements FunctionDao {

    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory = null;

    public DJFunction findFunctionByFunctionId(Integer id) {
        return (DJFunction) sessionFactory.getCurrentSession().get(DJFunction.class, id);
    }

    public DJFunction findFunctionuByFunctionkey(String key, Integer systemId) {
        List result = sessionFactory.getCurrentSession().createQuery("from t_function  where function_key = ? and system.id = ?").setString(0, key).setInteger(1, systemId).list();
        if (result != null && result.size() > 0) {
            return (DJFunction) result.get(0);
        }
        return null;
    }

    public void inserte(DJFunction function) {
        sessionFactory.getCurrentSession().save(function);
    }

    public void update(DJFunction function) {
        sessionFactory.getCurrentSession().update(function);
    }

    public void delate(DJFunction function) {
        sessionFactory.getCurrentSession().delete(function);
    }
}
