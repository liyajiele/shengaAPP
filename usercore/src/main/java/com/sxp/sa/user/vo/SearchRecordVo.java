package com.sxp.sa.user.vo;

import com.sxp.sa.basic.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchRecordVo extends BaseVo{

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

}
