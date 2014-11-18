package com.peaceful.auth.util;

/**
 * Created by wj on 14-4-26.
 */
public enum BACK {
    UNKNOW("未知原因出错", 0),
    MENUADDSUCCESS("添加菜单成功", 1),
    MENUUPDATESUCCESS("修改菜单成功", 2),
    MENUISEXIST("该标识的菜单已存在", 11),
    MENUNOTHASROLE("菜单至少要有一个角色", 15),
    ROLEUPDATESUCCESS("修改角色成功", 3),
    ROLEADDSUCCESS("添加角色成功", 4),
    ROLEISEXIST("同名的角色已经存在,换个名字吧", 10),
    SYSTEMADDSUCCESS("添加系统成功", 5),
    SYSTEMISEXIST("同名系统已存在，换个名字吧", 15),
    SYSTEMUPDATESUCCESS("修改系统成功", 6),
    USERUPDATESUCCESS("修改用户成功", 7),
    USERADDSUCCESS("加入用户成功", 8),
    USERNOTHASROLE("用户至少要有一种角色", 16),
    USERISEXIST("同名用户已被添加过", 9),
    RESOURCEADDSUCCESS("资源加入成功", 12),
    RESOURCEUPDATESUCCESS("资源修改成功", 13),
    RESOURCENOTHASROLE("资源至少要有一种角色", 17),
    RESOURCEISEXIST("要添加的资源已存在", 14),
    MENUNOTHASSYSTEN("菜单所属系统为空", -1),
    ROLENOTHASSYSTEN("角色所属系统为空", -2),
    USERNOTHASSYSTEN("用户所属系统为空", -3),
    RESOURCENOTHASSYSTEN("资源所属系统为空", -4),
    MENUCYCLE("你不可以选择你正要修改菜单的下级菜单作为其上一级菜单", -7);
    public String detailLog;
    public int defineState;

    BACK(String reason, int state) {
        this.detailLog = reason;
        this.defineState = state;
    }


}
