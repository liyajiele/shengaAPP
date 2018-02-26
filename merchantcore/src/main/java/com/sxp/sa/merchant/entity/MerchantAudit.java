package com.sxp.sa.merchant.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import com.sxp.sa.user.entity.Admin;
import com.sxp.sa.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_m_merchant_audit")
@ApiModel(value = "审核结果")
@Setter
@Getter
public class MerchantAudit extends BaseEntity{


    @ApiModelProperty("提交审核的店铺")
    private Merchant merchant;

    @ApiModelProperty("审核管理员")
    private Admin admin;

    @ApiModelProperty("提交审核的用户")
    private User user;

    @ApiModelProperty("审核状态 0待审核 1.通过 2.失败")
    private Integer auditStatus;

    @ApiModelProperty("失败原因")
    private String auditReason;



    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Merchant.class)
    @JoinColumn(name = "merchantId")
    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Admin.class)
    @JoinColumn(name = "adminId")
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
