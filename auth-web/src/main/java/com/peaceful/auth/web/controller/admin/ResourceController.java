package com.peaceful.auth.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.peaceful.auth.center.Service.ResourceService;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.domain.DJResource;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJSystem;
import com.peaceful.auth.center.util.HibernateSystemUtil;
import com.peaceful.auth.web.util.BACK;
import com.peaceful.auth.web.util.ControllerUtil;
import com.peaceful.auth.web.util.LEVEL;
import com.peaceful.auth.web.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ResourceController {


    @Autowired
    private SystemService systemService;

    @Autowired
    private ResourceService resourceService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



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

}
