package util;

import com.peaceful.auth.center.domain.*;

import java.util.*;

/**
 * Created by wangjun on 14-4-17.
 */
public class DomainUtil {

    public static DJSystem getSystem() {
        DJSystem djSystem = new DJSystem();
        djSystem.createTime = new Date();
        djSystem.description = "this is system";
        djSystem.operator = "it is me";
        djSystem.name = "this is system";
        djSystem.webIndex="welcome page";
        return djSystem;
    }

    public static DJAdministrator getAdministrator() {
        DJAdministrator djAdministrator = new DJAdministrator();
        djAdministrator.createTime = new Date();
        djAdministrator.isdel = 1;
        djAdministrator.name = "wj@163.com";
        return djAdministrator;
    }

    public static DJMenu getMenu() {
        DJMenu menu = new DJMenu();
        menu.createTime = new Date();
        menu.description = "this is menu";
        menu.isdel = 1;
        menu.operator = "it is me";
        menu.name = "菜单";
        menu.url = "url is http://";
        return menu;
    }

    public static DJRole getRole() {
        DJRole role = new DJRole();
        role.operator="it is me";
        role.createTime = new Date();
        role.isdel=1;
        role.name="this is role";
        return role;
    }

    public static List<DJSystem> getSystemList(int size) {
        List<DJSystem> systems = new ArrayList<DJSystem>();
        for (int i = 0; i < size; i++) {
            DJSystem djSystem = new DJSystem();
            djSystem.createTime = new Date();
            djSystem.description = "this is system " + i;
            djSystem.operator = "it is me " + i;
            djSystem.name = "this is system " + i;
            systems.add(djSystem);
        }
        return systems;
    }

    public static List<DJRole> getRoleList(int size) {
        List<DJRole> systems = new ArrayList<DJRole>();
        for (int i = 0; i < size; i++) {
            DJRole role = new DJRole();
            role.operator="it is me "+i;
            role.createTime = new Date();
            role.isdel=1;
            role.name="this is role "+i;
            systems.add(role);
        }
        return systems;
    }

    public static List<DJResource> getResourceList(int size) {
        List<DJResource> systems = new ArrayList<DJResource>();
        for (int i = 0; i < size; i++) {
            DJResource djResource = new DJResource();
            djResource.createTime = new Date();
            djResource.pattern = "this is pattern " + i;
            systems.add(djResource);
        }
        return systems;
    }

    public static List<DJMenu> getMenuList(int size) {
        List<DJMenu> systems = new ArrayList<DJMenu>();
        for (int i = 0; i < size; i++) {
            DJMenu menu = new DJMenu();
            menu.createTime = new Date();
            menu.description = "this is menu"+i;
            menu.isdel = 1;
            menu.operator = "it is me "+i;
            menu.name = "菜单 "+i;
            menu.url = "url is http:// "+i;
            systems.add(menu);
        }
        return systems;
    }

    public static Set<DJSystem> getSystemSet(int size) {
        Set<DJSystem> systems = new HashSet<DJSystem>();
        for (int i = 0; i < size; i++) {
            DJSystem djSystem = new DJSystem();
            djSystem.createTime = new Date();
            djSystem.description = "this is system " + i;
            djSystem.operator = "it is me " + i;
            djSystem.name = "this is system " + i;
            systems.add(djSystem);
        }
        return systems;
    }
}
