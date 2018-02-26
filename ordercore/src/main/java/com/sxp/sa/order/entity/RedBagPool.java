package com.sxp.sa.order.entity;

import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_o_redbag_pool")
@ApiModel(value = "红包池")
@Setter
@Getter
public class RedBagPool extends BaseEntity{

    @ApiModelProperty("红包池中余额")
    private Double balance;

    @ApiModelProperty("最大金额")
    private Double maxSingleBag;

    @ApiModelProperty("最小金额")
    private Double minSingleBag;
}
