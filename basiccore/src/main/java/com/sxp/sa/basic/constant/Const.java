package com.sxp.sa.basic.constant;

/**
 * Created by miss on 2017/2/17.
 */
public interface Const {

    /**
     * web baseurl
     */
    public String BASE_URL = "http://sa.51shenga.com/";
    /**
     * 接口 baseurl
     */
    public String API_BASE_URL = "http://api.51shenga.com";
    /**
     * 全局开启 REDIS 缓存
     */
    public  Boolean  OPNE_REDIS_CACHE = true;

    /**
     * 开启短信验证
     */
    public  Boolean OPEN_SMS = true;

    /**
     * token验证是否开启
     */
    public Boolean TOKEN_CHECK = false;

    public Boolean ADMIN_TOKEN_CHECK = false;

    /**
     * 验证码过期时间
     */
    public Long SMS_PERIOD = 15*60*1000l;

    String OSS_BUCKET_NAME = "51shenga";

    /**
     * banner默认滚动时间
     */
    Long BANNER_CAROUSEL = 3000l;
    /**
     * 无法获取定位时的默认地址
     */
    public Double DEFAULT_LATITUDE = 0.0;
    public Double DEFAULT_LONGITUDE = 0.0;

    /**
     * 默认可提现数
     */
    Integer CAN_DRAW_NUM =2;
    Integer CAN_GET_RED_NUM = 2;
    Double DRAW_MN_MONEY = 100.0;
    Integer PAYMENT_DATA = 24 ;//提现到账时间 / 小时
    /**
     * 推荐商家条数  detail
     */
    public Integer RECOMMOND_NUMS = 3;
    public Integer COMMON_NUMS = 2;

    public interface MerchantInitInfo{
        String MERCHANT_INIT_STARTS = "0";
        Integer MERCHANT_COMSMERPTION = 0;

        Double profits = 0.1;

        Integer rebateType = 1;

        Double rebate = 0.25;

        Double rebateToMerchant = 0.1;

        Double retateToParent1 = 0.2;

        Double retateToParent2 = 0.05;

        Double retateToParent3 = 0.0;


        Double retateToProvinceAgent = 0.00;

        Double retateToCityAgent = 0.00;

        Double retateToDistrictAgent = 0.25;

        Double retateToAreaAgent = 0.00;



    }


    public interface DuiBaParam{
        String KEY = "37CoLDNzK2et5AbQ7zJU3QMizFdx";
        String SECRET = "3RhXCt8dQDdygjUgQfDg5bUHAdZt";
    }


    /**
     * @Description: 常用状态类型
     * @author sxp
     */
    public interface Status {
        /**
         * @Fields locked : 锁定状态
         */
        int locked = -1;

        /**
         * @Fields invalid : 删除/无效状态
         */
        int invalid = 0;

        /**
         * @Fields valid : 有效/正常状态
         */
        int valid = 1;


        /**
         * @Fields delete : 删除
         */
        int delete = 2;


        /**
         * 出售状态 
         */
        int selling = 3;





    }

    public interface  AuditStatus{

        //提交审核
        int create = 0;
        int success = 1;
        int fail = 2;
    }




    /**
     * 返回代码
     */
    public interface Code{

        /**
         * 成功
         */
        int SUCCESS = 1;
        /**
         * 校验参数有误
         */
        Integer PARAM_FORMAT_ERROR = 2;
        /**
         * 权限不足
         */
        Integer UNAUTHORIZED = 3;
        /**
         * token验证失败
         */
        int TOKEN_ERROR = -1;
        /**
         * @Fields error : 失败
         */
        int ERROR = 0;

        int PARAM_ERROR=4;



        Integer RUNTIME_EXCEPTION = 10000;

        Integer RED_POOL_EXCEPTION = 10001;
        String RED_POOL_EXCEPTION_MSG = "红包池异常";

        Integer RED_POOL_EMPTY = 10002;
        String RED_POOL_EMPTY_MSG = "红包池已空";

        Integer SMS_SEND_ERROR = 11003;
        String SMS_SEND_ERROR_MSG = "短信发送失败";


