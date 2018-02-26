package com.sxp.sa.order.service;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.merchant.vo.MerchantDetailVo;
import com.sxp.sa.order.vo.EvlOrderVo;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OrderService {

    MerchantDetailVo addCommons(MerchantDetailVo mvo);


    Map<String,Object> getMerchantCommons(Long mid, Pageable pageable)throws BusinessException;

    Pager<EvlOrderVo> getMineOrderToBeEvaluated(Long uid,Pageable pageable)throws BusinessException;

    //评价订单
    EvlOrderVo evalorder(Long uid,Long orderId,Integer retaType,String retaCont,Double retaStar)throws BusinessException;

    Map<String,Object> getMineRebetaOrders(Long uid,Long startTime,Long endTime,Pageable pageable)throws BusinessException;

    Map<String,Object> getMineFinishedOrders(Long uid,Long startTime,Long endTime,Pageable pageable)throws BusinessException;

    Integer getMineOrderToBeEvaluatedNums(Long uid)throws BusinessException;

    /**
     * 下单
     * @param mid
     * @param totalAmount
     * @param rebateAmount
     * @param balanceAmount
     * @return
     * @throws BusinessException
     */
    String pay(Long uid,Long mid,String payType,Double totalAmount,Double rebateAmount,Double balanceAmount,String ip,String longitude,String latitude)throws BusinessException;



    //商户相关
    Map<String,Object> getMineOrders(Long mid,Integer tradeStatus,Long startTime,Long endTime, Pageable pageable)throws BusinessException;






    String duibaRaduceIntergral(Long uid,Integer credits,String appkey,String timestamp,String orderNum,String type,String sign,
                                 String itemCode,String description,Integer facePrice,String waitAudit,String params);



    String duibaRst(String appKey,String timestamp,String orderNum,String sign,String success,String errorMessage,String bizId);


    Boolean payCallBackWork(String outNums,Double totalFee,String payType)throws BusinessException;
}
