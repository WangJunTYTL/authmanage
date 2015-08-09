package com.peaceful.auth.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.peaceful.auth.center.Service.BeanService;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.domain.DJBean;
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
public class BeanController {

    @Autowired
    BeanService beanService;

    @Autowired
    SystemService systemService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/beans.do")
    public String findAllBeansOfSystem() {
        List<DJSystem> systems = systemService.findBeansSortBySystem();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("beans", systems);
        modelAndView.setViewName("beans");
        return "beans";
    }

    @RequestMapping(value = "/add/bean.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addBean(HttpServletRequest request, DJBean bean, Integer systemId, Integer[] roleIds, Integer groupId) {
        if (systemId == null) {
            return JSON.toJSONString(new Response(0, BACK.BEANNOTHASSYSTEN.code, LEVEL.ERROR.name(), BACK.BEANNOTHASSYSTEN.result));
        }
        if (roleIds == null || roleIds.length == 0) {
            return JSON.toJSONString(new Response(0, BACK.BEANNOTHASROLE.code, LEVEL.WARN.name(), BACK.BEANNOTHASROLE.result));
        }
        DJBean test = beanService.findBeanByBeankey(bean.beanKey, systemId);
        if (test != null)
            return JSON.toJSONString(new Response(0, BACK.BEANISEXIST.code, LEVEL.WARN.name(), BACK.BEANISEXIST.result));
        bean.operator = ControllerUtil.getCurrentOperator(request);
        bean.createTime = new Date();
        DJSystem system = new DJSystem();
        system.id = systemId;
        bean.system = system;
        List<DJRole> roles = new ArrayList<DJRole>();
        if (roleIds != null) {
            for (Integer id : roleIds) {
                DJRole role = new DJRole();
                role.id = id;
                roles.add(role);
            }
        }
        bean.roles = roles;
        try {
            beanService.insertBean(bean);
            return JSON.toJSONString(new Response(1, BACK.BEANADDSUCCESS.code, LEVEL.INFO.name(), BACK.BEANADDSUCCESS.result));
        } catch (Exception e) {
            logger.error("addBean:[}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }

    @RequestMapping(value = "/add/bean/pre.do")
    public String addBeanPre(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findAllSystems());
        return "addBean";
    }


    @RequestMapping(value = "/find/beans.do")
    public String findAllBeansSortBySystem(HttpServletRequest request) {
        request.setAttribute("systems", systemService.findBeansSortBySystem());
        return "beanList";
    }


    @RequestMapping(value = "/{id}/update/bean.do")
    public String updateBean(HttpServletRequest request, @PathVariable Integer id) {
        DJBean bean = beanService.findBeanByBeanId(id);
        request.setAttribute("bean", bean);
        request.setAttribute("system", systemService.findSystemBySystemId(bean.system.id, HibernateSystemUtil.ROLE));
        return "updateBean";
    }


    @RequestMapping(value = "/update/bean.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateBean(DJBean bean, Integer systemId, Integer[] roleIds, HttpServletRequest request) {
        if (systemId == null) {
            bean.system = null;
        } else {
            DJSystem system = new DJSystem();
            system.id = systemId;
            bean.system = system;
        }
        List<DJRole> roles = new ArrayList<DJRole>();
        if (roleIds != null) {
            for (Integer id : roleIds) {
                DJRole role = new DJRole();
                role.id = id;
                roles.add(role);
            }
        }
        bean.roles = roles;
        bean.setOperator(ControllerUtil.getCurrentOperator(request));
        try {
            beanService.updateBean(bean);
            return JSON.toJSONString(new Response(1, BACK.BEANUPDATESUCCESS.code, LEVEL.INFO.name(), BACK.BEANUPDATESUCCESS.result));
        } catch (Exception e) {
            logger.error("updateBean:{}", e);
            return JSON.toJSONString(new Response(0, BACK.UNKNOW.code, LEVEL.ERROR.name(), BACK.UNKNOW.result));
        }
    }


}
