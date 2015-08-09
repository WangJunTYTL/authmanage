package com.peaceful.auth.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.peaceful.auth.center.util.HibernateRoleUtil;
import com.peaceful.auth.center.util.HibernateSystemUtil;
import com.peaceful.auth.center.Service.*;
import com.peaceful.auth.center.domain.*;
import com.peaceful.auth.data.util.MD5Utils;
import com.peaceful.auth.data.domain.JSONFunction;
import com.peaceful.auth.web.util.*;
import org.apache.commons.lang3.StringUtils;
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
    private FunctionService functionService;

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
            administrator.setOperator(ControllerUtil.getCurrentOperator(request));
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
            administrator.setOperator(ControllerUtil.getCurrentOperator(request));
            administratorService.update(administrator);
        } catch (Exception e) {
            return "fail";
        }
        return "suc";
    }


    @RequestMapping(value = "/functions.do")
    public String findAllFunctionsOfSystem() {
        List<DJSystem> systems = systemService.findFunctionsSortBySystem();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("functions", systems);
        modelAndView.setViewName("functions");
        return "functions";
    }

    @RequestMapping(value = "/add/function.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addFunction(HttpServletRequest request, DJFunction function, Integer systemId, Integer[] roleIds, Integer groupId) {
        if (systemId == null) {
            return JSON.toJSONString(new Response(0, BACK.FUNCTIONNOTHASSYSTEN.code, LEVEL.ERROR.name(), BACK.FUNCTIONNOTHASSYSTEN.result));
        }
        if (roleIds == null || roleIds.length == 0) {
            return JSON.toJSONString(new Response(0, BACK.FUNCTIONNOTHASROLE.code, LEVEL.WARN.name(), BACK.FUNCTIONNOTHASROLE.result));
        }
        DJFunction test = functionService.findFunctionByFunctionkey(function.functionKey, systemId);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.FUNCTIONISEXIST.code, LEVEL.WARN.name(), BACK.FUNCTIONISEXIST.result));
        function.operator = ControllerUtil.getCurrentOperator(request);
        function.createTime = new Date();
        DJSystem system = new DJSystem();
        system.id = systemId;
        function.system = system;
        List<DJRole> roles = new ArrayList<DJRole>();
        if (roleIds != null) {
            for (Integer id : roleIds) {
                DJRole role = new DJRole();
                role.id = id;
                roles.add(role);
            }
        }
        function.roles = roles;
        if (function.parentFunction == null || function.parentFunction.id == null)
            function.parentFunction = null;
        try {
            functionService.insertFunction(function);
            return JSON.toJSONString(new Response(1, BACK.FUNCTIONADDSUCCESS.code, LEVEL.INFO.name(), BACK.FUNCTIONADDSUCCESS.result));
        } catch (Exception e) {
            logger.error("addFunction:[}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }

    @RequestMapping(value = "/add/function/pre.do")
    public String addFunctionPre(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findAllSystems());
        return "addFunction";
    }


    @RequestMapping(value = "/find/functions.do")
    public String findAllFunctionsSortBySystem(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findFunctionsSortBySystem());
        return "functionList";
    }


    @RequestMapping(value = "/{id}/update/function.do")
    public String updateFunction(HttpServletRequest request, @PathVariable Integer id) {
        DJFunction function = functionService.findFunctionByFunctionId(id);
        request.setAttribute("function", function);
        request.setAttribute("system", systemService.findSystemBySystemId(function.system.id, HibernateSystemUtil.ROLE));
        return "updateFunction";
    }


    @RequestMapping(value = "/update/function.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateFunction(DJFunction function, Integer systemId, Integer[] roleIds, HttpServletRequest request) {
        if (systemId == null) {
            function.system = null;
        } else {
            DJSystem system = new DJSystem();
            system.id = systemId;
            function.system = system;
        }
        List<DJRole> roles = new ArrayList<DJRole>();
        if (roleIds != null) {
            for (Integer id : roleIds) {
                DJRole role = new DJRole();
                role.id = id;
                roles.add(role);
            }
        }
        function.roles = roles;
        function.setOperator(ControllerUtil.getCurrentOperator(request));
        try {
            if (function.parentFunction != null && function.parentFunction.id != null) {
                DJFunction function_ = functionService.findFunctionByFunctionId(function.parentFunction.id);
                if (function_ != null && function_.parentFunction != null && function_.parentFunction.id != null) {
                    if (function_.parentFunction.id.equals(function.id))
                        return JSON.toJSONString(new Response(1, BACK.FUNCTIONCYCLE.code, LEVEL.ERROR.name(), BACK.FUNCTIONCYCLE.result));
                }
            }
            if (function.parentFunction.id == null)
                function.parentFunction = null;
            functionService.updateFunction(function);
            return JSON.toJSONString(new Response(1, BACK.FUNCTIONUPDATESUCCESS.code, LEVEL.INFO.name(), BACK.FUNCTIONUPDATESUCCESS.result));
        } catch (Exception e) {
            logger.error("updateFunction:{}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }

    @RequestMapping(value = "/addResource.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addResource(HttpServletRequest request, DJResource resource, Integer systemId, Integer[] roleIds) {
        if (systemId == null)
            return JSON.toJSONString(new Response(0, BACK.RESOURCENOTHASSYSTEN.code, LEVEL.WARN.name(), BACK.RESOURCENOTHASSYSTEN.result));
        if (roleIds == null || roleIds.length == 0) {
            return JSON.toJSONString(new Response(0, BACK.RESOURCENOTHASROLE.code, LEVEL.WARN.name(), BACK.RESOURCENOTHASROLE.result));
        }
        DJResource test = resourceService.findResourceByResourceUrl(resource.pattern, systemId);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.RESOURCEISEXIST.code, LEVEL.WARN.name(), BACK.RESOURCEISEXIST.result));
        resource.operator = ControllerUtil.getCurrentOperator(request);
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
            return JSON.toJSONString(new Response(1, BACK.RESOURCEADDSUCCESS.code, LEVEL.INFO.name(), BACK.RESOURCEADDSUCCESS.result));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
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
        resource.setOperator(ControllerUtil.getCurrentOperator(request));
        try {
            resourceService.updateResource(resource);
            return JSON.toJSONString(new Response(1, BACK.RESOURCEUPDATESUCCESS.code, LEVEL.INFO.name(), BACK.RESOURCEUPDATESUCCESS.result));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }

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


    @RequestMapping(value = "/addSystem.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addSystem(HttpServletRequest request, DJSystem system) {
        DJSystem test = systemService.findSystemByName(system.name);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.SYSTEMISEXIST.code, LEVEL.WARN.name(), BACK.SYSTEMISEXIST.result));
        DJAdministrator administrator = ControllerUtil.getCurrentAdministrator(request);
        system.setOperator(administrator.getName());
        system.createTime = new Date();
        system.secret = MD5Utils.string2MD5(String.valueOf(System.nanoTime()));
        try {
            systemService.insertSystem(system);
            return JSON.toJSONString(new Response(1, BACK.SYSTEMADDSUCCESS.code, LEVEL.INFO.name(), BACK.SYSTEMADDSUCCESS.result));
        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
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
            system.setOperator(ControllerUtil.getCurrentOperator(request));
            systemService.updateSystem(system);
            return JSON.toJSONString(new Response(1, BACK.SYSTEMUPDATESUCCESS.code, LEVEL.INFO.name(), BACK.SYSTEMUPDATESUCCESS.result));
        } catch (Exception e) {
            logger.error("updateSystem:{}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }

    }


    @RequestMapping(value = "/addRole.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addRole(HttpServletRequest request, DJRole role) {
        if (role.system.id == null) {
            return JSON.toJSONString(new Response(0, BACK.ROLENOTHASSYSTEN.code, LEVEL.ERROR.name(), BACK.ROLENOTHASSYSTEN.result));
        }
        DJRole test = roleService.findRoleByName(role.name, role.system.id);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.ROLEISEXIST.code, LEVEL.INFO.name(), BACK.ROLEISEXIST.result));
        DJAdministrator administrator = ControllerUtil.getCurrentAdministrator(request);
        role.setOperator(administrator.getName());
        role.createTime = new Date();
        try {
            roleService.insertRole(role);
            return JSON.toJSONString(new Response(1, BACK.ROLEADDSUCCESS.code, LEVEL.INFO.name(), BACK.ROLEADDSUCCESS.result));
        } catch (Exception e) {
            logger.error("addRole:{}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
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
        DJRole role = roleService.findRoleByRoleId(id, HibernateRoleUtil.FUNCTION);
        request.setAttribute("role", role);
        return "updateRole";
    }

    @RequestMapping(value = "/updateRole.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateRole(DJRole role, Integer systemId, HttpServletRequest request, String functionIds) {
        String[] ids = {};
        if (functionIds != null && StringUtils.isNotEmpty(functionIds)) {
            ids = functionIds.split(",");
        }
        if (systemId == null) {
            role.system = null;
        } else {
            DJSystem system = new DJSystem();
            system.id = systemId;
            role.system = system;
        }
        role.setOperator(ControllerUtil.getCurrentOperator(request));
        Set<DJFunction> functions = new HashSet<DJFunction>();
        if (ids.length != 0) {
            for (String id : ids) {
                DJFunction function = new DJFunction();
                function.setId(Integer.parseInt(id));
                functions.add(function);
            }
        }

        role.functions = functions;
        try {
            roleService.updateRole(role);
            return JSON.toJSONString(new Response(1, BACK.ROLEUPDATESUCCESS.code, LEVEL.INFO.name(), BACK.ROLEUPDATESUCCESS.result));

        } catch (Exception e) {
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }

    @RequestMapping(value = "/addRolePre.do")
    public String addRolePre(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findAllSystems());
        return "addRole";
    }


    @RequestMapping(value = "/{id}/find/functions.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getFunctionsOfSystem(@PathVariable Integer id) {
        List<JSONFunction> functionList = new ArrayList<JSONFunction>();
        functionList.addAll(TransitionUtils.batchToJSONFunction(systemService.findFunctionsBySystemId(id)));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("functions", functionList);
        return jsonObject.toJSONString();
    }




}
