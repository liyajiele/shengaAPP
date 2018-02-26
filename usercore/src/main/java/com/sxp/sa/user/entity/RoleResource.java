package com.sxp.sa.user.entity;

import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_u_role_resource")
@ApiModel(value = "角色-资源定义表")
@Getter
@Setter
public class RoleResource extends BaseEntity{

    @ApiModelProperty("角色")
    private Role role;

    @ApiModelProperty("资源id")
    private Resource resource;



    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Role.class)
    @JoinColumn(name = "roleId")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Resource.class)
    @JoinColumn(name = "resourceId")
    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
