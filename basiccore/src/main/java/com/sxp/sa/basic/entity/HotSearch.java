package com.sxp.sa.basic.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="tb_cms_hot_search")
@ApiModel("热门搜索")
@Getter
@Setter
public class HotSearch extends BaseEntity {

    @ApiModelProperty(value="搜索内容")
    private String content;
    @ApiModelProperty(value="城市")
    private City city;
    @ApiModelProperty("所在区县")
    private District district;

    @ApiModelProperty("搜索次数")
    private Integer searchTimes;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true,targetEntity = City.class)
    @JoinColumn(name="cityId")
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }



    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true,targetEntity = District.class)
    @JoinColumn(name="districtId")
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
