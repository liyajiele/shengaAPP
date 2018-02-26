package com.sxp.sa.merchant.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_m_merchant_own_type")
@ApiModel(value = "店铺所属分类")
@Setter
@Getter
public class MerchantOwnType extends BaseEntity{

    @ApiModelProperty("店铺")
    private Merchant merchant;

    @ApiModelProperty("所属分类")
    private MerchantType merchantType;


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Merchant.class)
    @JoinColumn(name = "merchantId")
    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = MerchantType.class)
    @JoinColumn(name = "merchantTypeId")
    public MerchantType getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(MerchantType merchantType) {
        this.merchantType = merchantType;
    }
}
