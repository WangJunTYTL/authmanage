package com.peaceful.auth.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.peaceful.auth.center.Service.RoleService;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.Service.UserService;
import com.peaceful.auth.center.domain.*;
import com.peaceful.auth.center.util.HibernateRoleUtil;
import com.peaceful.auth.center.util.HibernateSystemUtil;
import com.peaceful.auth.data.util.VCS;
import com.peaceful.auth.data.domain.*;
import com.peaceful.auth.web.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 对外提供http协议服务
 * Created by wj on 14-4-24.
 */
@Controller
@RequestMapping("sdk")
public class SDKServiceController {


    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private RoleService roleService;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取服务中心vcs值
     *
     * @return
     */
    @RequestMapping(value = "/findVCS.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String findCurrentVCS(HttpServletRequest request, @RequestParam("systemId") Integer systemId) {
        return JSON.toJSONString(VCS.getCurrentVersion());
    }

    /**
     * sdk 认证授权
     *
     * @param appkey
     * @param secret
     * @return
     */
    @RequestMapping(value = "/accredit.do", method = RequestMethod.POST)
    @ResponseBody
    public String sdkLogin(@RequestParam("appkey") String appkey, @RequestParam("secret") String secret) {
        DJSystem system;
        system = systemService.findSystemByAppkeyAndSecret(appkey, secret);
        if (system != null) {
            return JSON.toJSONString(new SDKSessionFormat(0, "OK", system));
        } else {
            return JSON.toJSONString(new SDKSessionFormat(1, "ERROR", "认证失败"));
        }
    }


    //在线帮助文档
    @RequestMapping(value = "/help.do", method = RequestMethod.GET)
    public String help() {
        return "/help";
    }

    //得到用户有关的所有信息
    @RequestMapping(value = "/findUser.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String findUser(String userEmail, Integer systemId) {
        DJUser user = userService.findUserByUserName(userEmail, systemId);
        if (user == null || user.isdel != 1) {
            return JSON.toJSONString(null);
        }
        List<DJResource> resources = userService.findCanAccressResourcesOfCerrentSystemAndUser(user.id, systemId);
        List<DJMenu> menus = userService.findCanAccressMenusOfCerrentSystemAndUser(user.id, systemId);
        List<DJRole> roles = userService.findHasAuthOfUser(user.id, systemId);
        JSONUser jsonUser = TransitionUtils.toJSONUser(user);
        List<JSONMenu> menuList = new ArrayList<JSONMenu>();
        List<JSONResource> resourceList = new ArrayList<JSONResource>();
        List<JSONRole> roleList = new ArrayList<JSONRole>();
        menuList.addAll(TransitionUtils.batchToJSONMenu(menus));
        resourceList.addAll(TransitionUtils.batchToJSONResource(resources));
        roleList.addAll(TransitionUtils.batchToJSONRole(roles));
        jsonUser.menus = menuList;
        jsonUser.resources = resourceList;
        jsonUser.roles = roleList;
        return JSON.toJSONString(jsonUser);
    }

    //得到系统有关的所有信息
    @RequestMapping(value = "/findSystem.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String findSystem(Integer systemId) {
        DJSystem system = systemService.findLiveSystemBySystemId(systemId);
        JSONSystem jsonSystem = null;
        if (system != null) {
            jsonSystem = new JSONSystem();
            jsonSystem.id = systemId;
            jsonSystem.name = system.name;
            List<JSONRole> jsonRoles = new ArrayList<JSONRole>();
            Set<DJRole> roles = system.roles;
            if (roles != null) {
                for (DJRole role : roles) {
                    JSONRole jsonRole = TransitionUtils.toJSONRole(role);
                    Set<DJUser> users = role.users;
                    Set<DJMenu> menus = role.menus;
                    List<JSONUser> userList = new ArrayList<JSONUser>();
                    if (users != null) {
                        userList.addAll(TransitionUtils.batchToJSONUser(users));
                    }
                    List<JSONMenu> menuList = new ArrayList<JSONMenu>();
                    if (menus != null) {
                        menuList.addAll(TransitionUtils.batchToJSONMenu(menus));
                    }
                    jsonRole.users = userList;
                    jsonRole.menus = menuList;
                    jsonRoles.add(jsonRole);
                }
            }
            jsonSystem.roles = jsonRoles;
        }
        return JSON.toJSONString(jsonSystem);
    }


    //统一认证大街账号
    @RequestMapping(value = "/identification.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String identificationEmail(@RequestParam("email") String username, @RequestParam("password") String password, Integer systemId) {
//        LDAPConnector ldapConnector = LDAPConnector.getInstance();
//        boolean ldapValidate = ldapConnector.validateUser(username, password);
        DJUser user = userService.findUserByUserNameAndPassword(username, systemId, password);
        boolean ldapValidate = user == null ? false : true;
        return JSON.toJSONString(ldapValidate);
    }

    //添加用户，并级联添加用户拥有的角色
    @RequestMapping(value = "/addUser.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addUser(DJUser user, Integer systemId, Integer[] roleIds) {
        if (systemId == null) {
            return JSON.toJSONString(new Response(0, BACK.USERNOTHASSYSTEN.code, LEVEL.WARN.name(), BACK.USERNOTHASSYSTEN.result));
        }
        user.isdel = 1;
        user.createTime = new Date();
        user.system = ReferenceId.getSystem(systemId);
        user.roles = ReferenceId.getRoles(roleIds);
        DJUser djUser = userService.findUserByUserName(user.email, systemId);
        if (djUser != null)
            return JSON.toJSONString(new Response(0, BACK.USERISEXIST.code, LEVEL.WARN.name(), BACK.USERISEXIST.result));
        try {
            userService.insertUser(user);
            return JSON.toJSONString(new Response(1, BACK.USERADDSUCCESS.code, LEVEL.INFO.name(), BACK.USERADDSUCCESS.result));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }

    }


