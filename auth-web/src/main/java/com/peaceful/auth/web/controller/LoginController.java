package com.peaceful.auth.web.controller;

import com.alibaba.fastjson.JSON;
import com.peaceful.auth.center.Service.AdministratorService;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.domain.DJAdministrator;
import com.peaceful.auth.center.domain.DJSystem;
import com.peaceful.auth.data.util.MD5Utils;
import com.peaceful.auth.web.util.SDKSessionFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangJun on 14-4-19.
 */
@Controller
public class LoginController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    SystemService systemService;

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @RequestParam("email") String username, @RequestParam("password") String password) {
        DJAdministrator administrator;
//        LDAPConnector ldapConnector = LDAPConnector.getInstance();
//        boolean ldapValidate = ldapConnector.validateUser(username, password);
//        if (ldapValidate) {
        administrator = administratorService.findAdministratorByUsernameAndPass(username, password);
        if (administrator != null) {
            request.getSession().setAttribute("administrator", administrator);
            return "redirect:/admin/index.do";
        } else {
            request.setAttribute("msg", "用户名和密码不匹配");
//            request.setAttribute("msg", "没有权限登陆系统");
        }
//        } else {
//            request.setAttribute("msg", "用户名和密码不匹配");
//        }
        return "../../../login";
    }


    //sdk获取通信token
    @RequestMapping(value = "/token.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getToken(@RequestParam("appkey") String appkey, @RequestParam("secret") String secret) {
        if (StringUtils.isBlank(appkey) || StringUtils.isBlank(secret)) {
            return JSON.toJSONString(new SDKSessionFormat(2, "Error", "参数不对"));
        }
        DJSystem system = systemService.findSystemByAppkeyAndSecret(appkey, secret);
        if (system == null) {
            return JSON.toJSONString(new SDKSessionFormat(1, "Warn", "appkey或secret不正确"));
        } else {
            DJSystem system_ = new DJSystem();
            system_.id= system.id;
            system_.token = MD5Utils.string2MD5(appkey+System.nanoTime());
            system.token = system_.token;
            systemService.updateSystem(system);
            return JSON.toJSONString(new SDKSessionFormat(0, "OK", system_));
        }
    }


}
