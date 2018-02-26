package com.sxp.sa.merchant.vo;

import com.sxp.sa.basic.entity.BaseVo;
import com.sxp.sa.merchant.entity.Merchant;
import com.sxp.sa.user.vo.UserVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MerchantAuditVo extends BaseVo{

    @ApiModelProperty("提交审核的店铺")
    private Merchant merchant;

    @ApiModelProperty("提交审核的用户")
    private UserVo user;

    @ApiModelProperty("审核状态 1待审核 2.通过 3.失败")
    private Integer auditStatus;

    @ApiModelProperty("失败原因")
    private String auditReason;
}
