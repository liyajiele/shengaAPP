package com.sxp.sa.user.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by miss on 2017/2/15.
 */

@Entity
@Table(name = "tb_o_draw")
@ApiModel(value="提现定义表")
@Getter@Setter
public class Draw extends BaseEntity {

    @ApiModelProperty(value = "提现用户")
    private User user;

    @ApiModelProperty(value = "提现金额")
    private Double drawNum;

    @ApiModelProperty(value = "提现订单号")
    private String drawOrderNum;

    @ApiModelProperty(value = "处理时间")
    private String gmtDealTime;

    @ApiModelProperty(value = "提现描述")
    private String descr;

    @ApiModelProperty(value = "提现状态 Const.DrawStatus")
    private String drawStatus;

    @ApiModelProperty(value="管理员拒绝该提现描述")
    private String rejectDescr;

    @ApiModelProperty(value="提款方式")
    private DrawChannel drawChannel;


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = DrawChannel.class)
    @JoinColumn(name = "DrawChannelId")
    public DrawChannel getDrawChannel() {
        return drawChannel;
    }

    public void setDrawChannel(DrawChannel drawChannel) {
        this.drawChannel = drawChannel;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
