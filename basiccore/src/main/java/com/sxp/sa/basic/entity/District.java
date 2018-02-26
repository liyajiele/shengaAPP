package com.sxp.sa.basic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name="tb_cms_district")
@ApiModel("区县")
public class District implements Serializable{
	

	
	/**
	 * ID，非自增
	 */
	@ApiModelProperty(value="主键，自增")
	private Integer tId;
	
	/**
	 * 所属市
	 */
	@ApiModelProperty(value="所属城市")
	private Integer cityId;
	
	/**
	 * 名称
	 */
	@ApiModelProperty(value="区县名称")
	private String name;
	
	/**
	 * 邮政编码
	 */
	private String postCode;
	
	

	public District() {
	}


	@Id
	@Column(name="id")
	public Integer gettId() {
		return tId;
	}


	public void settId(Integer tId) {
		this.tId = tId;
	}

	@Column( nullable=false)
	public Integer getCityId() {
		return cityId;
	}


	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	@Column( nullable=false, length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable=true,length=50)
	public String getPostCode() {
		return postCode;
	}


	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}



}
