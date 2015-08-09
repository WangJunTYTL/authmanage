package com.peaceful.auth.web.util;

import com.peaceful.auth.center.domain.DJAdministrator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WangJun <wangjuntytl@163.com>
 * @version 1.0 15/8/9
 * @since 1.6
 */

public class ControllerUtil {


    public static String getCurrentOperator(HttpServletRequest request) {
        return getCurrentAdministrator(request).getName();
    }

    public static DJAdministrator getCurrentAdministrator(HttpServletRequest request) {
        return (DJAdministrator) request.getSession().getAttribute("administrator");
    }
}
