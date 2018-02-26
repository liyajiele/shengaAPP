package com.sxp.sa.user.entity;

import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by miss on 2017/2/16.
 */
@Entity @Getter@Setter
@Table(name = "tb_u_third_info")
@ApiModel(value = "三方账号信息")
public class ThirdInfo extends BaseEntity {

    @ApiModelProperty(value = "所属用户")
    private User user;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "三方头像")
    private String avatar;

    @ApiModelProperty(value = "wx:groupId")
    private Integer groupId;

    @ApiModelProperty(value = "三方类型 Const.ThirdType 1 微信公众号 2.微信开放平台")
    private String thirdType;

    @ApiModelProperty(value = "三方openId")
    private String openId;

    @ApiModelProperty(value = "微信unionId")
    private String unionId;

    @ApiModelProperty(value = "三方名称 WxConst 中的name")
    private String thirdName;

    @ApiModelProperty(value = "wx:是否还关注公众号")
    private Integer subscribe;



    public ThirdInfo(){}

    public ThirdInfo(User user,String nickname,String avatar,Integer groupId,String thirdType,String openId,String unionId,String thirdName,Integer subscribe){
        this.user = user;
        this.nickname = nickname;
        this.avatar = avatar;
        this.groupId = groupId;
        this.thirdType = thirdType;
        this.openId = openId;
        this.unionId = unionId;
        this.thirdName = thirdName;
        this.subscribe = subscribe;
    }





    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    @Lob
//    @Column(name=" nickname", columnDefinition="MEDIUMBLOB" )
//    public byte[] getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(byte[] nickname) {
//        this.nickname = nickname;
//    }

}
