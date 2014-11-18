package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJMenu;

/**
 * Created by wangjun on 14-4-15.
 */
public interface MenuDao {

    DJMenu findMenuByMenuId(Integer id);
    DJMenu findMenuByMenukey(String key,Integer systemId);

    abstract void inserte(DJMenu djMenu);

    abstract void update(DJMenu djMenu);

    abstract void delate(DJMenu djMenu);

}
