package com.peaceful.auth.center.util;

import com.peaceful.auth.center.domain.DJRole;

import java.util.List;

/**
 * Created by wangjun on 14-4-17.
 */
public class HibernateRoleUtil {
    public static final int USER = 0;
    public static final int MENU = 1;
    public static final int MENUANDPARENT = 8;
    public static final int RESOURCE = 2;
    public static final int ROLE = 3;
    public static final int ROLEANDUSER = 5;
    public static final int ALL = 7;

    public static void load(List<DJRole> roles, int... casecadeType) {
        for (DJRole role : roles) {
            load(role, casecadeType);
        }
    }

    public static void load(DJRole role, int... casecadeType) {
        for (int i : casecadeType) {
            switch (i) {
                case 0: {
                    role.users.size();
                    break;
                }
                case 1: {
                    role.menus.size();
                    break;
                }
                case 2: {
                    role.resources.size();
                    break;
                }


                case 7: {
                    role.users.size();
                    role.menus.size();
                    role.resources.size();

                    break;
                }

            }
        }
    }
}
