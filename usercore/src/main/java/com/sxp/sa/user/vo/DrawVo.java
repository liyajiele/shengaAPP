package com.sxp.sa.user.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public  class DrawVo extends BaseVo {
    @ApiModelProperty(value = "提现用户")
    private UserVo user;

    @ApiModelProperty(value = "提现金额")
    private Double drawNum;

    @ApiModelProperty(value = "提现订单号")
    private String drawOrderNum;

    @ApiModelProperty(value = "处理时间")
    private String gmtDealTime;

    @ApiModelProperty(value = "提现描述")
    private String descr;

    @ApiModelProperty(value = "提现状态 Const.DrawStatus")
    private String drawStatus;

    @ApiModelProperty(value="管理员拒绝该提现描述")
    private String rejectDescr;

    @ApiModelProperty(value="提款方式")
    private DrawChannelVo drawChannel;

}
