package com.sxp.sa.basic.constant;

/**
 * Created by Administrator on 2017/2/18.
 */
public interface WxConst {




    /*各种url*/
    public interface URLS{
        /*微信统一下单*/
        String WX_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        /**
         * 发红包
         */
        String WX_RED_PACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
        /**
         * 企业打款
         */
        String WX_BIZ_PAY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
        /**
         * 查询企业打款
         */
        String WX_QUERY_BIZ_PAY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
    }
    //账号信息
    public interface UserWeixinAccount{

        //userToken
        String USER = "user";


        String BASEMSG_TITLE = USER+"WxBaseMsgTitle";
        String BASEMSG_DESC = USER+"WxBaseMsgDesc";
        String BASEMSG_PICURL = USER+"WxBaseMsgPicUrl";
        String BASEMSG_URL = USER+"WxBaseMsgUrl";


        //  token
        String TOEKN = "user";
        //appid
        String APPID = "wx47ab17c464379860";
        //secret
        String SECRET = "05ee3aa8b8448d6ec5a47ab25fbffa22";
        //是否开启 jsApi
        Boolean OPEN_JSAPI = true;

        //pay 未修改
        String MCHID = "1490709252";
        String KEY = "13669302113669302113669302113669";
        String NOTICE_URL = Const.API_BASE_URL+"/common/wxRechargeNotice/"+USER;

        String CERT_PAHT = "C:/cert/apiclient_cert.p12";

        //OauthApi 回调地址
        String OAUTH_CBURL = Const.API_BASE_URL+"/wx/user/wxLogin";
        //正常用户登录后的地址
        String WX_LOGIN_URL = Const.BASE_URL+"#/index/";

        //老用户 登录地址
        String WX_OLDUSER_URL = Const.BASE_URL+"#/wxreg/";

        //微信注册地址
        String WX_REG_URL = Const.BASE_URL+"#/wxreg/";
        //微信绑定登录地址
        String WX_LOGIN_BIND_URL = Const.BASE_URL+"#/wxlogin/";

        //信用户登录地址
        String WX_NEW_LOGIN_URL = Const.BASE_URL+"#/";


    }



}
