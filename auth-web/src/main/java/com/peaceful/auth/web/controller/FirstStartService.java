package com.peaceful.auth.web.controller;

import com.peaceful.auth.center.Service.AdministratorService;
import com.peaceful.auth.center.ServiceImpl.AppContextService;
import com.peaceful.auth.center.domain.DJAdministrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

/**
 * @author WangJun <wangjuntytl@163.com>
 * @version 1.0 15/9/27
 * @since 1.6
 */
@Controller
public class FirstStartService {

    static Logger logger = LoggerFactory.getLogger(FirstStartService.class);

    static {
        AdministratorService administratorService = AppContextService.applicationContext.getBean(AdministratorService.class);
        List<DJAdministrator> administrators = administratorService.finAllAdmin();
        if (administrators.isEmpty()) {
            DJAdministrator administrator = new DJAdministrator();
            administrator.createTime = new Date();
            administrator.isdel = 1;
            administrator.name = "admin";
            administrator.password = "123456";
            administrator.operator = "admin";
            administratorService.inserte(administrator);
            logger.info("--------------------------------------");
            logger.info("init system for the first start");
            logger.info("user：admin");
            logger.info("password：123456");
            logger.info("--------------------------------------");
        } else {
            logger.warn("please note: del the class when on product env");
        }
    }
}
