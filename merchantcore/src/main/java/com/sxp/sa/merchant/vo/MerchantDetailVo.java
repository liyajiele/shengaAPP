package com.sxp.sa.merchant.vo;


import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class MerchantDetailVo extends BaseVo{


    @ApiModelProperty(value = "店名", notes = "店名")
    private String nickname;

    @ApiModelProperty(value = "星级", notes = "星级")
    private String stars;

    @ApiModelProperty(value = "地址", notes = "地址")
    private String addr;

    @ApiModelProperty(value = "主图", notes = "主图")
    private String mainImage;

    @ApiModelProperty(value = "所有图片", notes = "所有图片")
    private String images;

    @ApiModelProperty(value = "人均消费/元", notes = "人均消费/元")
    private Integer consumerption;

    @ApiModelProperty(value = "营业时间 9:00~21:00", notes = "营业时间 9:00~21:00")
    private String shopHours;

    @ApiModelProperty(value = "电话", notes = "电话")
    private String mobile;

    @ApiModelProperty(value = "评论数", notes = "评论数")
    private Integer commentNum;

    @ApiModelProperty("返利给用户的百分比,如0.23")
    private Double rebate;


    @ApiModelProperty(value = "所在经度", notes = "所在经度")
    private String longitude;

    @ApiModelProperty(value = "所在维度", notes = "所在维度")
    private String latitude;



    @ApiModelProperty("评论")
    private List<CommonVo> commons ;


    private List<MerchantVo> recommonds;



    //附加
    @ApiModelProperty("是否收藏 0 否,1 是")
    private Integer hasCollect;

    @ApiModelProperty("距离")
    private Double distance;
}
