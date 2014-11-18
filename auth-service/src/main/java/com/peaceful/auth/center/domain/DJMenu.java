package com.peaceful.auth.center.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by wangjun on 14-4-15.
 */
@Entity(name = "menu")
public class DJMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    @Column(name = "menu_key")
    public String menukey;
    public String name;
    public String url;
    public String description;
    public String operator;
    public int isdel;
    @Column(name = "create_time")
    public Date createTime;
    @Column(name = "update_time")
    public Date updateTime = new Date();
    @ManyToOne(cascade = {CascadeType.MERGE})
    public DJSystem system = new DJSystem();
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name="menu_role",
            inverseJoinColumns={@JoinColumn(name="roles_id")},
            joinColumns={@JoinColumn(name="menus_id")}
    )
    public List<DJRole> roles = new ArrayList<DJRole>();
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name="parent_id")
    public DJMenu parentMenu;


    public DJMenu() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMenukey(String menukey) {
        this.menukey = menukey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setSystem(DJSystem system) {
        this.system = system;
    }

    public void setRoles(List<DJRole> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public String getMenukey() {
        return menukey;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getOperator() {
        return operator;
    }

    public int getIsdel() {
        return isdel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public DJSystem getSystem() {
        return system;
    }

    public List<DJRole> getRoles() {
        return roles;
    }

    public DJMenu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(DJMenu parentMenu) {
        this.parentMenu = parentMenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DJMenu menu = (DJMenu) o;

        if (id != null ? !id.equals(menu.id) : menu.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
