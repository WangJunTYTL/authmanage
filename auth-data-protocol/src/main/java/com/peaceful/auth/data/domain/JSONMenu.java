package com.peaceful.auth.data.domain;

import java.io.Serializable;

/**
 * Sdk and authCenter to exchange  data about functions
 * <p/>
 * A function may be an object of a method or multiple methods, or multiple methods of different objects
 * <p/>
 *
 * @author wangjun
 * @version 1.0 14-4-23
 * @since 1.6
 */
public class JSONMenu implements Serializable {
    /**
     * The user id as the primary key
     */
    public Integer id;
    /**
     * The unique identification of the function
     */
    public String menukey;
    /**
     * The name of this function
     */
    public String name;
    /**
     * The url of this function
     */
    public String url;
    /**
     * The parent of this function
     */
    public Integer parentId;
    /**
     * Tag the function is deleted
     */
    public int isdel;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONMenu menu = (JSONMenu) o;

        if (id != null ? !id.equals(menu.id) : menu.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenukey() {
        return menukey;
    }

    public void setMenukey(String menukey) {
        this.menukey = menukey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }
}
