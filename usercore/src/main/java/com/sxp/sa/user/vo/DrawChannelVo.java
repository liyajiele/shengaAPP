package com.sxp.sa.user.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class DrawChannelVo extends BaseVo {


    @ApiModelProperty(value = "所属用户")
    private UserVo user;

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


    public UserVo getUserVo() {
        return user;
    }

    public void setUserVo(UserVo userVo) {
        this.user = userVo;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getConcatUser() {
        return concatUser;
    }

    public void setConcatUser(String concatUser) {
        this.concatUser = concatUser;
    }

    public String getConcatMobile() {
        return concatMobile;
    }

    public void setConcatMobile(String concatMobile) {
        this.concatMobile = concatMobile;
    }

    public String getAliPay() {
        return aliPay;
    }

    public void setAliPay(String aliPay) {
        this.aliPay = aliPay;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankZh() {
        return bankZh;
    }

    public void setBankZh(String bankZh) {
        this.bankZh = bankZh;
    }

    public String getCardMobile() {
        return cardMobile;
    }

    public void setCardMobile(String cardMobile) {
        this.cardMobile = cardMobile;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getCardOwnerID() {
        return cardOwnerID;
    }

    public void setCardOwnerID(String cardOwnerID) {
        this.cardOwnerID = cardOwnerID;
    }
}