    //更新用户且可以否级联更新用户的角色信息
    @RequestMapping(value = "/updateUser.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateUser(DJUser user, Integer systemId, boolean cascade_user, String roleIds) {
        if (systemId == null) {
            return JSON.toJSONString(new Response(0, BACK.USERNOTHASSYSTEN.code, LEVEL.WARN.name(), BACK.USERNOTHASSYSTEN.result));
        }
        if (cascade_user) {
            user.system = ReferenceId.getSystem(systemId);
            if (StringUtils.isNotEmpty(roleIds)) {
                JSONArray jsonArray = JSON.parseArray(roleIds);
                Integer[] roleIds_ = new Integer[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); i++) {
                    roleIds_[i] = (Integer) jsonArray.get(i);
                }
                user.roles = ReferenceId.getRoles(roleIds_);
            } else
                user.roles = null;
        } else {
            DJUser djUser = userService.findUserByUserId(user.id);
            user.system = djUser.system;
            user.roles = djUser.roles;
        }
        try {
            userService.updateUser(user);
            return JSON.toJSONString(new Response(1, BACK.USERUPDATESUCCESS.code, LEVEL.INFO.name(), BACK.USERUPDATESUCCESS.result));
        } catch (Exception e) {
            logger.error("updateUser:{}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }


    //添加角色，不级联添加角色下的对象
    @RequestMapping(value = "/addRole.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addRole(DJRole role, Integer systemId) {
        if (systemId == null)
            return JSON.toJSONString(new Response(0, BACK.ROLENOTHASSYSTEN.code, LEVEL.ERROR.name(), BACK.ROLENOTHASSYSTEN.result));
        DJRole test = roleService.findRoleByName(role.name, role.system.id);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.ROLEISEXIST.code, LEVEL.INFO.name(), BACK.ROLEISEXIST.result));
        role.system = ReferenceId.getSystem(systemId);
        role.createTime = new Date();
        try {
            roleService.insertRole(role);
            return JSON.toJSONString(new Response(1, BACK.ROLEADDSUCCESS.code, LEVEL.INFO.name(), BACK.ROLEADDSUCCESS.result));
        } catch (Exception e) {
            logger.error("addRole:{}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }

    //更新角色且可以级联更新角色与菜单的对应关系
    @RequestMapping(value = "/updateRole.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateRole(DJRole role, Integer systemId, boolean cascade_menu, String menuIds) {
        if (systemId == null)
            return JSON.toJSONString(new Response(0, BACK.ROLENOTHASSYSTEN.code, LEVEL.ERROR.name(), BACK.ROLENOTHASSYSTEN.result));
        if (cascade_menu) {
            role.system = ReferenceId.getSystem(systemId);
            if (StringUtils.isNotEmpty(menuIds)) {
                JSONArray jsonArray = JSON.parseArray(menuIds);
                Integer[] menuIds_ = new Integer[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); i++) {
                    menuIds_[i] = (Integer) jsonArray.get(i);
                }
                role.menus = ReferenceId.getMenus_(menuIds_);
            } else
                role.menus = null;
        } else {
            DJRole djRole = roleService.findRoleByRoleId(role.id, HibernateRoleUtil.MENU);
            role.system = djRole.system;
            role.menus = djRole.menus;
        }
        try {
            roleService.updateRole(role);
            return JSON.toJSONString(new Response(1, BACK.ROLEUPDATESUCCESS.code, LEVEL.INFO.name(), BACK.ROLEUPDATESUCCESS.result));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }

    @RequestMapping(value = "/getMenus.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getMenusOfSystem(Integer systemId) {
        List<JSONMenu> menuList = new ArrayList<JSONMenu>();
        menuList.addAll(TransitionUtils.batchToJSONMenu(systemService.findMenusBySystemId(systemId)));
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(menuList);
        return jsonArray.toJSONString();
    }


    @RequestMapping(value = "/getRoles.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getRolesOfSystem(Integer systemId) {
        List<DJRole> roles = systemService.findRolesBySystemId(systemId);
        List<TransientRole> roleList = new ArrayList<TransientRole>();
        for (DJRole role : roles) {
            TransientRole transientRole = new TransientRole();
            transientRole.id = role.id;
            transientRole.name = role.name;
            roleList.add(transientRole);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(roleList);
        return jsonArray.toJSONString();
    }

    @RequestMapping(value = "/getUsers.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getUsersOfSystem(Integer systemId) {
        DJSystem system = systemService.findSystemBySystemId(systemId, HibernateSystemUtil.USER);
        Set<DJUser> users = system.users;
        List<JSONUser> userList = new ArrayList<JSONUser>();
        userList.addAll(TransitionUtils.batchToJSONUser(users));
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(userList);
        return jsonArray.toJSONString();
    }

    @RequestMapping(value = "/getResources.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getResourcesOfSystem(Integer systemId) {
        DJSystem system = systemService.findSystemBySystemId(systemId, HibernateSystemUtil.RESOURCE);
        Set<DJResource> resources = system.resources;
        List<JSONResource> resourceList = new ArrayList<JSONResource>();
        resourceList.addAll(TransitionUtils.batchToJSONResource(resources));
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(resourceList);
        return jsonArray.toJSONString();
    }


    @RequestMapping(value = "a")
    public String testa() {
        return "page_a.ftl";
    }

    @RequestMapping(value = "b")
    public String testb() {
        return "main";
    }

}
