package com.sxp.sa.user.entity;


import com.sxp.sa.basic.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_u_search_record")
@ApiModel(value = "搜索记录")
@Setter
@Getter
public class SearchRecord extends BaseEntity{

    @ApiModelProperty("搜索用户")
    private User user ;

    @ApiModelProperty("搜索内容")
    private String content;

    @ApiModelProperty(value = "所在经度", notes = "所在经度")
    private String longitude;

    @ApiModelProperty(value = "所在维度", notes = "所在维度")
    private String latitude;

    @ApiModelProperty("所在大区")
    private Area area;

    @ApiModelProperty("所在省")
    private Province province;

    @ApiModelProperty("所在城市")
    private City city;

    @ApiModelProperty("所在区域")
    private District district;

    @ApiModelProperty("搜索次数")
    private Integer searchTimes;


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Area.class)
    @JoinColumn(name = "areaId")
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Province.class)
    @JoinColumn(name = "provinceId")
    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = City.class)
    @JoinColumn(name = "cityId")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = District.class)
    @JoinColumn(name = "districtId")
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
