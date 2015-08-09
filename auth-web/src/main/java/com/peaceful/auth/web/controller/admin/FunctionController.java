package com.peaceful.auth.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.peaceful.auth.center.Service.FunctionService;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.domain.DJFunction;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJSystem;
import com.peaceful.auth.center.util.HibernateSystemUtil;
import com.peaceful.auth.data.domain.JSONFunction;
import com.peaceful.auth.web.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping("admin")
public class FunctionController {


    @Autowired
    private SystemService systemService;

    @Autowired
    private FunctionService functionService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


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
