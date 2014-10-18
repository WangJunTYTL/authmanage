package com.peaceful.auth.data.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wj on 14-5-1.
 */
public class JSONSystem implements Serializable{
    public int id;
    public String name;
    public List<JSONRole> roles = new ArrayList<JSONRole>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JSONRole> getRoles() {
        return roles;
    }

    public void setRoles(List<JSONRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONSystem system = (JSONSystem) o;

        if (id != system.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
