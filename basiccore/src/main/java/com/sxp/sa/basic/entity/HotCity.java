package com.sxp.sa.basic.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name="tb_cms_hotcity")
@ApiModel("热门城市")
public class HotCity extends BaseEntity {
	
	@ApiModelProperty(value="记录类型 1.热门城市 , 2城市列表")
	private String type;
	@ApiModelProperty(value="城市")
	private City city;

	
	@Column(length = 200)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name="cityId")
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}

	
	
}
