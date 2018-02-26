package com.sxp.sa.order.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
//红包账户记录
public class RBAccountRecordVo  extends BaseVo{

    @ApiModelProperty("改变金额")
    private Double amount;
}
