package com.peaceful.auth.center.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by wangjun on 14-4-15.
 */
@Entity(name = "resource")
public class DJResource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String pattern;
    public String description;
    public String operator;
    public int isdel;
    @ManyToOne(cascade = {CascadeType.MERGE})
    public DJSystem system = new DJSystem();
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    public List<DJRole> roles = new ArrayList<DJRole>();
    @Column(name = "create_time")
    public Date createTime;
    @Column(name = "update_time")
    public Date updateTime = new Date();

    public DJResource() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public DJSystem getSystem() {
        return system;
    }

    public void setSystem(DJSystem system) {
        this.system = system;
    }

    public List<DJRole> getRoles() {
        return roles;
    }

    public void setRoles(List<DJRole> roles) {
        this.roles = roles;
    }
}
