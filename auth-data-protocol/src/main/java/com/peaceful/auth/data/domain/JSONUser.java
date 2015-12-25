package com.peaceful.auth.data.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Sdk and authCenter to exchange  data about user
 * <p/>
 * Created by wangjun on 14-4-23.
 */
public class JSONUser implements Serializable {

    /**
     * The user id as the primary key
     */
    public Integer id;
    /**
     * The user login account , recommend email
     */
    public String email;
    /**
     * User's real name
     */
    public String name;
    /**
     * User's  password
     */
    public String password;
    /**
     * Pass state
     * <p/>
     * 0 The System initialization pass
     * 1 The user modified the password
     * 2 Expired pass
     */
    public int passwordState;
    /**
     * Operator manager
     */
    public String operator;
    /**
     * Tag the user is deleted
     */
    public int isdel;
    /**
     * Create a user time
     */
    public Date createTime;
    /**
     * Update a user time
     */
    public Date updateTime;
    /**
     * Users have the resources
     */
    public List<JSONResource> resources = new ArrayList<JSONResource>();
    /**
     * Users have the functions
     */
    public List<JSONMenu> menus = new ArrayList<JSONMenu>();
    /**
     * Users have the roles
     */
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

    public List<JSONMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<JSONMenu> menus) {
        this.menus = menus;
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
