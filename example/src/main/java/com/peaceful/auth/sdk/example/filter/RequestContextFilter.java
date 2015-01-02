package com.peaceful.auth.sdk.example.filter;

import com.peaceful.auth.sdk.example.other.Contstant;
import com.peaceful.auth.sdk.example.other.RequestContext;
import com.peaceful.common.util.StringUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by wangjun on 15/1/2.
 */
public class RequestContextFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String user  = (String) servletRequest.getAttribute(Contstant.CURRENT_USER);
        if (StringUtils.isNotEmpty(user)){
            RequestContext.setCurrentUser(user);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
