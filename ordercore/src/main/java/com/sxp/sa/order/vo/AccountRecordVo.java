package com.sxp.sa.order.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AccountRecordVo extends BaseVo{


    @ApiModelProperty("改变金额")
    private Double amount;

    @ApiModelProperty("1+ 2-")
    private Integer amountType;

    @ApiModelProperty("记录类型 1.购物返利 2.粉丝消费返利 3.红包 4.消费 5.提现 6.平台奖励")
    private Integer recordType;
}
