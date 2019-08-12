package com.sunppenergy.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sunppenergy.entity.SysRoleEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统优化页面操作类
 *
 * @Author: admin
 * @Date: 2019/7/26 14:59
 * @Description:
 */
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 2750841132019718296L;

    private Integer id;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 登录姓名
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 联系号码
     */
    private String phone;

    /**
     * 删除标志位（0:未删除 1：已删除）
     */
    private Integer deleteFlag;

    private Integer substationId;

    private Date createTime;

    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    private List<Integer> roleIds;

    private Set<SysAuthVO> auths;
    /**
     * 属性不与数据库字段映射
     */
    private Set<SysRoleVO> roles = new HashSet<SysRoleVO>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Set<SysRoleVO> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRoleVO> roles) {
        this.roles = roles;
    }

    public Integer getSubstationId() {
        return substationId;
    }

    public void setSubstationId(Integer substationId) {
        this.substationId = substationId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public Set<SysAuthVO> getAuths() {
        return auths;
    }

    public void setAuths(Set<SysAuthVO> auths) {
        this.auths = auths;
    }

    @Override
    public String toString() {
        return "SysUserVO{" +
                "id=" + id +
                ", realName='" + realName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", substationId=" + substationId +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                ", roles=" + roles +
                '}';
    }
}
