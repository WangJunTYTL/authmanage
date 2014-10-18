package com.peaceful.auth.filter;

import com.peaceful.auth.data.VCS;
import com.peaceful.auth.util.Util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by wj on 14-4-24.
 */
public class VCSFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI();
        if (path.indexOf("/css") != -1 || path.indexOf("/js") != -1 || path.indexOf("/image") != -1) {
            //检测资源文件
        } else if (path.indexOf("add") != -1 || path.indexOf("insert") != -1 || path.indexOf("update") != -1) {
            Util.report("将要修改VCS，当前版本；" + VCS.getCurrentVersion() + "触发修改的URL：" + path);
            VCS.updateCurrentVersion();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
