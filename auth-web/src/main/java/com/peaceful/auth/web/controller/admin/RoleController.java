package com.peaceful.auth.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.peaceful.auth.center.Service.RoleService;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.domain.*;
import com.peaceful.auth.center.util.HibernateRoleUtil;
import com.peaceful.auth.web.util.BACK;
import com.peaceful.auth.web.util.ControllerUtil;
import com.peaceful.auth.web.util.LEVEL;
import com.peaceful.auth.web.util.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author WangJun <wangjuntytl@163.com>
 * @version 1.0 15/8/9
 * @since 1.6
 */
@Controller
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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


}
