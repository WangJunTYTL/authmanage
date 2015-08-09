package com.peaceful.auth.center.dao;


import com.peaceful.auth.center.domain.DJFunction;

/**
 * Created by wangjun on 14-4-15.
 */
public interface FunctionDao {

    DJFunction findFunctionByFunctionId(Integer id);

    DJFunction findFunctionuByFunctionkey(String key, Integer systemId);

    abstract void inserte(DJFunction function);

    abstract void update(DJFunction function);

    abstract void delate(DJFunction function);

}
