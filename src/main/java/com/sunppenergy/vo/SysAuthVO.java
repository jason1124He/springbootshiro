package com.sunppenergy.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author hej
 * @since 2019-07-17
 */
@TableName("sys_auth")
public class SysAuthVO implements Serializable {


    private static final long serialVersionUID = -3488454319883935735L;

    private Integer id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 权限字段
     */
    private String url;

    /**
     * 删除标志位（0：未删除  1：已删除）
     */
    private Integer deleteFlag;

    private Integer pageSize;

    private Integer pageIndex;

    private Date createTime;

    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public String toString() {
        return "SysAuthEntity{" +
                "id=" + id +
                ", name=" + name +
                ", url=" + url +
                ", deleteFlag=" + deleteFlag +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysAuthVO sysAuthVO = (SysAuthVO) o;
        return Objects.equals(id, sysAuthVO.id) &&
                Objects.equals(name, sysAuthVO.name) &&
                Objects.equals(url, sysAuthVO.url) &&
                Objects.equals(deleteFlag, sysAuthVO.deleteFlag) &&
                Objects.equals(pageSize, sysAuthVO.pageSize) &&
                Objects.equals(pageIndex, sysAuthVO.pageIndex) &&
                Objects.equals(createTime, sysAuthVO.createTime) &&
                Objects.equals(createUser, sysAuthVO.createUser) &&
                Objects.equals(updateTime, sysAuthVO.updateTime) &&
                Objects.equals(updateUser, sysAuthVO.updateUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, deleteFlag, pageSize, pageIndex, createTime, createUser, updateTime, updateUser);
    }
}
