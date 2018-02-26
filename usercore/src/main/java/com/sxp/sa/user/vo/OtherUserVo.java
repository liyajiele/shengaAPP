package com.sxp.sa.user.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class OtherUserVo extends BaseVo{

    @ApiModelProperty(value = "用户名", notes = "用户名")
    private String username;

    @ApiModelProperty(value = "邀请码", notes = "邀请码")
    private String invateCode;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像,地址", notes = "头像,地址")
    private String avatar;
}
