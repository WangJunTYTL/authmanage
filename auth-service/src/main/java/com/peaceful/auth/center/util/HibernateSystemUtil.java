package com.peaceful.auth.center.util;

import com.peaceful.auth.center.domain.DJSystem;

import java.util.List;

/**
 * Created by wangjun on 14-4-17.
 */
public class HibernateSystemUtil {
    public static final int USER = 0;
    public static final int FUNCTION = 1;
    public static final int FUNCTIONANDPARENT = 8;
    public static final int RESOURCE = 2;
    public static final int ROLE = 3;
    public static final int ROLEANDUSER = 5;
    public static final int ALL = 7;

    public static void systemLoad(List<DJSystem> systems, int... types) {
        for (DJSystem system : systems) {
            load(system, types);
        }
    }

    public static void load(DJSystem system, int... cascadeType) {
        if(system == null) return;

        for (int i : cascadeType) {
            switch (i) {
                case 0: {
                    system.users.size();
                    break;
                }
                case 1: {
                    system.functions.size();
                    break;
                }
                case 2: {
                    system.resources.size();
                    break;
                }
                case 3: {
                    system.roles.size();
                    break;
                }

                case 7: {
                    system.roles.size();
                    system.users.size();
                    system.functions.size();
                    system.resources.size();

                    break;
                }
                case 5: {
                    system.roles.size();
                    if (system != null)
                        if (system.roles != null && system.roles.size() != 0)
                            system.roles.iterator().hasNext();
                    break;
                }
            }
        }
    }
}
