package com.sxp.sa.basic.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/2/24.
 */
public class BaseVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("创建时间")
    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
