package com.peaceful.auth.center.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by wangjun on 14-4-15.
 */
@Entity(name = "t_resource")
public class DJResource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String pattern;
    public String description;
    public String operator;
    @Column(name = "is_del")
    public int isDel;
    @ManyToOne(cascade = {CascadeType.MERGE})
    public DJSystem system = new DJSystem();
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    public List<DJRole> roles = new ArrayList<DJRole>();
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @Column(name = "create_time")
    public Date createTime;
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
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

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isdel) {
        this.isDel = isdel;
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
