package com.peaceful.auth.center.ServiceImpl;

import com.peaceful.auth.center.Service.FunctionService;
import com.peaceful.auth.center.dao.FunctionDao;
import com.peaceful.auth.center.dao.SystemDao;
import com.peaceful.auth.center.domain.DJFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "functionService")
public class FunctionServiceImpl implements FunctionService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SystemDao systemDao = null;

    public void setfunctionDao(FunctionDao functionDao) {
        this.functionDao = functionDao;
    }

    @Autowired
    FunctionDao functionDao = null;

    public void setSystemDao(SystemDao systemDao) {
        this.systemDao = systemDao;
    }


    public DJFunction findFunctionByFunctionId(Integer id) {
        DJFunction function = functionDao.findFunctionByFunctionId(id);
        if (function.system != null) {
            logger.info("load system id is {}", function.system.id);
        }
        logger.info("load roles {}", function.roles);
        logger.info("load parent id {}", function.parentFunction == null ?null:function.parentFunction.id);
        return function;
    }


    public void insertFunction(DJFunction function) {
        functionDao.inserte(function);
    }

    @Override
    public DJFunction findFunctionByFunctionkey(String key, Integer systemId) {
        return functionDao.findFunctionuByFunctionkey(key, systemId);
    }

    public void updateFunction(DJFunction function) {
        functionDao.update(function);
    }

}
