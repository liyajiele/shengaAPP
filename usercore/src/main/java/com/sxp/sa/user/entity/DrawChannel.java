package com.sxp.sa.user.entity;

import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_u_draw_channel")
@ApiModel(value = "提现渠道")
@Getter@Setter
public class DrawChannel extends BaseEntity {

    @ApiModelProperty(value = "所属用户")
    private User user;

    @ApiModelProperty(value = "提现渠道类型 Const.DrawChannelType")
    private String channelType;

    @ApiModelProperty(value = "联系人姓名")
    private String concatUser;

    @ApiModelProperty(value = "联系人电话")
    private String concatMobile;

    @ApiModelProperty(value = "支付宝账号")
    private String aliPay;

    @ApiModelProperty(value = "卡号")
    private String cardNum;

    @ApiModelProperty(value = "银行名")
    private String bankName;

    @ApiModelProperty(value = "支行名")
    private String bankZh;

    @ApiModelProperty(value = "开卡预留手机号")
    private String cardMobile;

    @ApiModelProperty(value = "开卡人姓名")
    private String cardOwner;

    @ApiModelProperty(value = "开卡人身份证")
    private String cardOwnerID;


    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}