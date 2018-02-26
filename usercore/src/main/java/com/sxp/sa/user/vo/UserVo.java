package com.sxp.sa.user.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserVo extends BaseVo{

    @ApiModelProperty(value = "用户名", notes = "用户名")
    private String username;

    @ApiModelProperty(value = "邀请码", notes = "邀请码")
    private String invateCode;

    @ApiModelProperty(value = "二维码地址", notes = "二维码地址")
    private String qrcode;

    @ApiModelProperty(value = "昵称")
    private String nickname;


    @ApiModelProperty(value = "头像,地址", notes = "头像,地址")
    private String avatar;

    @ApiModelProperty(value = "用户等级 Const.UserLevel", notes = "用户等级 Const.UserLevel")
    private Integer level;

    @ApiModelProperty(value = "性别 0是女  1是男", notes = "性别")
    private Integer gender;


    @ApiModelProperty(value = "生日", notes = "生日")
    private String birth;

    @ApiModelProperty(value = "是否是商家 0否 1是", notes = "是否是商家 0否 1是")
    private Integer isMerchant;

    @ApiModelProperty(value = "是否是代理商 0否 1是", notes = "是否是代理商 0否 1是")
    private Integer isAgent;

    @ApiModelProperty("token")
    private String token;
}
