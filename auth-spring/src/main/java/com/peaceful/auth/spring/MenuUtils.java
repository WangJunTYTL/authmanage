package com.peaceful.auth.spring;

import com.peaceful.auth.Impl.AuthServiceImpl;
import com.peaceful.auth.api.AuthService;
import com.peaceful.auth.data.domain.JSONMenu;
import com.peaceful.auth.exception.CreateSessionException;
import com.peaceful.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Date 14-10-11.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class MenuUtils extends TagSupport {

    private static Logger logger = LoggerFactory.getLogger(MenuUtils.class);
    private static final AuthContext AUTH_CONTEXT = AuthContext.getAuthContext();

    private String menuKey;
    private String menuLevel;
    private String otherAttr;
    private String leftSpace;

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public void setMenuLevel(String menuLevel) {
        this.menuLevel = menuLevel;
    }

    public void setOtherAttr(String otherAttr) {
        this.otherAttr = otherAttr;
    }

    public void setLeftSpace(String leftSpace) {
        this.leftSpace = leftSpace;
    }

    public static String getFirstLevelMenu(String menuKey) throws CreateSessionException {
        AuthService authService = AuthServiceImpl.getAuthService();
        JSONMenu menu = authService.getMenu(menuKey, AUTH_CONTEXT.getCurrentUser());
        if (menu != null)
            return "<li class=\"active\" id='" + menuKey + "' ><a  href=\"" + menu.getUrl() + "\" >" + menu.getName() + "</a></li>\n";
        return "";
    }

    public static String getSecondLevelMenu(String menuKey) throws CreateSessionException {
        AuthService authService = AuthServiceImpl.getAuthService();
        JSONMenu menu = authService.getMenu(menuKey, AUTH_CONTEXT.getCurrentUser());
        if (menu != null)
            return "<div class=\"col-md-1\"><a class=\"btn btn-primary\"  id = '" + menuKey + "' href= '" + menu.getUrl() + "'" + ">" + menu.getName() + "</a></div>\n";
        return "";
    }

    public static String getSecondLevelMenu(String menuKey, String otherAttr) throws CreateSessionException {
        AuthService authService = AuthServiceImpl.getAuthService();
        JSONMenu menu = authService.getMenu(menuKey, AUTH_CONTEXT.getCurrentUser());
        otherAttr = otherAttr == null ? "" : otherAttr;
        if (menu != null) {
            return "<div class=\"col-md-1\"><a class=\"btn btn-primary\" id=\"" + menuKey + "\" href=\"" + menu.getUrl() + "\"" + otherAttr + ">" + menu.getName() + "</a></div>\n";
        }
        return "";
    }

    public static String getSecondLevelMenu(String menuKey, String otherAttr, String leftSpace) throws CreateSessionException {
        if (StringUtils.isEmpty(otherAttr) && StringUtils.isEmpty(leftSpace)) {
            getSecondLevelMenu(menuKey);
        }
        if (StringUtils.isEmpty(otherAttr) && StringUtils.isNotEmpty(leftSpace)) {
            AuthService authService = AuthServiceImpl.getAuthService();
            JSONMenu menu = authService.getMenu(menuKey, AUTH_CONTEXT.getCurrentUser());
            if (menu != null)
                return "<div class=\"col-md-1\" style=\"margin-left: " + leftSpace + ";\"><a class=\"btn btn-primary\" id=\"" + menuKey + "\" href=\"" + menu.getUrl() + "\">" + menu.getName() + "</a></div>\n";

            return "";
        }

        if (StringUtils.isEmpty(leftSpace)) {
            getSecondLevelMenu(menuKey, otherAttr);
        }
        AuthService authService = AuthServiceImpl.getAuthService();
        JSONMenu menu = authService.getMenu(menuKey, AUTH_CONTEXT.getCurrentUser());
        otherAttr = otherAttr == null ? "" : otherAttr;
        if (menu != null) {
            return "<div class=\"col-md-1\" style=\"margin-left: " + leftSpace + ";\"><a class=\"btn btn-primary\" id=\"" + menuKey + "\" href=\"" + menu.getUrl() + "\"" + otherAttr + ">" + menu.getName() + "</a></div>\n";
        }
        return "";
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            if (menuLevel.equals("L1") || menuLevel.equals("L2")) {
                if (menuLevel.equals("L1"))
                    out.print(getFirstLevelMenu(menuKey));
                else if (menuLevel.equals("L2")) {
                    if (StringUtils.isNotEmpty(otherAttr))
                        out.print(getSecondLevelMenu(menuKey, otherAttr, leftSpace));
                    else
                        out.print(getSecondLevelMenu(menuKey));
                }

            } else {
                out.print("MenuLevel只支持L1和L2");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CreateSessionException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    public static JSONMenu getMenu(String menuKey) throws CreateSessionException {
        AuthService authService = AuthServiceImpl.getAuthService();
        return authService.getMenu(menuKey, AUTH_CONTEXT.getCurrentUser());
    }
}
