package com.sxp.sa.basic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="tb_cms_province")
@ApiModel("省")
public class Province implements Serializable{
	
	private static final long serialVersionUID = 700235310223571456L;

	
	/**
	 * ID，非自增
	 */
	@ApiModelProperty(value="主键，自动增长")
	private Integer tId;
	
	/**
	 * 名称
	 */
	@ApiModelProperty(value="省名称")
	private String name;
	
	/**
	 * 排序
	 */
	private Integer orderBy;
	
	/**
	 * 运费
	 */
	private Double expressCharge;
	
	/**
	 * 所属大区
	 */
	@ApiModelProperty(value="大区ID")
	private Integer areaId;

	public Province() {
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

	@Column(nullable=true)
	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}


	@Column(nullable=true)
	public Double getExpressCharge() {
		return expressCharge;
	}


	public void setExpressCharge(Double expressCharge) {
		this.expressCharge = expressCharge;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

}
