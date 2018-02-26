package com.sxp.sa.user.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Created by sxp
 * on 2016/12/26.
 */

@Entity
@Table(name = "tb_u_admin")
@ApiModel(value = "管理员定义表")
public class Admin extends BaseEntity {

    @ApiModelProperty(value = "关联用户")
    private User user;


    @ApiModelProperty(value = "描述信息")
    private String description;




    @OneToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }




    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
