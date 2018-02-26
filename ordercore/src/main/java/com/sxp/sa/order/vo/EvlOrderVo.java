package com.sxp.sa.order.vo;

import com.sxp.sa.basic.entity.BaseVo;
import com.sxp.sa.merchant.vo.MerchantVo;
import com.sxp.sa.user.vo.OtherUserVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class EvlOrderVo extends BaseVo{

    @ApiModelProperty("用户")
    private OtherUserVo user;

    @ApiModelProperty("关联商家")
    private MerchantVo merchant;

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

    @ApiModelProperty("返利金额")
    private Double rebate;
}
