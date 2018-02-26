package com.sxp.sa.agent.vo;

import com.sxp.sa.basic.entity.*;
import com.sxp.sa.user.vo.AccountVo;
import com.sxp.sa.user.vo.AdminVo;
import com.sxp.sa.user.vo.UserVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentInfosVo extends BaseVo{


    @ApiModelProperty("关联用户")
    private UserVo user;

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

    @ApiModelProperty("招商经理")
    private AdminVo zhaoShangManager;

    @ApiModelProperty("客户经理")
    private AdminVo kuhuManager;

    @ApiModelProperty("账户信息")
    private AccountVo account;

    @ApiModelProperty("代理开始时间")
    private Long startTime;

    @ApiModelProperty("代理过期时间")
    private Long endTime;

    @ApiModelProperty("区域内商家数")
    private Integer mctNums;

    @ApiModelProperty("粉丝数")
    private Integer fansNums;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("代理商信息")
    private String agentInfo;
}
