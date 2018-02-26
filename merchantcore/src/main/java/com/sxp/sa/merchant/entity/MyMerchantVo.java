package com.sxp.sa.merchant.entity;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MyMerchantVo extends BaseVo{

    @ApiModelProperty(value = "所属用户", notes = "所属用户")
    private Long uid;

    @ApiModelProperty(value = "店名", notes = "店名")
    private String nickname;

    @ApiModelProperty(value = "商铺标签  干净个p|都是骗子")
    private String tags;

    @ApiModelProperty(value = "星级", notes = "星级")
    private String stars;

    @ApiModelProperty(value = "人均消费/元", notes = "人均消费/元")
    private Integer consumerption;

    @ApiModelProperty(value = "营业时间 9:00~21:00", notes = "营业时间 9:00~21:00")
    private String shopHours;

    @ApiModelProperty(value = "地址", notes = "地址")
    private String addr;

    @ApiModelProperty(value = "电话", notes = "电话")
    private String mobile;

    @ApiModelProperty(value = "评论数", notes = "评论数")
    private Integer commentNum;

    @ApiModelProperty(value = "主图", notes = "主图")
    private String mainImage;

    @ApiModelProperty(value = "所有图片", notes = "所有图片")
    private String images;

    @ApiModelProperty(value = "所在经度", notes = "所在经度")
    private String longitude;

    @ApiModelProperty(value = "所在维度", notes = "所在维度")
    private String latitude;



    @ApiModelProperty("商家让利百分比,如0.23")
    private Double profits;

    @ApiModelProperty("1全反 2.部分返利")
    private Integer rebateType;


    @ApiModelProperty("所在大区id")
    private Integer areaId;

    @ApiModelProperty("所在省份id")
    private Integer provinceId;

    @ApiModelProperty("所在城市id")
    private Integer cityId;

    @ApiModelProperty("所在区域id")
    private Integer district;

    @ApiModelProperty("经营者姓名")
    private String ownerRealName;

    @ApiModelProperty("经营者身份证")
    private String ownerIdcard;

    @ApiModelProperty("营业执照类型 1.企业法人,2个体工商")
    private Integer licenceType;

    @ApiModelProperty("营业执照 1.已经办理, 2暂无法提供")
    private Integer licenceStatus;

    @ApiModelProperty("执照照片地址")
    private String licenceImage;

    @ApiModelProperty("当日营业额")
    private Double tumover;

    @ApiModelProperty("当日订单数")
    private Integer orderNum;
}
