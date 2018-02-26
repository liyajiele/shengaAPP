package com.sxp.sa.order.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_o_duiba")
@ApiModel(value = "兑吧订单")
@Setter
@Getter
public class DuibaOrder extends BaseEntity{

    @ApiModelProperty("用户id")
    private Long uid;

    @ApiModelProperty("本次扣除的积分")
    private Integer credits;

    @ApiModelProperty("自有商品商品编码(非必须字段)")
    private String itemCode;

    @ApiModelProperty("接口appKey，应用的唯一标识")
    private String appKey;

    @ApiModelProperty("时间戳")
    private String timestamp;

    @ApiModelProperty("本次积分消耗的描述(带中文，请用utf-8进行url解码")
    private String description;

    private String orderNum;

    @ApiModelProperty("兑换类型：alipay(支付宝), qb(Q币), coupon(优惠券), object(实物), phonebill(话费), phoneflow(流量), virtual(虚拟商品), turntable(大转盘), singleLottery(单品抽奖)，hdtoolLottery(活动抽奖),htool(新活动抽奖),manualLottery(手动开奖),gameLottery(游戏),ngameLottery(新游戏),questionLottery(答题),quizzLottery(测试题),guessLottery(竞猜) 所有类型不区分大小写")
    private String type;


    @ApiModelProperty("兑换商品的市场价值，单位是分，请自行转换单位")
    private Integer facePrice;

    @ApiModelProperty("此次兑换实际扣除开发者账户费用，单位为分")
    private Integer actualPrice;

    @ApiModelProperty("用户ip，不保证获取到")
    private String ip;

    @ApiModelProperty("是否需要审核(如需在自身系统进行审核处理，请记录下此信息)")
    private String waitAudit;

    @ApiModelProperty("详情参数，不同的类型，返回不同的内容，中间用英文冒号分隔。(支付宝类型带中文，请用utf-8进行解码) 实物商品：返回收货信息(姓名:手机号:省份:城市:区域:详细地址)、支付宝：返回账号信息(支付宝账号:实名)、话费：返回手机号、QB：返回QQ号")
    private String params;

    @ApiModelProperty("")
    private String sign;

    @ApiModelProperty("0失败 1成功 2.等待结果")
    private String result;


    private String bizOrder;


}