        Integer USER_NOT_EXISTS = 11001;
        String USER_NOT_EXISTS_MSG = "用户不存在";

        Integer DRAW_CHANNEL_NOT_EXISTS = 11002;
        String DRAW_CHANNEL_NOT_EXISTS_MSG = "提现渠道不存在";

        Integer DRAW_NOT_EXISTS = 11003;
        String DRAW_NOT_EXISTS_MSG = "提现记录不存在";

        Integer ACCOUNT_ERROR = 11002;
        String ACCOUNT_ERROR_MSG = "账户异常";

        Integer ACCOUNT_GET_RB_NUMS_LOW = 11003;
        String ACCOUNT_GET_RB_NUMS_LOW_MSG = "抢红包次数不足";

        Integer DRAW_NUM_ERROR = 11004;
        String DRAW_NUM_ERROR_MSG = "提现额度不足";

        Integer CAN_DRAW_NUMS_LESS = 11005;
        String CAN_DRAW_NUMS_LESS_MSG = "当日提现次数不足";

        Integer PWD_ERROR = 11006;
        String PWD_ERROR_MSG = "密码错误";

        Integer SMS_ERROR = 11007;
        String SMS_ERROR_MSG = "验证码错误";


        /*店铺相关*/
        Integer MERCHANT_NOT_EXISTS = 12001;
        String MERCHANT_NOT_EXISTS_MSG = "店铺不存在";
        Integer MERCHANT_TYPE_ERROR = 12002;
        String MERCHANT_TYPE_ERROR_MSG = "店铺分类错误";

        Integer REBATE_AMOUNT_ERROR = 12003;
        String REBATE_AMOUNT_ERROR_MSG = "参与返利金额错误";



        /*订单相关*/
        Integer ORDER_CANT_EVL = 13001;
        String ORDER_CANT_EVL_MSG = "无法评价该订单";



        /**
         * 区域相关
         */
        Integer DISTRICT_NOT_EXISTS = 14001;
        String DISTRICT_NOT_EXISTS_MSG = "区域不存在";


        /*请求结果*/
        Integer REQUEST_ERROR = 50001;
        Integer PROVINCE_NOT_EXISTS = 51000;
        String PROVINCE_NOT_EXISTS_MSG= "省不存在";

        Integer CITY_NOT_EXISTS = 51001;
        String CITY_NOT_EXISTS_MSG= "城市不存在";

        Integer AREA_NOT_EXISTS = 51002;
        String AREA_NOT_EXISTS_MSG = "大区不存在";




        /*权限相关*/
        Integer RESOURCE_NOT_EXISTS = 61001;
        String  RESOURCE_NOT_EXISTS_MSG= "资源不存在";

        Integer ROLE_NOT_EXISTS = 61002;
        String ROLE_NOT_EXISTS_MSG = "角色不存在";

        Integer ADMIN_NOT_EXISTS = 61003;
        String ADMIN_NOT_EXISTS_MSG= "管理员不存在";

        Integer IS_ADMIN_NOW = 61004;
        String IS_ADMIN_NOW_MSG = "已经是管理员无法重复绑定";


        Integer NOTICE_NOT_EXISTS = 62001;
        String NOTICE_NOT_EXISTS_MSG = "后台公告不存在";

        Integer BANNER_NOT_EXISTS = 62002;
        String BANNER_NOT_EXISTS_MSG = "banner不存在";


        Integer AGENT_INFO_NOT_EXISTS = 63001;
        String  AGENT_INFO_NOT_EXISTS_MSG = "代理不存在";

        Integer DISTRICT_HAD_AGENT =  63002;
        String DISTRICT_HAD_AGENT_MSG = "该区域已有代理";
    }



    public interface Message {
        /**
         * @Fields ok : 成功
         */
        String SUCCESS = "成功!";
        /**
         * @Fields error : 失败
         */
        String ERROR = "系统错误!";
        String PARAM_ERROR = "参数有误";
        String OBJECT_NOT_FOUND = "找不到要操作的记录!";

    }




    /*SmsCodeType*/
    public interface SMSCodeType{
        /*注册验证码*/
        String REGIST_CODE = "1";
        /*忘记密码*/
        String FORGET_PASS = "2";
    }


