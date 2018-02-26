package com.sxp.sa.merchant.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_m_tags")
@ApiModel(value = "店铺标签")
@Setter
@Getter
public class Tags extends BaseEntity{

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("标签图片")
    private String tagImage;

    @ApiModelProperty("标签背景色字符串")
    private String tagColor;

}
