package com.sxp.sa.user.vo;


import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AccountVo extends BaseVo{

    @ApiModelProperty("账户余额")
    private Double balance;

    @ApiModelProperty("红包余额")
    private Double redBalance;

    @ApiModelProperty("返利余额")
    private Double retateBalance;

    @ApiModelProperty("冻结余额")
    private Double frozenBalance;

    @ApiModelProperty("冻结红包金额")
    private Double frozenRedBlc;

    @ApiModelProperty("积分")
    private Integer intergral;

    @ApiModelProperty("当日可提现次数")
    private Integer canDrawNum;

    @ApiModelProperty("当日抢红包次数")
    private Integer canGetRebNum;

    @ApiModelProperty("最低提现金额")
    private Double drawMinMoney;

    @ApiModelProperty("到账时间 /小时")
    private Integer paymentDate;
}
