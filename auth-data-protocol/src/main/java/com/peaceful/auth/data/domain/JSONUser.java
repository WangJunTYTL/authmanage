package com.peaceful.auth.data.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 14-4-23.
 */
public class JSONUser implements Serializable{

    public Integer id;
    public String email;
    public String name;
    public String password;
    public int passwordState;
    public String operator;
    public int isdel;
    public Date createTime;
    public Date updateTime;
    public List<JSONResource> resources = new ArrayList<JSONResource>();
    public List<JSONFunction> functions = new ArrayList<JSONFunction>();
    public List<JSONRole> roles = new ArrayList<JSONRole>();
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<JSONRole> getRoles() {
        return roles;
    }

    public void setRoles(List<JSONRole> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<JSONResource> getResources() {
        return resources;
    }

    public void setResources(List<JSONResource> resources) {
        this.resources = resources;
    }

    public List<JSONFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(List<JSONFunction> functions) {
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONUser user = (JSONUser) o;

        if (!id.equals(user.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getPasswordState() {
        return passwordState;
    }

    public void setPasswordState(int passwordState) {
        this.passwordState = passwordState;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
