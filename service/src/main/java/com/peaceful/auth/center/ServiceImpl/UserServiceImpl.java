package com.peaceful.auth.center.ServiceImpl;

import com.peaceful.auth.center.Service.UserService;
import com.peaceful.auth.center.dao.SystemDao;
import com.peaceful.auth.center.domain.DJMenu;
import com.peaceful.auth.center.domain.DJResource;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.dao.UserDao;
import com.peaceful.auth.center.domain.DJUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "userService")
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SystemDao systemDao = null;
    @Autowired
    UserDao userDao = null;

    public DJUser findUserByUserName(String name, Integer systemId) {
        return userDao.findUserByUserName(name,systemId);
    }

    public java.util.List<DJResource> findCanAccressResourcesOfCerrentSystemAndUser(Integer userId, Integer systemId) {
        return userDao.findCanAccessResourcesOfUserOfSystem(userId,systemId);
    }

    public java.util.List<DJMenu> findCanAccressMenusOfCerrentSystemAndUser(Integer userId, Integer systemId) {
        return userDao.findCanAccessMenusOfUserOfSystem(userId,systemId);
    }

    public java.util.List<DJRole> findHasAuthOfUser(Integer userId, Integer systemId) {
        return userDao.findNowRolesOfUserOfSystem(userId,systemId);
    }

    public DJUser findUserByUserId(Integer userId) {
        DJUser user = userDao.findUserByUserId(userId);
        if (user.system != null) {
            logger.info("load system id is {}", user.system.id);
        }
        logger.info("load roles {}", user.roles);
        return user;
    }

    @Override
    public DJUser findUserByUserNameAndPassword(String username,Integer systemId, String password) {
        return userDao.findUserByUserNameAndPassword(username,systemId,password);
    }

    public boolean findUserIsDel(Integer userId, Integer systemId) {
        return false;
    }

    public void insertUser(DJUser user) {
        userDao.insert(user);
    }

    public void updateUser(DJUser user) {
        userDao.update(user);
    }


}

