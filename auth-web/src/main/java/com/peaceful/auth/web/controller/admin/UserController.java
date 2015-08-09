package com.peaceful.auth.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.Service.UserService;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJSystem;
import com.peaceful.auth.center.domain.DJUser;
import com.peaceful.auth.center.util.HibernateSystemUtil;
import com.peaceful.auth.web.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WangJun <wangjuntytl@163.com>
 * @version 1.0 15/8/9
 * @since 1.6
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;

    private final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/addUser.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addUser(HttpServletRequest request, DJUser user, Integer systemId, Integer[] roleIds) {
        if (systemId == null) {
            return JSON.toJSONString(new Response(0, BACK.USERNOTHASSYSTEN.code, LEVEL.WARN.name(), BACK.USERNOTHASSYSTEN.result));

        }
        if (roleIds == null || roleIds.length == 0) {
            return JSON.toJSONString(new Response(0, BACK.USERNOTHASROLE.code, LEVEL.WARN.name(), BACK.USERNOTHASROLE.result));
        }
        DJUser test = userService.findUserByUserName(user.email, systemId);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.USERISEXIST.code, LEVEL.WARN.name(), BACK.USERISEXIST.result));
        user.operator = ControllerUtil.getCurrentOperator(request);
        user.createTime = new Date();
        user.password = md5PasswordEncoder.encodePassword(Constant.DEFAULT_PASSWORD, Constant.DEFAULT_PASSWORD_SALT);
        user.passwordState = 0;
        DJSystem system = new DJSystem();
        system.id = systemId;
        user.system = system;
        List<DJRole> roles = new ArrayList<DJRole>();
        for (Integer id : roleIds) {
            DJRole role = new DJRole();
            role.id = id;
            roles.add(role);
        }
        user.roles = roles;
        try {
            userService.insertUser(user);
            return JSON.toJSONString(new Response(1, BACK.USERADDSUCCESS.code, LEVEL.INFO.name(), BACK.USERADDSUCCESS.result));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }

    @RequestMapping(value = "/addUserPre.do")
    public String addUserPre(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findAllSystems());
        return "addUser";
    }

    @RequestMapping(value = "/findUsers.do")
    public String findAllUsersSortBySystem(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findUsersSortBySystem());
        return "userList";
    }

    @RequestMapping(value = "/{id}/updateUser.do")
    public String updateUser(HttpServletRequest request, @PathVariable Integer id) {
        DJUser user = userService.findUserByUserId(id);
        request.setAttribute("user", user);
        request.setAttribute("system", systemService.findSystemBySystemId(user.system.id, HibernateSystemUtil.ROLE));
        return "updateUser";
    }

    @RequestMapping(value = "/updateUser.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateUser(DJUser user, Integer systemId, Integer[] roleIds, HttpServletRequest request) {
        if (systemId == null) {
            user.system = null;
        } else {
            DJSystem system = new DJSystem();
            system.id = systemId;
            user.system = system;
        }
        List<DJRole> roles = new ArrayList<DJRole>();
        if (roleIds != null) {
            for (Integer id : roleIds) {
                DJRole role = new DJRole();
                role.id = id;
                roles.add(role);
            }
        }
        user.roles = roles;
        user.setOperator(ControllerUtil.getCurrentOperator(request));
        DJUser user_ = userService.findUserByUserId(user.id);
        user.password = user_.password;
        user.passwordState = user_.passwordState;
        try {
            userService.updateUser(user);
            return JSON.toJSONString(new Response(1, BACK.USERUPDATESUCCESS.code, LEVEL.INFO.name(), BACK.USERUPDATESUCCESS.result));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }
}
