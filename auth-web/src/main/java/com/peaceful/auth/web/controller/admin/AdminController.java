package com.peaceful.auth.web.controller.admin;

import com.peaceful.auth.center.Service.AdministratorService;
import com.peaceful.auth.center.domain.DJAdministrator;
import com.peaceful.auth.web.util.ControllerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangJun on 14-4-19.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdministratorService administratorService;




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

}
