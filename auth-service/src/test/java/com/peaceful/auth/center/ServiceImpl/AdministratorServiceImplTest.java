package com.peaceful.auth.center.ServiceImpl;

import com.peaceful.auth.center.Service.AdministratorService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import util.DomainUtil;

/**
 * Created by WangJun on 14-4-19.
 */
public class AdministratorServiceImplTest {
    AdministratorService administratorService = null;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @org.junit.Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        administratorService = applicationContext.getBean("administratorService", AdministratorService.class);
    }
    @Test
    public void testFindAdministratorByAdministratorId() throws Exception {

    }

    @Test
    public void testFindAdministratorByUsernameAndPass() throws Exception {
    }

    @Test
    public void testInserte() throws Exception {
        administratorService.inserte(DomainUtil.getAdministrator());    }

    @Test
    public void testUpdate() throws Exception {

    }
}
