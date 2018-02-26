package com.sxp.sa.user.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_u_admin_role")
@ApiModel(value = "管理员-角色定义表")
@Getter
@Setter
public class AdminRole extends BaseEntity{

    @ApiModelProperty("管理员")
    private Admin admin;

    @ApiModelProperty("角色")
    private Role role;


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Admin.class)
    @JoinColumn(name = "adminId")
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin adminId) {
        this.admin = adminId;
    }


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Role.class)
    @JoinColumn(name = "roleId")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
