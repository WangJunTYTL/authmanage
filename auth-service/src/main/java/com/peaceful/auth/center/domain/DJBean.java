package com.peaceful.auth.center.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 14-4-15.
 */
@Entity(name = "t_bean")
public class DJBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    @Column(name = "bean_key")
    public String beanKey;
    public String name;
    public String description;
    @Column(length = 1000)
    public String beans;
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
            name = "t_bean_role",
            inverseJoinColumns = {@JoinColumn(name = "roles_id")},
            joinColumns = {@JoinColumn(name = "beans_id")}
    )
    public List<DJRole> roles = new ArrayList<DJRole>();


    public DJBean() {

    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
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


    public String getName() {
        return name;
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

    public String getBeanKey() {
        return beanKey;
    }

    public void setBeanKey(String beanKey) {
        this.beanKey = beanKey;
    }

    public String getBeans() {
        return beans;
    }

    public void setBeans(String beans) {
        this.beans = beans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DJBean bean = (DJBean) o;

        if (id != null ? !id.equals(bean.id) : bean.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
