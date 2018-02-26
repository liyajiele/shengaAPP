package com.sxp.sa.user.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by sxp
 * on 2016/12/28.
 */
@Entity
@Table(name = "tb_u_url_filter")
@ApiModel(value = "url权限定义")
public class UrlFilter extends BaseEntity {

    @ApiModelProperty(value = "url名称,描述")
    private String name;

    @ApiModelProperty(value = "url地址")
    private String url;

    @ApiModelProperty(value = "所需要的角色，可省略 ,分割")
    private String roles;

    @ApiModelProperty(value = "所需要的权限，可省略 ,分割")
    private String permissions;


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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
