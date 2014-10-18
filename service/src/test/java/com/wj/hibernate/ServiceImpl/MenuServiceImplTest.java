package com.wj.hibernate.ServiceImpl;

import com.peaceful.auth.center.Service.MenuService;
import com.peaceful.auth.center.Service.RoleService;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.domain.DJMenu;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJSystem;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import util.DomainUtil;

import java.util.*;

/**
 * Created by wangjun on 14-4-17.
 */
public class MenuServiceImplTest {

    MenuService menuService = null;
    SystemService systemService = null;
    RoleService roleService= null;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @org.junit.Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        menuService = applicationContext.getBean("menuService", MenuService.class);
        systemService = applicationContext.getBean("systemService", SystemService.class);
        roleService = applicationContext.getBean("roleService", RoleService.class);
    }

    @Test
    public void testFindAllMenuSortBySystem() throws Exception {

    }

    @Test
    public void testFindMenuByMenuId() throws Exception {

    }

    @Test
    public void testInsertMenu() throws Exception {
        DJMenu menu = DomainUtil.getMenu();
        DJSystem system = DomainUtil.getSystem();
        system.id=1;
        menu.system= system;
        DJRole role = DomainUtil.getRole();
        role.id=2;
        List<DJRole> roles = new ArrayList<DJRole>();
        roles.add(role);
        menu.roles=roles;
        menuService.insertMenu(menu);
    }



    @Test
    public void testUpdateMenu() throws Exception {

    }

    @Test
    public void testDeleteMenu() throws Exception {

    }
}
