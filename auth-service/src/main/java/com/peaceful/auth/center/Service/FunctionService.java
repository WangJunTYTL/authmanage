package com.peaceful.auth.center.Service;

import com.peaceful.auth.center.domain.DJFunction;

/**
 * Created by wangjun on 14-4-17.
 */
public interface FunctionService {
    void insertFunction(DJFunction function);

    DJFunction findFunctionByFunctionkey(String key, Integer systemId);

    /**
     * 级联其角色对象
     *
     * @param functionId
     * @return
     */
    DJFunction findFunctionByFunctionId(Integer functionId);

    void updateFunction(DJFunction function);
}
