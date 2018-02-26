package com.sxp.sa.agent.entity;


import com.sxp.sa.basic.entity.*;
import com.sxp.sa.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_a_agent_info")
@ApiModel(value = "代理商")
@Setter
@Getter
public class AgentInfos extends BaseEntity{

    @ApiModelProperty("关联用户")
    private User user;

    @ApiModelProperty("1总代理 2.area代理 ,3province 4city 5 district")
    private Integer agentLevel;

    @ApiModelProperty("所在大区")
    private Area area;

    @ApiModelProperty("所在省")
    private Province province;

    @ApiModelProperty("所在城市")
    private City city;

    @ApiModelProperty("所在区域")
    private District district;

    @ApiModelProperty("招商经理 adminId")
    private Long zhaoShangAid;

    @ApiModelProperty("客户经理 adminId")
    private Long kehuAid;

    @ApiModelProperty("代理开始时间")
    private Long startTime;

    @ApiModelProperty("代理过期时间")
    private Long endTime;


    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("代理商信息")
    private String agentInfo;

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


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
