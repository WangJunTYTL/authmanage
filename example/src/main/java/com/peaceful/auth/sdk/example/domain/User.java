package com.peaceful.auth.sdk.example.domain;

/**
 * Created by wangjun on 15/1/4.
 */
public class User {
    public long id;
    public String username;
    public String passwd;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
