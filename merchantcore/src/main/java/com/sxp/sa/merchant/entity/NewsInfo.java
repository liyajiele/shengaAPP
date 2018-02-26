package com.sxp.sa.merchant.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_m_news_info")
@ApiModel(value = "最新信息记录")
@Setter
@Getter
public class NewsInfo extends BaseEntity{

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("类型 1.红包 ,2返利, 3兑换")
    private Integer newsType;

    @ApiModelProperty("类型描述")
    private String newsTypeDesc;

    @ApiModelProperty("颜色")
    private String color;
}
