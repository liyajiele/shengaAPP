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
@Table(name = "tb_u_resource")
@ApiModel(value = "资源定义表")
public class Resource extends BaseEntity {

    @ApiModelProperty(value = "父级")
    private Resource parentResource;

    @ApiModelProperty(value = "资源名")
    private String resourceName ;

    @ApiModelProperty(value = "资源字符串")
    private String permission;

    @ApiModelProperty("资源url")
    private String url;


    @OneToOne(cascade = CascadeType.REFRESH,targetEntity = Resource.class)
    @JoinColumn(name = "parentId")
    public Resource getParentResource() {
        return parentResource;
    }

    public void setParentResource(Resource parentResource) {
        this.parentResource = parentResource;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
