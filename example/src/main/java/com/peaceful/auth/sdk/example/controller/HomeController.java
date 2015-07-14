package com.peaceful.auth.sdk.example.controller;

import com.alibaba.fastjson.JSON;
import com.peaceful.auth.api.AuthService;
import com.peaceful.auth.sdk.example.domain.User;
import com.peaceful.auth.sdk.example.other.Contstant;
import com.peaceful.auth.sdk.example.other.RequestContext;
import com.peaceful.auth.sdk.example.outer.api.AuthApi;
import com.peaceful.auth.spring.AUTH;
import com.peaceful.common.util.Http;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 15/1/2.
 */
@Controller
@RequestMapping()
public class HomeController {
    AuthService authService = AuthApi.getAuthService();
    private final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    @RequestMapping({"", "index", "home"})
    public String home() {
        return "index";
    }

    @RequestMapping({"sys", "getSys"})
    @AUTH.Function("getSysInfo")
    public void getSystemInfo() {
        Http.responseJSON(0, JSON.toJSONString(authService.getSystem()));
    }

    @AUTH.RequireLogin
    @RequestMapping("me")
    @ResponseBody
    public void getPersonInfo() {
        Http.responseJSON(0, JSON.toJSONString(authService.getUser((String) Http.getRequest().getSession().getAttribute(Contstant.CURRENT_USER))));
    }

    @RequestMapping("guest")
    @AUTH.Role("guest")
    public String getGuest() {
        return "guest";
    }


    @RequestMapping("login")
    @ResponseBody
    public void login(@RequestParam String email, @RequestParam String passwd) {
        //通过权限中心添加的用户默认密码10000，改密码被加密且不可逆，所以需要在这里先处理下密码
        passwd = md5PasswordEncoder.encodePassword(passwd, Contstant.DEFAULT_PASSWORD_SALT);
        if (authService.identificationEmail(email, passwd)) {
            Http.getRequest().getSession().setAttribute(Contstant.CURRENT_USER, email);
            Http.responseJSON(2, "login suc");
        } else {
            Http.responseJSON(-1, "邮箱或密码错误");
        }
    }

    @RequestMapping("logout")
    @ResponseBody
    public void logout() {
        Http.getRequest().getSession().removeAttribute(Contstant.CURRENT_USER);
        Http.responseJSON(1, "你已安全退出");
    }
}
