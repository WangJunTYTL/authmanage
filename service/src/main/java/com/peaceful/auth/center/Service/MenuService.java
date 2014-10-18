package com.peaceful.auth.center.Service;

import com.peaceful.auth.center.domain.DJMenu;

/**
 * Created by wangjun on 14-4-17.
 */
public interface MenuService {
    void insertMenu(DJMenu menu);

    DJMenu findMenuByMenukey(String key, Integer systemId);

    /**
     * 级联其角色对象
     *
     * @param menuId
     * @return
     */
    DJMenu findMenuByMenuId(Integer menuId);

    void updateMenu(DJMenu menu);
}
