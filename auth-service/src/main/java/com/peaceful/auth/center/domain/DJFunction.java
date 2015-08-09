package com.peaceful.auth.center.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by wangjun on 14-4-15.
 */
@Entity(name = "t_function")
public class DJFunction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    @Column(name = "function_key")
    public String functionKey;
    public String name;
    public String url;
    public String description;
    public String operator;
    @Column(name = "is_del")
    public int isDel;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "create_time")
    public Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "update_time")
    public Date updateTime = new Date();
    @ManyToOne(cascade = {CascadeType.MERGE})
    public DJSystem system = new DJSystem();
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "t_function_role",
            inverseJoinColumns = {@JoinColumn(name = "roles_id")},
            joinColumns = {@JoinColumn(name = "functions_id")}
    )
    public List<DJRole> roles = new ArrayList<DJRole>();
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    public DJFunction parentFunction;


    public DJFunction() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFunctionKey(String functionKey) {
        this.functionKey = functionKey;
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

    public void setIsDel(int isdel) {
        this.isDel = isdel;
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

    public String getFunctionKey() {
        return functionKey;
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

    public int getIsDel() {
        return isDel;
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

    public DJFunction getParentFunction() {
        return parentFunction;
    }

    public void setParentFunction(DJFunction parentFunction) {
        this.parentFunction = parentFunction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DJFunction function = (DJFunction) o;

        if (id != null ? !id.equals(function.id) : function.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