    /**
     * 订单交易状态
     */
    public interface TradeStatus{

        // 这里修改的时候要注意，业务逻辑里面有用大小来判定状态
        //OrderServiceImpl queryHfOrderState 有用到关系

        /**
         * 发起(未付款)
         */
        Integer LAUNCH = 1;


        /**
         * 用户确认支付
         */
        Integer USER_PAY = 2;
        /**
         * 支付回调通知:失败
         */
        Integer PAY_ERROR = 3;

        /**
         * 支付回调成功
         */
        Integer PAY_SUCCESS = 4;

        /**
         * 已发货
         */
        Integer GOODS_SENT=5;

        /**
         * 实体类商品用户确认收货 （虚拟产品已经到账）
         */
        Integer USER_AFFIRM_RECEIVE_GOODS=6;

        /**
         * sales return,用户退货
         */
        Integer USER_SALES_RETURN_GOODS=7;
        /**
         * already 已经,用户已评价商品
         */
        Integer USER_ALREADY_COMMENT_GOODS=8;

    }

    /**
     * 第三方订单交易状态
     */
    public interface ThirdTradeStatus{

        /**
         * 初始值 initial value 备用，如果new 订单的时候，不对 thirdTradeStatus 不进行初始化 则为null
         */
        Integer INITIAL_VALUE=null;




        //10 话费相关
        //订单发往api
        Integer HF_TO_API = 10;

        //正在充值
        Integer HF_API_BEING_RECHARGE=11;

        //api充值成功并回调
        Integer HF_API_CB_SUCCESS = 12;

        //api 返回失败
        Integer HF_API_FAIL = 13;

    }



   public interface  DrawChannelType{
       /**
        * 支付宝类型
        */
       String ALI_PAY="aliPay";
       /**
        * 银行卡类型
        */
       String CARD_NUM="card";
    }

    /**
     * 提现状态
     */
    public interface DrawStatus{

        /**
         * 发起(提现)
         */
        String LAUNCH = "1";

        /**
         * 管理员拒绝提现
         */
        String ADMIN_REJECT="2";
        /**
         * 管理员 通过提现，且，支付宝转账成功 ，提现成功(银行卡，目前是手动，默认没有失败的)
         */
        String  ADMIN_AGREE_SUCCESS = "3";

        /**
         * 管理员 通过提现，支付宝转账返回失败， 提现失败
         */
        String ADMIN_AGREE_ERROR="4";

    }


    /**
     * 三方类型
     */
    public interface ThirdType{

        //微信平台
        String WX = "1";
        //QQ
        String QQ = "2";
        //Sina
        String SINA = "3";
    }


    /**
     * 充值渠道
     */
    public interface PayType{
        //微信支付
        String WX_PAY = "wx_pay";
        //微信扫码支付
        String WX_SCAN = "wx_scan";
        //支付宝支付
        String ALI_PAY = "ali_pay";

        String BALANCE_PAY = "balance_pay";

    }

    public interface AdminRatifyDraw{
        /**
         * 管理员同意
         */
        String ADMIN_AGREE="agree";
        /**
         * 管理员拒绝
         */
        String ADMIN_REJECT="reject";
    }

    /**
     * 阿里云相关参数
     */
    public interface AliParam{


        //ali accessKey
        String ALI_KEY_SA = "LTAIWdQibagTRrWb";


        //ali secret
        String ALI_SECRET_SA = "JBlLGegKXgdyXqoAda2IeF9DJSPFrN";


    }

    public interface SMSParams{
        //短信 end-point
        String SMS_ENDPOINT = "cn-hangzhou";
        //短信 regionId
        String SMS_REGION_ID = "cn-hangzhou";
        //短信 产品
        String SMS =  "Dysmsapi";//"Sms";
        //短信 domain
        String SMS_DOMAIN = "dysmsapi.aliyuncs.com";
        //短信签名名称
        String SMS_SIGN_NAME = "sa";
        //短信模板名称
        String SMS_TPL_NAME = "sa";
        //短信位数
        Integer SMS_LENGTH = 4;
    }

}
