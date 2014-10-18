package com.peaceful.auth.center.ServiceImpl;

import com.peaceful.auth.center.Service.MenuService;
import com.peaceful.auth.center.dao.MenuDao;
import com.peaceful.auth.center.dao.SystemDao;
import com.peaceful.auth.center.domain.DJMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "menuService")
public class MenuServiceImpl implements MenuService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SystemDao systemDao = null;

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Autowired
    MenuDao menuDao = null;

    public void setSystemDao(SystemDao systemDao) {
        this.systemDao = systemDao;
    }


    public DJMenu findMenuByMenuId(Integer id) {
        DJMenu menu = menuDao.findMenuByMenuId(id);
        if (menu.system != null) {
            logger.info("load system id is {}", menu.system.id);
        }
        logger.info("load roles {}", menu.roles);
        logger.info("load parent id {}", menu.parentMenu == null ?null:menu.parentMenu.id);
        return menu;
    }


    public void insertMenu(DJMenu menu) {
        menuDao.inserte(menu);
    }

    @Override
    public DJMenu findMenuByMenukey(String key, Integer systemId) {
        return menuDao.findMenuByMenukey(key,systemId);
    }

    public void updateMenu(DJMenu menu) {
        menuDao.update(menu);
    }

}
