package com.peaceful.auth.web.util;

import com.peaceful.auth.center.domain.DJFunction;
import com.peaceful.auth.center.domain.DJResource;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJUser;
import com.peaceful.auth.data.domain.JSONFunction;
import com.peaceful.auth.data.domain.JSONResource;
import com.peaceful.auth.data.domain.JSONRole;
import com.peaceful.auth.data.domain.JSONUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by wangjun on 14-8-21.
 */
public class TransitionUtils {

    public static JSONRole toJSONRole(DJRole role) {
        if (role == null)
            return null;
        JSONRole jsonRole = new JSONRole();
        jsonRole.setId(role.getId());
        jsonRole.setName(role.getName());
        jsonRole.setOperator(role.getOperator());
        jsonRole.setDescription(role.getDescription());
        jsonRole.setCreateTime(role.getCreateTime());
        jsonRole.setIsdel(role.getIsDel());
        return jsonRole;
    }
    public static JSONFunction toJSONFunction(DJFunction menu) {
        if (menu == null)
            return null;
        JSONFunction jsonMenu = new JSONFunction();
        jsonMenu.setId(menu.getId());
        jsonMenu.setName(menu.getName());
        jsonMenu.setParentId(menu.getParentFunction()==null?null:menu.getParentFunction().id);
        jsonMenu.setIsDel(menu.isDel);
        jsonMenu.setFunctionKey(menu.functionKey);
        jsonMenu.setUrl(menu.url);
        return jsonMenu;
    }

    public static JSONResource toJSONResource(DJResource resource) {
        if (resource == null)
            return null;
        JSONResource jsonResource = new JSONResource();
        jsonResource.setId(resource.getId());
        jsonResource.setPattern(resource.getPattern());
        return jsonResource;
    }

    public static List<JSONResource> batchToJSONResource(List<DJResource> resources) {
        if (resources == null) {
            return null;
        }
        List<JSONResource> jsonResources = new ArrayList<JSONResource>();
        for (DJResource resource : resources) {
            jsonResources.add(toJSONResource(resource));
        }
        return jsonResources;
    }

    public static List<JSONResource> batchToJSONResource(Set<DJResource> resources) {
        if (resources == null) {
            return null;
        }
        List<JSONResource> jsonResources = new ArrayList<JSONResource>();
        for (DJResource resource : resources) {
            jsonResources.add(toJSONResource(resource));
        }
        return jsonResources;
    }
    public static List<JSONFunction> batchToJSONFunction(List<DJFunction> menus) {
        if (menus == null) {
            return null;
        }
        List<JSONFunction> jsonMenus = new ArrayList<JSONFunction>();
        for (DJFunction menu : menus) {
            jsonMenus.add(toJSONFunction(menu));
        }
        return jsonMenus;
    }
    public static List<JSONFunction> batchToJSONFunction(Set<DJFunction> menus) {
        if (menus == null) {
            return null;
        }
        List<JSONFunction> jsonMenus = new ArrayList<JSONFunction>();
        for (DJFunction menu : menus) {
            jsonMenus.add(toJSONFunction(menu));
        }
        return jsonMenus;
    }


    public static List<JSONRole> batchToJSONRole(List<DJRole> roles) {
        if (roles == null) {
            return null;
        }
        List<JSONRole> jsonRoles = new ArrayList<JSONRole>();
        for (DJRole role : roles) {
            jsonRoles.add(toJSONRole(role));
        }
        return jsonRoles;
    }


    public static JSONUser toJSONUser(DJUser user) {
        if (user == null)
            return null;
        JSONUser jsonUser = new JSONUser();
        jsonUser.createTime = user.getCreateTime();
        jsonUser.updateTime = user.getUpdateTime();
        jsonUser.email = user.getEmail();
        jsonUser.name = user.getName();
        jsonUser.passwordState=user.passwordState;
        jsonUser.id = user.getId();
        jsonUser.isdel=user.isDel;
        return jsonUser;
    }


    public static List<JSONUser> batchToJSONUser(List<DJUser> users) {
        if (users == null) {
            return null;
        }
        List<JSONUser> jsonUsers = new ArrayList<JSONUser>();
        for (DJUser user : users) {
            jsonUsers.add(toJSONUser(user));
        }
        return jsonUsers;

    }

    public static List<JSONUser> batchToJSONUser(Set<DJUser> users) {
        if (users == null) {
            return null;
        }
        List<JSONUser> jsonUsers = new ArrayList<JSONUser>();
        for (DJUser user : users) {
            jsonUsers.add(toJSONUser(user));
        }
        return jsonUsers;

    }
}
