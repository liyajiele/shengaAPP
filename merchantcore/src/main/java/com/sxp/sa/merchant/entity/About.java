package com.sxp.sa.merchant.entity;

import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "tb_b_about")
@ApiModel(value = "关于省啊")
@Setter
@Getter
public class About extends BaseEntity {

    @ApiModelProperty("appVersion")
    private String appVersion;

    @ApiModelProperty("是否强制升级 1是 0否")
    private Integer forceUpdate;

    @ApiModelProperty("更新地址")
    private String updateUrl;

    @ApiModelProperty("更新记录")
    private String updateContent;

    @ApiModelProperty("更新图片")
    private String updateImages;

    @ApiModelProperty("系统类型 1web 2wx 3android 4ios")
    private Integer osType;

    @ApiModelProperty("官网地址")
    private String officailWebsite;

    @ApiModelProperty("官方Email")
    private String officialEmail;

    @ApiModelProperty("官网qq")
    private String officialQQ;

    @ApiModelProperty("官网微信")
    private String officialWx;

    @ApiModelProperty("官网电话")
    private String officialMobile;

    @ApiModelProperty("应用图标")
    private String image;

    @ApiModelProperty("应用名称")
    private String appName;

    @Lob
    @Column(columnDefinition = "TEXT")
    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    @Lob
    @Column(columnDefinition = "TEXT")
    public String getUpdateImages() {
        return updateImages;
    }

    public void setUpdateImages(String updateImages) {
        this.updateImages = updateImages;
    }
}
