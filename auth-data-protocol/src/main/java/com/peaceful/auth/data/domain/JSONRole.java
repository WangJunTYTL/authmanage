package com.peaceful.auth.data.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 14-4-23.
 */
public class JSONRole implements Serializable {

    public Integer id;
    public String name;
    public String operator;
    public String description;
    public List<JSONUser> users;
    public List<JSONFunction> functions;
    public int isdel;
    public Date createTime;
    public List<JSONUser> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONRole role = (JSONRole) o;

        if (id != null ? !id.equals(role.id) : role.id != null) return false;

        return true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void setUsers(List<JSONUser> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public List<JSONFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(List<JSONFunction> functions) {
        this.functions = functions;
    }
}
