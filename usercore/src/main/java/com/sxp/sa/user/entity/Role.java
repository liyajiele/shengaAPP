package com.sxp.sa.user.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by sxp
 * on 2016/12/26.
 */

@Entity
@Table(name = "tb_u_role")
@ApiModel(value = "角色定义表")
public class Role extends BaseEntity {

    @ApiModelProperty(value = "角色名称")
    private String role;

    @ApiModelProperty(value = "角色描述")
    private String description;




    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
