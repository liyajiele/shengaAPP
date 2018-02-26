package com.sxp.sa.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.sxp.sa.basic.constant.WxConst;
import com.sxp.sa.basic.entity.BizPayParam;
import com.sxp.sa.basic.entity.QueryBizPayParam;
import com.sxp.sa.basic.entity.Redpack;
import com.sxp.sa.basic.entity.UnifiedOrder;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.basic.utils.wxpay.HttpsRequest;
import com.sxp.sa.basic.utils.wxpay.RandomStringGenerator;
import com.sxp.sa.basic.utils.wxpay.Signature;
import com.sxp.sa.basic.utils.wxpay.XMLParser;
import com.sxp.sa.order.service.WxService;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20.
 */
@Service
public class WxServiceImpl extends BaseService implements WxService {

    /**
     * 查询企业打款结果
     * @param weixinGZH
     * @param orderNum
     * @return
     */
    @Override
    public String queryBizPay(String weixinGZH, String orderNum) {
        String appid ="";
        String secret ="";
        String mchid ="";
        String key ="";
        String wxPayNoticeUrl ="";

        switch (weixinGZH){
            case "user":{
                appid = WxConst.UserWeixinAccount.APPID;
                secret = WxConst.UserWeixinAccount.SECRET;
                mchid = WxConst.UserWeixinAccount.MCHID;
                key = WxConst.UserWeixinAccount.KEY;
                wxPayNoticeUrl = WxConst.UserWeixinAccount.NOTICE_URL;
                break;
            }
        }


        QueryBizPayParam queryBizPayParam = new QueryBizPayParam();
        queryBizPayParam.setNonce_str(Util.getRandomNumber(32));
        queryBizPayParam.setPartner_trade_no(orderNum);
        queryBizPayParam.setMch_id(mchid);
        queryBizPayParam.setAppid(appid);


        HttpsRequest request = null;
        try {
            String sign = Signature.getSign(queryBizPayParam,key);
            //sign
            queryBizPayParam.setSign(sign);

            request = new HttpsRequest(weixinGZH);
            String orderRst = request.sendPost(WxConst.URLS.WX_QUERY_BIZ_PAY,queryBizPayParam,weixinGZH);
            Map<String,Object> orderRstMap = XMLParser.getMapFromXML(orderRst);

            return JSON.toJSONString(orderRstMap);
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

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
    @Override
    public String bizPay(String weixinGZH, String orderNum, String openId, Double amount, String desc, String ip) {
        String appid ="";
        String secret ="";
        String mchid ="";
        String key ="";
        String wxPayNoticeUrl ="";

        switch (weixinGZH){
            case "user":{
                appid = WxConst.UserWeixinAccount.APPID;
                secret = WxConst.UserWeixinAccount.SECRET;
                mchid = WxConst.UserWeixinAccount.MCHID;
                key = WxConst.UserWeixinAccount.KEY;
                wxPayNoticeUrl = WxConst.UserWeixinAccount.NOTICE_URL;
                break;
            }
        }

        BizPayParam bizPayParam = new BizPayParam();
        bizPayParam.setMch_appid(appid);
        bizPayParam.setMchid(mchid);
        bizPayParam.setNonce_str(Util.getRandomNumber(32));
        bizPayParam.setPartner_trade_no(orderNum);
        bizPayParam.setOpenid(openId);
        bizPayParam.setCheck_name("NO_CHECK");
        bizPayParam.setAmount((int)(amount*100));
        bizPayParam.setDesc(desc);
        bizPayParam.setSpbill_create_ip(ip);


        HttpsRequest request = null;
        try {
            String sign = Signature.getSign(bizPayParam,key);
            //sign
            bizPayParam.setSign(sign);

            request = new HttpsRequest(weixinGZH);
            String orderRst = request.sendPost(WxConst.URLS.WX_BIZ_PAY,bizPayParam,weixinGZH);
            Map<String,Object> orderRstMap = XMLParser.getMapFromXML(orderRst);

            return JSON.toJSONString(orderRstMap);
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return null;
    }

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
    @Override
    public String sendredpack(String weixinGZH, String sendName, String orderNum, String openId, Double totalAmount, String wishing, String ip, String actName, String remarks) {
        String appid ="";
        String secret ="";
        String mchid ="";
        String key ="";
        String wxPayNoticeUrl ="";

        switch (weixinGZH){
            case "user":{
                appid = WxConst.UserWeixinAccount.APPID;
                secret = WxConst.UserWeixinAccount.SECRET;
                mchid = WxConst.UserWeixinAccount.MCHID;
                key = WxConst.UserWeixinAccount.KEY;
                wxPayNoticeUrl = WxConst.UserWeixinAccount.NOTICE_URL;
                break;
            }
        }
        Redpack redpack = new Redpack();
        redpack.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
        redpack.setMch_billno(orderNum);
        redpack.setMch_id(mchid);
        redpack.setWxappid(appid);
        redpack.setSend_name(sendName);
        redpack.setRe_openid(openId);
        redpack.setTotal_amount((int)(totalAmount*100));
        redpack.setTotal_num(1);
        redpack.setWishing(wishing);
        redpack.setClient_ip(ip);
        redpack.setAct_name(actName);
        redpack.setRemark(remarks);

        HttpsRequest request = null;
        try {
            String sign = Signature.getSign(redpack,key);
            redpack.setSign(sign);

            request = new HttpsRequest(weixinGZH);
            String orderRst = request.sendPost(WxConst.URLS.WX_RED_PACK,redpack,weixinGZH);
            Map<String,Object> orderRstMap = XMLParser.getMapFromXML(orderRst);



            return JSON.toJSONString(orderRstMap);
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return  null;


    }

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
    @Override
    public String wxPayUnifiedOrder(String weixinGZH ,String openid,
                                    String body,String orderNum,Integer fee,String ip)  {

        String appid ="";
        String secret ="";
        String mchid ="";
        String key ="";
        String wxPayNoticeUrl ="";

        switch (weixinGZH){
            case "user" :
                appid = WxConst.UserWeixinAccount.APPID;
                secret = WxConst.UserWeixinAccount.SECRET;
                mchid = WxConst.UserWeixinAccount.MCHID;
                key = WxConst.UserWeixinAccount.KEY;
                wxPayNoticeUrl = WxConst.UserWeixinAccount.NOTICE_URL;
                break;
        }





        UnifiedOrder unifiedOrder = new UnifiedOrder();
        unifiedOrder.setAppid(appid);
        unifiedOrder.setMch_id(mchid);
        String nonce = RandomStringGenerator.getRandomStringByLength(32);
        unifiedOrder.setNonce_str(nonce);
        unifiedOrder.setBody(body);
        unifiedOrder.setOut_trade_no(orderNum);
        unifiedOrder.setTotal_fee(fee);
        unifiedOrder.setSpbill_create_ip(ip);
        unifiedOrder.setNotify_url(wxPayNoticeUrl);
        unifiedOrder.setTrade_type("JSAPI");
        unifiedOrder.setOpenid(openid);



        HttpsRequest request = null;
        try {
            String sign = Signature.getSign(unifiedOrder,key);
            unifiedOrder.setSign(sign);

            request = new HttpsRequest(weixinGZH);
            String orderRst = request.sendPost(WxConst.URLS.WX_UNIFIED_ORDER,unifiedOrder,weixinGZH);
            Map<String,Object> orderRstMap = XMLParser.getMapFromXML(orderRst);

            Map<String,Object> jsApiRst = new HashMap<String,Object>();
            jsApiRst.put("appId",appid);
            //当前时间戳 10位
            jsApiRst.put("timeStamp",(new Date().getTime()+"").substring(0,10));
            //32位随机字符串
            jsApiRst.put("nonceStr",nonce);
            //订单详情扩展字符串	一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
            jsApiRst.put("package","prepay_id="+(String)orderRstMap.get("prepay_id"));
            jsApiRst.put("signType","MD5");
//            jsApiRst.put("totalFee",fee.toString());
            //sign
            String jsSign = Signature.getSign(jsApiRst,key);
            jsApiRst.put("paySign",jsSign);


            return JSON.toJSONString(jsApiRst);
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return  null;
    }



    /**
     * 微信扫码支付统一下单 返回二维码 URL
     */
    @Override
    public String wxScanUnifiedOrder(String weixinGZH,String body,String productId,String orderNum,Integer fee,String ip){
        String appid ="";
        String secret ="";
        String mchid ="";
        String key ="";
        String wxPayNoticeUrl ="";

        switch (weixinGZH){
            case "user" :
                appid = WxConst.UserWeixinAccount.APPID;
                secret = WxConst.UserWeixinAccount.SECRET;
                mchid = WxConst.UserWeixinAccount.MCHID;
                key = WxConst.UserWeixinAccount.KEY;
                wxPayNoticeUrl = WxConst.UserWeixinAccount.NOTICE_URL;
                break;
        }

        UnifiedOrder unifiedOrder = new UnifiedOrder();
        unifiedOrder.setAppid(appid);
        unifiedOrder.setMch_id(mchid);
        String nonce = RandomStringGenerator.getRandomStringByLength(32);
        unifiedOrder.setNonce_str(nonce);
        unifiedOrder.setBody(body);
        unifiedOrder.setOut_trade_no(orderNum);
        unifiedOrder.setTotal_fee(fee);
        unifiedOrder.setSpbill_create_ip(ip);
        unifiedOrder.setNotify_url(wxPayNoticeUrl);
        unifiedOrder.setTrade_type("NATIVE");
        unifiedOrder.setProduct_id(productId);


        HttpsRequest request = null;
        try {
            String sign = Signature.getSign(unifiedOrder,key);
            unifiedOrder.setSign(sign);

            request = new HttpsRequest(weixinGZH);
            String orderRst = request.sendPost(WxConst.URLS.WX_UNIFIED_ORDER,unifiedOrder,weixinGZH);
            Map<String,Object> orderRstMap = XMLParser.getMapFromXML(orderRst);


            return (String) orderRstMap.get("code_url");
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }




}
