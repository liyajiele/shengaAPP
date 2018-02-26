package com.sxp.sa.user.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/2/19.
 */
@Getter@Setter
public class ThirdInfoVo extends BaseVo {



    @ApiModelProperty(value = "所属用户")
    private UserVo user;

    @ApiModelProperty(value = "三方昵称")
    private String nickname;

    @ApiModelProperty(value = "三方头像")
    private String avatar;

    @ApiModelProperty(value = "三方类型 Const.ThirdType")
    private String thirdType;

    @ApiModelProperty(value = "三方openId")
    private String openId;

    @ApiModelProperty(value = "微信unionId")
    private String unionId;

    @ApiModelProperty(value = "三方名称 WxConst 中的name")
    private String thirdName;

    @ApiModelProperty(value = "wx:groupId")
    private Integer groupId;


    @ApiModelProperty(value = "wx:是否还关注公众号")
    private Integer subscribe;

}
