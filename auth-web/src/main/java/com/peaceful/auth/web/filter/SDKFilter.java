package com.peaceful.auth.web.filter;

import com.alibaba.fastjson.JSON;
import com.peaceful.auth.center.Service.SystemService;
import com.peaceful.auth.center.ServiceImpl.AppContextService;
import com.peaceful.auth.data.util.VCS;
import com.peaceful.auth.web.util.SDKSessionFormat;
import com.peaceful.auth.web.util.Util;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wj on 14-4-24.
 */
public class SDKFilter implements Filter {

    SystemService systemService;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (verify(request, response) == 1) {
            return;
        }
        updateVcs(request);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }


    private void updateVcs(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path.indexOf("/css") != -1 || path.indexOf("/js") != -1 || path.indexOf("/image") != -1) {
            //检测资源文件
        } else if (path.indexOf("add") != -1 || path.indexOf("insert") != -1 || path.indexOf("update") != -1) {
            Util.report("将要修改VCS，当前版本；" + VCS.getCurrentVersion() + "触发修改的URL：" + path);
            VCS.updateCurrentVersion();
        }
    }

    private int verify(HttpServletRequest request, HttpServletResponse response) {
        if (systemService == null) {
            systemService = AppContextService.applicationContext.getBean(SystemService.class);
        }
        String systemId = request.getParameter("systemId");
        String token = request.getParameter("token");

        if (StringUtils.isBlank(systemId) || StringUtils.isBlank(token)) {
            responseString(response, JSON.toJSONString(new SDKSessionFormat(1, "Error", "参数不对")));
            return 1;
        }

        String token_ = systemService.allTokens.get(systemId);
        if (StringUtils.isEmpty(token_)) {
            systemService.refreshTokensOfAllSystem();
            token_ = systemService.allTokens.get(Integer.valueOf(systemId));
        }

         if (token.equals(token_)) {
            // pass
        } else {
            responseString(response, JSON.toJSONString(new SDKSessionFormat(1, "Error", "请求非法")));
            return 1;
        }
        return 0;
    }

    private static void responseString(HttpServletResponse response, String str) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter e = response.getWriter();
            e.write(str);
            e.flush();
            e.close();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

}
