package com.wj.hibernate.ServiceImpl;

import com.peaceful.auth.center.Service.MenuService;
import com.peaceful.auth.center.Service.ResourceService;
import com.peaceful.auth.center.Service.RoleService;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.domain.DJResource;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJSystem;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import util.DomainUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJun on 14-4-17.
 */
public class ResourceServiceImplTest {

    MenuService menuService = null;
    SystemService systemService = null;
    RoleService roleService= null;
    ResourceService resourceService= null;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @org.junit.Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        menuService = applicationContext.getBean("menuService", MenuService.class);
        systemService = applicationContext.getBean("systemService", SystemService.class);
        roleService = applicationContext.getBean("roleService", RoleService.class);
        resourceService = applicationContext.getBean("resourceService", ResourceService.class);
    }

    @Test
    public void testFindAllResourceSortBySystem() throws Exception {

    }

    @Test
    public void testFindResourceByResourceId() throws Exception {

    }

    @Test
    public void testInsertResource() throws Exception {
        DJResource resource = DomainUtil.getResourceList(1).get(0);
        DJSystem system = new DJSystem();
        system.id=1;
        DJRole role = DomainUtil.getRole();
        role.id=2;
        List<DJRole> roles = new ArrayList<DJRole>();
        roles.add(role);
        resource.system=system;
        resource.roles=roles;
        resourceService.insertResource(resource);
    }

    @Test
    public void testUpdateResource() throws Exception {
        DJResource resource = resourceService.findResourceByResourceId(2);
        DJSystem system = new DJSystem();
        system.id=1;
        DJRole role = DomainUtil.getRole();
        role.id=2;
        List<DJRole> roles = new ArrayList<DJRole>();
        roles.add(role);
        resource.system=system;
        resource.roles=roles;
        resourceService.updateResource(resource);
    }

    @Test
    public void testDeleteResource() throws Exception {

    }
}
