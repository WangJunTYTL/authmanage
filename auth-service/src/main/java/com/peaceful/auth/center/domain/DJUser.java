package com.peaceful.auth.center.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by wangjun on 14-4-15.
 */
@Entity(name = "t_user")
public class DJUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String email;
    public String name;
    public String password;
    @Column(name = "password_state")
    public int passwordState; // 0 初始密码，1 已修改
    public String operator;
    @Column(name = "is_del")
    public int isDel;
    @ManyToOne(cascade = {CascadeType.MERGE})
    public DJSystem system = new DJSystem();
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    public List<DJRole> roles = new ArrayList<DJRole>();
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "create_time")
    public Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "update_time")
    public Date updateTime = new Date();

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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPasswordState() {
        return passwordState;
    }

    public void setPasswordState(int passwordState) {
        this.passwordState = passwordState;
    }
}
