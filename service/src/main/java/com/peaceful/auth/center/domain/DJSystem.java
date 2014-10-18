package com.peaceful.auth.center.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangjun on 14-4-17.
 */
@Entity
@Table(name = "system")
public class DJSystem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String name;
    public String operator;
    public String description;

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public int isdel;
    @Column(name = "web_index")
    public String webIndex;
    @Column(name = "create_time")
    public Date createTime;
    @Column(name = "update_time")
    public Date updateTime= new Date();
    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    public Set<DJUser> users = new HashSet<DJUser>();
    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    public Set<DJMenu> menus = new HashSet<DJMenu>();
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

    public Set<DJMenu> getMenus() {
        return menus;
    }

    public void setMenus(Set<DJMenu> menus) {
        this.menus = menus;
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
}
