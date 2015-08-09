package com.peaceful.auth.center.ServiceImpl;

import com.peaceful.auth.center.Service.BeanService;
import com.peaceful.auth.center.dao.BeanDao;
import com.peaceful.auth.center.dao.SystemDao;
import com.peaceful.auth.center.domain.DJBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "beanService")
public class BeanServiceImpl implements BeanService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SystemDao systemDao = null;

    public void setbeanDao(BeanDao beanDao) {
        this.beanDao = beanDao;
    }

    @Autowired
    BeanDao beanDao = null;

    public void setSystemDao(SystemDao systemDao) {
        this.systemDao = systemDao;
    }


    public DJBean findBeanByBeanId(Integer id) {
        DJBean bean = beanDao.findBeanByBeanId(id);
        if (bean.system != null) {
            logger.info("load system id is {}", bean.system.id);
        }
        logger.info("load roles {}", bean.roles);
        return bean;
    }


    public void insertBean(DJBean bean) {
        beanDao.inserte(bean);
    }

    @Override
    public DJBean findBeanByBeankey(String key, Integer systemId) {
        return beanDao.findBeanByBeankey(key, systemId);
    }

    public void updateBean(DJBean bean) {
        beanDao.update(bean);
    }

}
