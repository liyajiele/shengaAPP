package com.sxp.sa.merchant.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CommonVo extends BaseVo{

    @ApiModelProperty("评论者头像")
    private String avatar;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("评分")
    private String stars;

    @ApiModelProperty("评论内容")
    private String retaCont;
}
