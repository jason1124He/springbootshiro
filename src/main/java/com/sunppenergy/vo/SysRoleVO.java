package com.sunppenergy.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sunppenergy.entity.SysAuthEntity;

import java.io.Serializable;
import java.util.*;


/**
 * <p>
 *
 * </p>
 *
 * @author hej
 * @since 2019-07-17
 */
public class SysRoleVO implements Serializable {

    private static final long serialVersionUID = 5428943353623049963L;
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 删除标志位（0:未删除 1：已删除）
     */
    private Integer deleteFlag;

    private Date createTime;

    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    /**
     * 属性不与数据库字段映射
     */
    private Set<SysAuthVO> auths = new HashSet<>();

    private List<Integer> authIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<SysAuthVO> getAuths() {
        return auths;
    }

    public void setAuths(Set<SysAuthVO> auths) {
        this.auths = auths;
    }

    public List<Integer> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<Integer> authIds) {
        this.authIds = authIds;
    }

    @Override
    public String toString() {
        return "SysRoleEntity{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", deleteFlag=" + deleteFlag +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRoleVO sysRoleVO = (SysRoleVO) o;
        return Objects.equals(id, sysRoleVO.id) &&
                Objects.equals(name, sysRoleVO.name) &&
                Objects.equals(description, sysRoleVO.description) &&
                Objects.equals(deleteFlag, sysRoleVO.deleteFlag) &&
                Objects.equals(createTime, sysRoleVO.createTime) &&
                Objects.equals(createUser, sysRoleVO.createUser) &&
                Objects.equals(updateTime, sysRoleVO.updateTime) &&
                Objects.equals(updateUser, sysRoleVO.updateUser) &&
                Objects.equals(auths, sysRoleVO.auths) &&
                Objects.equals(authIds, sysRoleVO.authIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, deleteFlag, createTime, createUser, updateTime, updateUser, auths, authIds);
    }
}
