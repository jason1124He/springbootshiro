package com.sunppenergy.vo;

import java.io.Serializable;

/**
 * @Author: admin
 * @Date: 2019/7/29 14:37
 * @Description:
 */
public class RoleAuthVO implements Serializable {


    private static final long serialVersionUID = -4019909130072689004L;

    private Integer id;

    private Integer roleId;

    private Integer authId;

    private String name;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
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

    @Override
    public String toString() {
        return "RoleAuthVO{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", authId=" + authId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
