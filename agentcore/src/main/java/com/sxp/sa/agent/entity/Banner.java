package com.sxp.sa.agent.entity;

import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 
 * @ClassName: CommentType
 * @Description: 字典-评分设置定义表
 *
 */
@Entity
@Table(name = "tb_m_banner")
@ApiModel(value = "广告")
public class Banner extends BaseEntity {

	@ApiModelProperty("代理商id")
	private Long agentId;

	@ApiModelProperty("区域id")
	private Integer districtId;

	@ApiModelProperty(value = "图片关联链接地址")
	private String url;
	@ApiModelProperty(value = "图片地址")
	private String image;
	@ApiModelProperty(value = "广告详情")
	private String content;

	@ApiModelProperty(value = "轮播时间")
	private Long carouselTime ;


	@Column(nullable = true, length = 255)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(nullable = true, length = 200)
	public String getImage() {
		return image;
	}
	public void setImage(String icon) {
		this.image = icon;
	}
	@Column(name="content",columnDefinition="MEDIUMTEXT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Long getCarouselTime() {
		return carouselTime;
	}

	public void setCarouselTime(Long carouselTime) {
		this.carouselTime = carouselTime;
	}


    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }
}
