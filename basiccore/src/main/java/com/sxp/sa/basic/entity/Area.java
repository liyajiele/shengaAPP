package com.sxp.sa.basic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name="tb_cms_area")
@ApiModel("大区")
public class Area implements Serializable{
	

	
	/**
	 * ID，非自增
	 */
	@ApiModelProperty(value="主键，自增")
	private Integer tId;
	
	/**
	 * 名称
	 */
	@ApiModelProperty(value="大区名称")
	private String name;
	

	public Area() {
	}

	@Id
	@Column(name="id")
	public Integer gettId() {
		return tId;
	}


	public void settId(Integer tId) {
		this.tId = tId;
	}

	@Column( nullable=false, length=50)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


}
