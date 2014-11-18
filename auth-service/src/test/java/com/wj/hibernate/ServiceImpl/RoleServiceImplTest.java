package com.wj.hibernate.ServiceImpl;

import com.peaceful.auth.center.Service.RoleService;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJSystem;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import util.DomainUtil;

/**
 * Created by wangjun on 14-4-17.
 */
public class RoleServiceImplTest {

    RoleService roleService = null;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @org.junit.Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        roleService = applicationContext.getBean("roleService", RoleService.class);
    }
    @Test
    public void testFindAllRoleSortBySystem() throws Exception {

    }

    @Test
    public void testFindRoleByRoleId() throws Exception {

    }

    @Test
    public void testInsertRole() throws Exception {
        DJRole role =DomainUtil.getRole();
        DJSystem system= DomainUtil.getSystem();
        system.id=1;
        role.system= system;
        roleService.insertRole(role);
    }

    @Test
    public void testUpdateRole() throws Exception {

    }

    @Test
    public void testDeleteRole() throws Exception {

    }
}
