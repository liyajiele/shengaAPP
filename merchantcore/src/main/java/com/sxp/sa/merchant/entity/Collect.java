package com.sxp.sa.merchant.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import com.sxp.sa.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_m_collect")
@ApiModel(value = "店铺收藏记录表")
@Setter
@Getter
public class Collect extends BaseEntity{

    @ApiModelProperty("收藏者id")
    private User user;

    @ApiModelProperty("商店id")
    private Merchant merchant;

    @ApiModelProperty("收藏状态 1收藏,0取消收藏")
    private Integer collectStatus;

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
}
