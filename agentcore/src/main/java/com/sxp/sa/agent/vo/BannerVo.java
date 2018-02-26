package com.sxp.sa.agent.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerVo extends BaseVo{


    @ApiModelProperty(value = "图片关联地址")
    private String url;

    @ApiModelProperty(value = "图片地址")
    private String image;

    @ApiModelProperty(value = "广告详情")
    private String content;

    @ApiModelProperty(value = "轮播时间")
    private Long carouselTime ;

    @ApiModelProperty("代理商id")
    private Long agentId;

    @ApiModelProperty("区域id")
    private Integer districtId;
}
