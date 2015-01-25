package com.peaceful.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.peaceful.auth.Constant;
import com.peaceful.auth.center.Service.*;
import com.peaceful.auth.center.domain.*;
import com.peaceful.auth.data.domain.JSONMenu;
import com.peaceful.auth.util.*;
import com.peaceful.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by WangJun on 14-4-19.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemService systemService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/index.do")
    public String redirectIndex(HttpServletRequest request) {
        DJAdministrator administrator = (DJAdministrator) request.getSession().getAttribute("administrator");
        if (administrator == null) {
            return "/login";
        }
        return "index";
    }

    @RequestMapping(value = "/out.do")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login.jsp";
    }

    @RequestMapping(value = "/insertAdmin.do", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String insertAdministrator(DJAdministrator administrator, HttpServletRequest request) {
        try {
            administrator.setOperator(getCurrentOperator(request));
            administratorService.inserte(administrator);
        } catch (Exception e) {
            return "fail";
        }
        return "suc";
    }

    @RequestMapping(value = "/updateAdmin.do", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateAdministrator(DJAdministrator administrator, HttpServletRequest request) {
        try {
            administrator.setOperator(getCurrentOperator(request));
            administratorService.update(administrator);
        } catch (Exception e) {
            return "fail";
        }
        return "suc";
    }


    @RequestMapping(value = "/menus.do")
    public String findAllMenusOfSystem() {
        List<DJSystem> systems = systemService.findMenusSortBySystem();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("menus", systems);
        modelAndView.setViewName("menus");
        return "menus";
    }

    @RequestMapping(value = "/addMenu.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addMenu(HttpServletRequest request, DJMenu menu, Integer systemId, Integer[] roleIds, Integer groupId) {
        if (systemId == null) {
            return JSON.toJSONString(new Response(0, BACK.MENUNOTHASSYSTEN.defineState, LEVEL.ERROR.name(), BACK.MENUNOTHASSYSTEN.detailLog));
        }
        if (roleIds == null || roleIds.length == 0) {
            return JSON.toJSONString(new Response(0, BACK.MENUNOTHASROLE.defineState, LEVEL.WARN.name(), BACK.MENUNOTHASROLE.detailLog));
        }
        DJMenu test = menuService.findMenuByMenukey(menu.menukey, systemId);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.MENUISEXIST.defineState, LEVEL.WARN.name(), BACK.MENUISEXIST.detailLog));
        menu.operator = getCurrentOperator(request);
        menu.createTime = new Date();
        DJSystem system = new DJSystem();
        system.id = systemId;
        menu.system = system;
        List<DJRole> roles = new ArrayList<DJRole>();
        if (roleIds != null) {
            for (Integer id : roleIds) {
                DJRole role = new DJRole();
                role.id = id;
                roles.add(role);
            }
        }
        menu.roles = roles;
        if (menu.parentMenu == null || menu.parentMenu.id == null)
            menu.parentMenu = null;
        try {
            menuService.insertMenu(menu);
            return JSON.toJSONString(new Response(1, BACK.MENUADDSUCCESS.defineState, LEVEL.INFO.name(), BACK.MENUADDSUCCESS.detailLog));
        } catch (Exception e) {
            logger.error("addMenu:[}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.defineState, LEVEL.ERROR.name(), BACK.UNKNOW.detailLog));
        }
    }

    @RequestMapping(value = "/addMenuPre.do")
    public String addMenuPre(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findAllSystems());
        return "addMenu";
    }


    @RequestMapping(value = "/findMenus.do")
    public String findAllMenusSortBySystem(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findMenusSortBySystem());
        return "menuList";
    }


    @RequestMapping(value = "/{id}/updateMenu.do")
    public String updateMenu(HttpServletRequest request, @PathVariable Integer id) {
        DJMenu menu = menuService.findMenuByMenuId(id);
        request.setAttribute("menu", menu);
        request.setAttribute("system", systemService.findSystemBySystemId(menu.system.id, HibernateSystemUtil.ROLE));
        return "updateMenu";
    }


    @RequestMapping(value = "/updateMenu.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateMenu(DJMenu menu, Integer systemId, Integer[] roleIds, HttpServletRequest request) {
        if (systemId == null) {
            menu.system = null;
        } else {
            DJSystem system = new DJSystem();
            system.id = systemId;
            menu.system = system;
        }
        List<DJRole> roles = new ArrayList<DJRole>();
        if (roleIds != null) {
            for (Integer id : roleIds) {
                DJRole role = new DJRole();
                role.id = id;
                roles.add(role);
            }
        }
        menu.roles = roles;
        menu.setOperator(getCurrentOperator(request));
        try {
            if (menu.parentMenu != null && menu.parentMenu.id != null) {
                DJMenu menu_ = menuService.findMenuByMenuId(menu.parentMenu.id);
                if (menu_ != null && menu_.parentMenu != null && menu_.parentMenu.id != null) {
                    if (menu_.parentMenu.id.equals(menu.id))
                        return JSON.toJSONString(new Response(1, BACK.MENUCYCLE.defineState, LEVEL.ERROR.name(), BACK.MENUCYCLE.detailLog));
                }
            }
            if (menu.parentMenu.id == null)
                menu.parentMenu = null;
            menuService.updateMenu(menu);
            return JSON.toJSONString(new Response(1, BACK.MENUUPDATESUCCESS.defineState, LEVEL.INFO.name(), BACK.MENUUPDATESUCCESS.detailLog));
        } catch (Exception e) {
            logger.error("updateMenu:{}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.defineState, LEVEL.ERROR.name(), BACK.UNKNOW.detailLog));
        }
    }

    @RequestMapping(value = "/addResource.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addResource(HttpServletRequest request, DJResource resource, Integer systemId, Integer[] roleIds) {
        if (systemId == null)
            return JSON.toJSONString(new Response(0, BACK.RESOURCENOTHASSYSTEN.defineState, LEVEL.WARN.name(), BACK.RESOURCENOTHASSYSTEN.detailLog));
        if (roleIds == null || roleIds.length == 0) {
            return JSON.toJSONString(new Response(0, BACK.RESOURCENOTHASROLE.defineState, LEVEL.WARN.name(), BACK.RESOURCENOTHASROLE.detailLog));
        }
        DJResource test = resourceService.findResourceByResourceUrl(resource.pattern, systemId);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.RESOURCEISEXIST.defineState, LEVEL.WARN.name(), BACK.RESOURCEISEXIST.detailLog));
        resource.operator = getCurrentOperator(request);
        resource.createTime = new Date();
        DJSystem system = new DJSystem();
        system.id = systemId;
        resource.system = system;
        List<DJRole> roles = new ArrayList<DJRole>();
        for (Integer id : roleIds) {
            DJRole role = new DJRole();
            role.id = id;
            roles.add(role);
        }
        resource.roles = roles;
        try {
            resourceService.insertResource(resource);
            return JSON.toJSONString(new Response(1, BACK.RESOURCEADDSUCCESS.defineState, LEVEL.INFO.name(), BACK.RESOURCEADDSUCCESS.detailLog));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.defineState, LEVEL.ERROR.name(), BACK.UNKNOW.detailLog));
        }
    }

    @RequestMapping(value = "/addResourcePre.do")
    public String addResourcePre(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findAllSystems());
        return "addResource";
    }

    @RequestMapping(value = "/findResources.do")
    public String findAllResourcesSortBySystem(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findResourcesSortBySystem());
        return "resourceList";
    }

    @RequestMapping(value = "/{id}/updateResource.do")
    public String updateResource(HttpServletRequest request, @PathVariable Integer id) {
        DJResource resource = resourceService.findResourceByResourceId(id);
        request.setAttribute("resource", resource);
        request.setAttribute("system", systemService.findSystemBySystemId(resource.system.id, HibernateSystemUtil.ROLE));
        return "updateResource";
    }

    @RequestMapping(value = "/updateResource.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateResource(DJResource resource, Integer systemId, Integer[] roleIds, HttpServletRequest request) {
        if (systemId == null) {
            resource.system = null;
        } else {
            DJSystem system = new DJSystem();
            system.id = systemId;
            resource.system = system;
        }
        List<DJRole> roles = new ArrayList<DJRole>();
        for (Integer id : roleIds) {
            DJRole role = new DJRole();
            role.id = id;
            roles.add(role);
        }
        resource.roles = roles;
        resource.setOperator(getCurrentOperator(request));
        try {
            resourceService.updateResource(resource);
            return JSON.toJSONString(new Response(1, BACK.RESOURCEUPDATESUCCESS.defineState, LEVEL.INFO.name(), BACK.RESOURCEUPDATESUCCESS.detailLog));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.defineState, LEVEL.ERROR.name(), BACK.UNKNOW.detailLog));
        }
    }

    @RequestMapping(value = "/addUser.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addUser(HttpServletRequest request, DJUser user, Integer systemId, Integer[] roleIds) {
        if (systemId == null) {
            return JSON.toJSONString(new Response(0, BACK.USERNOTHASSYSTEN.defineState, LEVEL.WARN.name(), BACK.USERNOTHASSYSTEN.detailLog));

        }
        if (roleIds == null || roleIds.length == 0) {
            return JSON.toJSONString(new Response(0, BACK.USERNOTHASROLE.defineState, LEVEL.WARN.name(), BACK.USERNOTHASROLE.detailLog));
        }
        DJUser test = userService.findUserByUserName(user.email, systemId);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.USERISEXIST.defineState, LEVEL.WARN.name(), BACK.USERISEXIST.detailLog));
        user.operator = getCurrentOperator(request);
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
            return JSON.toJSONString(new Response(1, BACK.USERADDSUCCESS.defineState, LEVEL.INFO.name(), BACK.USERADDSUCCESS.detailLog));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.defineState, LEVEL.ERROR.name(), BACK.UNKNOW.detailLog));
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
        user.setOperator(getCurrentOperator(request));
        DJUser user_ = userService.findUserByUserId(user.id);
        user.password = user_.password;
        user.passwordState = user_.passwordState;
        try {
            userService.updateUser(user);
            return JSON.toJSONString(new Response(1, BACK.USERUPDATESUCCESS.defineState, LEVEL.INFO.name(), BACK.USERUPDATESUCCESS.detailLog));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.defineState, LEVEL.ERROR.name(), BACK.UNKNOW.detailLog));
        }
    }


    @RequestMapping(value = "/addSystem.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addSystem(HttpServletRequest request, DJSystem system) {
        DJSystem test = systemService.findSystemByName(system.name);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.SYSTEMISEXIST.defineState, LEVEL.WARN.name(), BACK.SYSTEMISEXIST.detailLog));
        DJAdministrator administrator = getCurrentAdministrator(request);
        system.setOperator(administrator.getName());
        system.createTime = new Date();
        try {
            systemService.insertSystem(system);
            return JSON.toJSONString(new Response(1, BACK.SYSTEMADDSUCCESS.defineState, LEVEL.INFO.name(), BACK.SYSTEMADDSUCCESS.detailLog));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.defineState, LEVEL.ERROR.name(), BACK.UNKNOW.detailLog));
        }
    }

    @RequestMapping(value = "/addSystemPre.do")
    public String addSystemPre() {
        return "addSystem";
    }

    @RequestMapping(value = "/findSystem.do")
    public String findAllSystem(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findAllSystems());
        return "systemList";
    }

    @RequestMapping(value = "/{id}/updateSystem.do")
    public String updateSystem(HttpServletRequest request, @PathVariable Integer id) {
        request.setAttribute("system", systemService.findSystemBySystemId(id));
        return "updateSystem";
    }

    @RequestMapping(value = "/updateSystem.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateSystem(DJSystem system, HttpServletRequest request) {
        try {
            system.setOperator(getCurrentOperator(request));
            systemService.updateSystem(system);
            return JSON.toJSONString(new Response(1, BACK.SYSTEMUPDATESUCCESS.defineState, LEVEL.INFO.name(), BACK.SYSTEMUPDATESUCCESS.detailLog));
        } catch (Exception e) {
            logger.error("updateSystem:{}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.defineState, LEVEL.ERROR.name(), BACK.UNKNOW.detailLog));
        }

    }


    @RequestMapping(value = "/addRole.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addRole(HttpServletRequest request, DJRole role) {
        if (role.system.id == null) {
            return JSON.toJSONString(new Response(0, BACK.ROLENOTHASSYSTEN.defineState, LEVEL.ERROR.name(), BACK.ROLENOTHASSYSTEN.detailLog));
        }
        DJRole test = roleService.findRoleByName(role.name, role.system.id);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.ROLEISEXIST.defineState, LEVEL.INFO.name(), BACK.ROLEISEXIST.detailLog));
        DJAdministrator administrator = getCurrentAdministrator(request);
        role.setOperator(administrator.getName());
        role.createTime = new Date();
        try {
            roleService.insertRole(role);
            return JSON.toJSONString(new Response(1, BACK.ROLEADDSUCCESS.defineState, LEVEL.INFO.name(), BACK.ROLEADDSUCCESS.detailLog));
        } catch (Exception e) {
            logger.error("addRole:{}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.defineState, LEVEL.ERROR.name(), BACK.UNKNOW.detailLog));
        }
    }


    @RequestMapping(value = "/{id}/getRoles.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getRolesOfSystem(@PathVariable Integer id) {
        List<DJRole> roles = systemService.findRolesBySystemId(id);
        List<TransientRole> roleList = new ArrayList<TransientRole>();
        for (DJRole role : roles) {
            TransientRole transientRole = new TransientRole();
            transientRole.id = role.id;
            transientRole.name = role.name;
            roleList.add(transientRole);
        }
        JSONObject rolesObject = new JSONObject();
        rolesObject.put("roles", roleList);
        return rolesObject.toJSONString();
    }


    @RequestMapping(value = "/findRoles.do")
    public String findAllRolesSortBySystem(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findRolesSortBySystem());
        return "roleList";
    }

    @RequestMapping(value = "/{id}/updateRole.do", produces = {"application/json;charset=UTF-8"})
    public String updateRole(HttpServletRequest request, @PathVariable Integer id) {
        DJRole role = roleService.findRoleByRoleId(id, HibernateRoleUtil.MENU);
        request.setAttribute("role", role);
        return "updateRole";
    }

    @RequestMapping(value = "/updateRole.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateRole(DJRole role, Integer systemId, HttpServletRequest request, String menuIds) {
        String[] ids = {};
        if (menuIds != null && StringUtils.isNotEmpty(menuIds)) {
            ids = menuIds.split(",");
        }
        if (systemId == null) {
            role.system = null;
        } else {
            DJSystem system = new DJSystem();
            system.id = systemId;
            role.system = system;
        }
        role.setOperator(getCurrentOperator(request));
        Set<DJMenu> menus = new HashSet<DJMenu>();
        if (ids.length != 0) {
            for (String id : ids) {
                DJMenu menu = new DJMenu();
                menu.setId(Integer.parseInt(id));
                menus.add(menu);
            }
        }

        role.menus = menus;
        try {
            roleService.updateRole(role);
            return JSON.toJSONString(new Response(1, BACK.ROLEUPDATESUCCESS.defineState, LEVEL.INFO.name(), BACK.ROLEUPDATESUCCESS.detailLog));

        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.defineState, LEVEL.ERROR.name(), BACK.UNKNOW.detailLog));
        }
    }

    @RequestMapping(value = "/addRolePre.do")
    public String addRolePre(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findAllSystems());
        return "addRole";
    }


    @RequestMapping(value = "/{id}/getMenus.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getMenusOfSystem(@PathVariable Integer id) {
        List<JSONMenu> menuList = new ArrayList<JSONMenu>();
        menuList.addAll(TransitionUtils.batchToJSONMenu(systemService.findMenusBySystemId(id)));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("menus", menuList);
        return jsonObject.toJSONString();
    }


    private DJAdministrator getCurrentAdministrator(HttpServletRequest request) {
        return (DJAdministrator) request.getSession().getAttribute("administrator");
    }

    private String getCurrentOperator(HttpServletRequest request) {
        return getCurrentAdministrator(request).getName();
    }


}
