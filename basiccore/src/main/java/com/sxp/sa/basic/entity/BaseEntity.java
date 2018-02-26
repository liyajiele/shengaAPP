package com.sxp.sa.basic.entity;


import com.sxp.sa.basic.constant.Const;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by miss on 2015/10/19.
 */

@MappedSuperclass
public class BaseEntity implements Serializable {

    @ApiModelProperty(value = "用户ID")
    protected Long id;

    @ApiModelProperty(value = "状态: -1 锁定状态 , 0 删除/无效状态,  1 正常/有效状态,  2删除状态")
    protected Integer status;

    @ApiModelProperty(value = "版本,Hibernate 维护")
    protected Integer sysversion;

    @ApiModelProperty(value = "创建者")
    protected Long createUser;

    @ApiModelProperty(value = "创建时间")
    protected Long createTime;

    @ApiModelProperty(value = "修改者")
    protected Long modifyUser;

    @ApiModelProperty(value = "修改时间")
    protected Long modifyTime;

    @ApiModelProperty(value = "修改描述")
    protected String modifyDesc;

    @ApiModelProperty(value = "优先级")
    protected Integer priority;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * 为确保赋值增加默认值1:正常
     */
    @Column(nullable = false, columnDefinition = "int default 1")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Version
    public Integer getSysversion() {
        return sysversion;
    }

    public void setSysversion(Integer sysversion) {
        this.sysversion = sysversion;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyDesc() {
        return modifyDesc;
    }

    public void setModifyDesc(String modifyDesc) {
        this.modifyDesc = modifyDesc;
    }

    public Integer getPriority() {
        return priority;
    }

    @Column(nullable = false ,columnDefinition = "int default 0")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @PrePersist
    public void setInsertBefore(){
        this.createTime = (new Date()).getTime();
        if(status == null){
            this.status = Const.Status.valid;
        }
        if(priority == null){
            this.priority = 0;
        }
        this.modifyTime = (new Date()).getTime();
    }


    /**
     * 数据修改前的操作
     */
    @PreUpdate
    public void setUpdateBefore() {
        this.modifyTime = (new Date()).getTime();
    }


    public static String getLongToString(Long longTime) {
        if (longTime == null) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(longTime);
        return sf.format(date);
    }



}
