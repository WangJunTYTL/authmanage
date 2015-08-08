package com.peaceful.auth.center.ServiceImpl;

import com.peaceful.auth.center.domain.DJRole;
import com.peaceful.auth.center.Service.RoleService;
import com.peaceful.auth.center.dao.RoleDao;
import com.peaceful.auth.center.dao.SystemDao;
import com.peaceful.auth.center.util.HibernateRoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangjun on 14-4-17.
 */
@Component(value = "roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    SystemDao systemDao = null;
    @Autowired
    RoleDao roleDao = null;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setSystemDao(SystemDao systemDao) {
        this.systemDao = systemDao;
    }


    public DJRole findRoleByRoleId(Integer id) {
        return roleDao.findRoleById(id);
    }

    public DJRole findRoleByRoleId(Integer roleId, Integer casecadeType) {
        DJRole role = roleDao.findRoleById(roleId);
        HibernateRoleUtil.load(role,casecadeType);
        return role;
    }


    public void insertRole(DJRole role) {
        roleDao.inserte(role);

    }

    public DJRole findRoleByName(String name, Integer systemId) {
        return roleDao.findRoleByName(name,systemId);
    }

    public void updateRole(DJRole role) {
        roleDao.update(role);
    }

}
