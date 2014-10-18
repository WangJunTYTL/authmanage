package com.peaceful.auth.center.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 14-4-15.
 */
@Entity(name = "adminatrator")
public class DJAdministrator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String name;
    public String password;
    public int isdel;
    public String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "create_time")
    public Date createTime;
    @Column(name = "update_time")
    public Date updateTime = new Date();
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    public List<DJRole> roles = new ArrayList<DJRole>();

    public String getName() {
        return name;
    }

    public DJAdministrator() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
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

    public List<DJRole> getRoles() {
        return roles;
    }

    public void setRoles(List<DJRole> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
