package com.sxp.sa.order.service;

/**
 * Created by Administrator on 2017/2/20.
 */
public interface WxService {

    /**
     * 查询企业打款结果
     * @param weixinGZH
     * @param orderNum
     * @return
     */
    String queryBizPay(String weixinGZH, String orderNum);
    /**
     * 企业付款
     * @param weixinGZH
     * @param orderNum
     * @param openId
     * @param amount
     * @param desc
     * @param ip
     * @return
     */
    String bizPay(String weixinGZH, String orderNum, String openId, Double amount, String desc, String ip);

    /**
     * 发红包
     * @param weixinGZH
     * @param sendName
     * @param orderNum
     * @param openId
     * @param totalAmount
     * @param wishing
     * @param ip
     * @param actName
     * @param remarks
     * @return
     */
    String sendredpack(String weixinGZH, String sendName, String orderNum, String openId, Double totalAmount, String wishing, String ip, String actName, String remarks);

    /**
     * 微信支付统一下单
     * @param weixinGZH
     * @param openid
     * @param body
     * @param orderNum
     * @param fee
     * @param ip
     * @return
     */
    String wxPayUnifiedOrder(String weixinGZH, String openid,
                             String body, String orderNum, Integer fee, String ip) ;



    /**
     * 微信扫码支付统一下单 返回二维码 URL
     */
    String wxScanUnifiedOrder(String weixinGZH, String body, String productId, String orderNum, Integer fee, String ip);

}
