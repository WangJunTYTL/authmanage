package com.peaceful.auth.center.domain;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangjun on 14-4-17.
 */
@Entity
@Table(name = "t_system")
public class DJSystem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String name;
    @Column(name = "app_key")
    public String appKey;
    public String secret;
    public String token;
    public String operator;
    public String description;

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isdel) {
        this.isDel = isdel;
    }

    @Column(name = "is_del")
    public int isDel;
    @Column(name = "web_index")
    public String webIndex;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "create_time")
    public Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "update_time")
    public Date updateTime = new Date();
    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    public Set<DJUser> users = new HashSet<DJUser>();
    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    public Set<DJFunction> functions = new HashSet<DJFunction>();
    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    public Set<DJBean> beans = new HashSet<DJBean>();
    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    public Set<DJResource> resources = new HashSet<DJResource>();
    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    public Set<DJRole> roles = new HashSet<DJRole>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appkey) {
        this.appKey = appkey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWebIndex() {
        return webIndex;
    }

    public void setWebIndex(String webIndex) {
        this.webIndex = webIndex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Set<DJUser> getUsers() {
        return users;
    }

    public void setUsers(Set<DJUser> users) {
        this.users = users;
    }

    public Set<DJFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<DJFunction> functions) {
        this.functions = functions;
    }

    public Set<DJResource> getResources() {
        return resources;
    }

    public void setResources(Set<DJResource> resources) {
        this.resources = resources;
    }

    public Set<DJRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<DJRole> roles) {
        this.roles = roles;
    }

    public Set<DJBean> getBeans() {
        return beans;
    }

    public void setBeans(Set<DJBean> beans) {
        this.beans = beans;
    }
}
