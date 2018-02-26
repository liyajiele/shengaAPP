package com.sxp.sa.user.entity;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by dell on 2017/7/17.
 */

@Entity
@Table(name = "tb_u_account")
@ApiModel(value = "账户定义表")
@Setter
@Getter
public class Account extends BaseEntity{


    @ApiModelProperty("所属用户")
    private User user;


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

    @ApiModelProperty("冻结返利余额")
    private Double frozenRetateBalance;

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


    public Account (){}
    public Account (User user){
        this.user = user;
        this.balance = 0.0;
        this.redBalance = 0.0;
        this.retateBalance = 0.0;
        this.frozenBalance = 0.0;
        this.frozenRedBlc = 0.0;
        this.frozenRetateBalance =0.0;
        this.intergral = 0;
        this.canDrawNum = Const.CAN_DRAW_NUM;
        this.canGetRebNum = Const.CAN_GET_RED_NUM;
        this.drawMinMoney = Const.DRAW_MN_MONEY;
        this.paymentDate = Const.PAYMENT_DATA;
    }





    @OneToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
