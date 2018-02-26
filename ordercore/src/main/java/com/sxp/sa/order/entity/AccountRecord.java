package com.sxp.sa.order.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import com.sxp.sa.merchant.entity.Merchant;
import com.sxp.sa.user.entity.Draw;
import com.sxp.sa.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_o_account_record")
@ApiModel(value = "用户账户记录")
@Setter
@Getter
public class AccountRecord extends BaseEntity{

    @ApiModelProperty("所属用户")
    private User user;

    @ApiModelProperty("关联商户")
    private Merchant merchant;

    @ApiModelProperty("关联的订单")
    private Order order;

    @ApiModelProperty("关联粉丝")
    private User fans;

    @ApiModelProperty("关联提现记录id")
    private Draw drawRecord;

    @ApiModelProperty("改变金额")
    private Double amount;

    @ApiModelProperty("1+ 2-")
    private Integer amountType;

    @ApiModelProperty("记录类型 1.购物返利 2.粉丝消费返利 3.红包 4.消费 5.提现 6.平台奖励 ")
    private Integer recordType;


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

    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Order.class)
    @JoinColumn(name = "orderId")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "fansId")
    public User getFans() {
        return fans;
    }

    public void setFans(User fans) {
        this.fans = fans;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Draw.class)
    @JoinColumn(name = "drawRecordId")
    public Draw getDrawRecord() {
        return drawRecord;
    }

    public void setDrawRecord(Draw drawRecord) {
        this.drawRecord = drawRecord;
    }
}
