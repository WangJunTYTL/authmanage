package com.peaceful.auth.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.domain.DJAdministrator;
import com.peaceful.auth.center.domain.DJSystem;
import com.peaceful.auth.data.util.MD5Utils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author WangJun <wangjuntytl@163.com>
 * @version 1.0 15/8/9
 * @since 1.6
 */
@Controller
@RequestMapping("/admin")
public class SystemController {

    @Autowired
    private SystemService systemService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

}
