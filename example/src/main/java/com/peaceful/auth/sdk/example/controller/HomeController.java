package com.peaceful.auth.sdk.example.controller;

import com.alibaba.fastjson.JSON;
import com.peaceful.auth.api.AuthService;
import com.peaceful.auth.sdk.example.other.RequestContext;
import com.peaceful.auth.sdk.example.outer.api.AuthApi;
import com.peaceful.auth.spring.AUTH;
import com.peaceful.web.util.Http;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangjun on 15/1/2.
 */
@Controller
@RequestMapping()
public class HomeController {
    AuthService authService = AuthApi.getAuthService();

    @RequestMapping({"","index","home"})
    public String home(){
        return "index";
    }

    @RequestMapping({"sys","getSys"})
    @AUTH.Function("getSysInfo")
    public void getSystemInfo() {
        Http.responseJSON(0,JSON.toJSONString(authService.getSystem()));
    }

    @AUTH.RequireLogin
    @RequestMapping("me")
    public void getPersonInfo(){
        Http.responseJSON(0,JSON.toJSONString(authService.getUser(RequestContext.getCurrentUser())));
    }
}
