package com.wj.hibernate.ServiceImpl;

import com.peaceful.auth.center.Service.UserService;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJSystem;
import com.peaceful.auth.center.domain.DJUser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjun on 14-4-17.
 */
public class UserServiceImplTest {

    UserService userService = null;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @org.junit.Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        userService = applicationContext.getBean("userService", UserService.class);
    }

    @Test
    public void findUserById(){
        userService.findUserByUserId(1);
    }

    @Test
    public void testFindAllUsersSortBySystem() throws Exception {

    }

    @Test
    public void testFindAllUsers() throws Exception {
//        DJSystem systems = userService.findAllUser();
        /*for (DJSystem system : systems) {
            logger.info(system.name);
            for (DJUser user:system.users){
                logger.info(user.email);
            }

        }*/
    }

    @Test
    public void testInsertUser() throws Exception {
        DJUser user = new DJUser();
        user.email = "emiala";
        DJSystem system = new DJSystem();
        system.id = 17;
        user.system = system;
        DJRole role = new DJRole();
        role.id=28;
        List roles = new ArrayList();
        roles.add(role);
        user.roles=roles;
        userService.insertUser(user);

    }

    @Test
    public void testInsertUserNotCascadeRole() throws Exception {
        DJUser user = new DJUser();
        user.email = "emial";
        DJSystem system = new DJSystem();
        system.id = 17;
        user.system = system;
        userService.insertUser(user);

    }


    @Test
    public void testUpdateUser() throws Exception {
        DJUser user = userService.findUserByUserId(2);
        user.isdel=1;
        DJRole role = new DJRole();
        role.id=1;
        List roles = new ArrayList();
        roles.add(role);
        user.roles=roles;
        userService.updateUser(user);

    }

    @Test
    public void findLiveResourcesOfUser() throws Exception {

    }

}
