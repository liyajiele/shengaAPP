package com.sxp.sa.user.vo;

import com.sxp.sa.basic.entity.BaseVo;
import com.sxp.sa.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackVo extends BaseVo{

    @ApiModelProperty("反馈者")
    private UserVo user;

    @ApiModelProperty("反馈类型")
    private Integer type;

    @ApiModelProperty("反馈描述")
    private String typeDesc;

    @ApiModelProperty("反馈内容")
    private  String content;

    @ApiModelProperty("反馈图片地址 []")
    private String images;
}
