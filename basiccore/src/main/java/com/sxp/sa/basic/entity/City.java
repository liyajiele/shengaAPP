package com.sxp.sa.basic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name="tb_cms_city")
@ApiModel(value = "市")
public class City implements Serializable{
	

	
	/**
	 * ID，非自增
	 */
	@ApiModelProperty(value="主键，自增")
	private Integer tId;
	
	/**
	 * 所属省
	 */
	@ApiModelProperty(value="所属省ID")
	private Integer provinceId;
	
	/**
	 * 地区编号
	 */
	@ApiModelProperty(value="地区编号")
	private String areaCode;
	
	/**
	 * 名称
	 */
	@ApiModelProperty(value="城市名称")
	private String name;
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * 排序
	 */
	private Integer orderBy;
	
	@ApiModelProperty(value="城市全拼")
	private String pinyin;
	
	@ApiModelProperty(value="城市首拼")
	private String pinyinCode;
	
	@ApiModelProperty(value="城市首字母")
	private String pinyinArea;

	public City() {
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
	public Integer getProvinceId() {
		return provinceId;
	}


	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	@Column( nullable=false)
	public String getName() {
		return name;
	}
	
	@Column( nullable=true)
	public String getPinyin() {
		return pinyin;
	}


	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Column( nullable=true)
	public String getPinyinCode() {
		return pinyinCode;
	}


	public void setPinyinCode(String pinyinCode) {
		this.pinyinCode = pinyinCode;
	}

	@Column( nullable=true)
	public String getPinyinArea() {
		return pinyinArea;
	}


	public void setPinyinArea(String pinyinArea) {
		this.pinyinArea = pinyinArea;
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

}
