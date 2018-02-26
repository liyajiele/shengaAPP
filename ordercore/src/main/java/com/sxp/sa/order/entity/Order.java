package com.sxp.sa.order.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import com.sxp.sa.merchant.entity.Merchant;
import com.sxp.sa.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_o_order")
@ApiModel(value = "订单定义表")
@Setter
@Getter
public class Order extends BaseEntity{

    @ApiModelProperty("所属用户")
    private User user;

    @ApiModelProperty("关联商家")
    private Merchant merchant;

    @ApiModelProperty("订单号")
    private String orderNum;

    @ApiModelProperty("交易状态 Const.TradeStatus")
    private Integer tradeStatus;

    @ApiModelProperty("总金额")
    private Double totalAmount;

    @ApiModelProperty("参与返利的金额")
    private Double rebateAmount;

    @ApiModelProperty("实收金额")
    private Double receiptAmount;

    @ApiModelProperty("余额支付额度")
    private Double balanceAmount;

    @ApiModelProperty("订单描述")
    private String subject;

    @ApiModelProperty("订单内容")
    private String orderContent;

    @ApiModelProperty("订单创建时间")
    private Long gmtCreate;

    @ApiModelProperty("订单支付时间")
    private Long gmtPayment;

    @ApiModelProperty("订单结束时间")
    private Long gmtClose;

    @ApiModelProperty("支付类型 Const.PayType")
    private String payType;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("返利金额")
    private Double rebate;

    @ApiModelProperty("下单时ip地址")
    private String ip;

    @ApiModelProperty("经度")
    private String latitude;
    @ApiModelProperty("纬度")
    private String longitude;

    @ApiModelProperty("评价内容")
    private String retaCont;

    @ApiModelProperty("评价星级")
    private Double retaStra;

    @ApiModelProperty("1.显示 ,2 匿名评论")
    private Integer retaType;


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Merchant.class)
    @JoinColumn(name = "merchantId")
    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @Lob
    @Column(columnDefinition = "TEXT")
    public String getRetaCont() {
        return retaCont;
    }

    public void setRetaCont(String retaCont) {
        this.retaCont = retaCont;
    }
}
