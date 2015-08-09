package com.peaceful.auth.web.util;

import com.peaceful.auth.center.domain.DJFunction;
import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.domain.DJSystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Date 14-9-24.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class ReferenceId {


    public static DJSystem getSystem(Integer id) {
        if (id != null) {
            DJSystem system = new DJSystem();
            system.id = id;
            return system;
        }
        return null;
    }

    public static DJRole getRole(Integer id) {
        if (id != null) {
            DJRole role = new DJRole();
            role.id = id;
            return role;
        }
        return null;
    }

    public static List<DJRole> getRoles(Integer[] ids) {
        if (ids != null && ids.length != 0) {
            List<DJRole> roles = new ArrayList<DJRole>();
            for (Integer id : ids) {
                roles.add(getRole(id));
            }
            return roles;
        }
        return null;
    }

    public static DJFunction getMenu(Integer id) {
        if (id != null) {
            DJFunction menu = new DJFunction();
            menu.id = id;
            return menu;
        }
        return null;
    }

    public static List<DJFunction> getMenus(Integer[] ids) {
        if (ids != null && ids.length != 0) {
            List<DJFunction> menus = new ArrayList<DJFunction>();
            for (Integer id : ids) {
                menus.add(getMenu(id));
            }
            return menus;
        }
        return null;
    }

    public static Set<DJFunction> getMenus_(Integer[] ids) {
        if (ids != null && ids.length != 0) {
            Set<DJFunction> menus = new HashSet<DJFunction>();
            for (Integer id : ids) {
                menus.add(getMenu(id));
            }
            return menus;
        }
        return null;
    }
}
