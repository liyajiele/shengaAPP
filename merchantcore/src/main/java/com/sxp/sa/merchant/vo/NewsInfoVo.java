package com.sxp.sa.merchant.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class NewsInfoVo extends BaseVo{
    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("类型 1.红包 ,2返利, 3兑换")
    private Integer newsType;

    @ApiModelProperty("类型描述")
    private String newsTypeDesc;

    @ApiModelProperty("颜色")
    private String color;
}
