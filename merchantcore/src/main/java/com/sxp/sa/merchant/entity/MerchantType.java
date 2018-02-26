package com.sxp.sa.merchant.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_m_merchant_type")
@ApiModel(value = "店铺分类")
@Setter
@Getter
public class MerchantType extends BaseEntity{

    @ApiModelProperty("分类名称")
    private String typeName;

    @ApiModelProperty("分类图片")
    private String typeImage;
}
