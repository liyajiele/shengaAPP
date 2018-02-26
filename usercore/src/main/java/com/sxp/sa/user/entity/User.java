package com.sxp.sa.user.entity;

import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Created by miss on 2015/10/27.
 *
 *  用户model
 */
@Entity
@Table(name = "tb_u_user")
@ApiModel(value = "用户定义表")
@Setter
@Getter
public class User extends BaseEntity {
    @ApiModelProperty(value = "用户名", notes = "用户名")
    private String username;

    @ApiModelProperty(value = "邀请码", notes = "邀请码")
    private String invateCode;

    @ApiModelProperty(value = "二维码地址", notes = "二维码地址")
    private String qrcode;

    @ApiModelProperty(value = "密码", notes = "密码")
    private String password;


    @ApiModelProperty(value = "支付密码", notes = "支付密码")
    private String payPass;


    @ApiModelProperty(value = "用户手机号", notes = "用户手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱", notes = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickname;


    @ApiModelProperty(value = "头像,地址", notes = "头像,地址")
    private String avatar;


    @ApiModelProperty(value = "姓名", notes = "姓名")
    private String realname;

    @ApiModelProperty(value = "用户等级 Const.UserLevel", notes = "用户等级 Const.UserLevel")
    private Integer level;

    @ApiModelProperty(value = "父级1", notes = "父级1")
    private Long parent1;

    @ApiModelProperty(value = "父级2", notes = "父级2")
    private Long parent2;

    @ApiModelProperty(value = "父级3", notes = "父级3")
    private Long parent3;


    @ApiModelProperty(value = "性别 0是女  1是男", notes = "性别")
    private Integer gender;


    @ApiModelProperty(value = "生日", notes = "生日")
    private String birth;


    @ApiModelProperty(value = "身份证号", notes = "身份证号")
    private String idnum;


    @ApiModelProperty(value = "所在经度", notes = "所在经度")
    private String longitude;

    @ApiModelProperty(value = "所在维度", notes = "所在维度")
    private String latitude;

    @ApiModelProperty("IP地址")
    private String ip;

    @ApiModelProperty(value = "所属商家", notes = "所属商家")
    private Long merchantId;

    @ApiModelProperty("所属代理")
    private Long    agentId;

    @ApiModelProperty("二维码过期时间")
    private Long qrcodeTimeout;

    @ApiModelProperty("qrcode Ticket")
    private String  qrcodeTicket;


    //商家相关

    @ApiModelProperty(value = "是否是商家 0否 1是", notes = "是否是商家 0否 1是")
    private Integer isMerchant;

    @ApiModelProperty("返利merchantId")
    private Long myMerchant;

    @ApiModelProperty(value = "身份证正面图片", notes = "身份证正面图片")
    private String idcardImage1;

    @ApiModelProperty(value = "身份证反面面图片", notes = "身份证反面面图片")
    private String idcardImage2;


    //
    @ApiModelProperty(value = "是否是代理商 0否 1是", notes = "是否是代理商 0否 1是")
    private Integer isAgent;


}
