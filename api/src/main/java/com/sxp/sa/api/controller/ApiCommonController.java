package com.sxp.sa.api.controller;

import cn.com.duiba.credits.sdk.result.CreditConsumeResult;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.constant.WxConst;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.basic.utils.wxpay.Signature;
import com.sxp.sa.basic.utils.wxpay.XMLParser;
import com.sxp.sa.merchant.entity.About;
import com.sxp.sa.merchant.service.AboutService;
import com.sxp.sa.agent.service.BannerService;
import com.sxp.sa.agent.vo.BannerVo;
import com.sxp.sa.order.service.OrderService;
import com.sxp.sa.user.entity.BaomingInfos;
import com.sxp.sa.user.entity.UrlFilterList;
import com.sxp.sa.user.repository.BaomingInfosRepository;
import com.sxp.sa.user.service.FeedbackService;
import com.sxp.sa.user.service.SMSCodeService;
import com.sxp.sa.user.vo.SMSCodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static com.sxp.sa.basic.utils.Util.isNotEmpty;

/**
 * Created by dell on 2017/7/20.
 */

@Api(value = "通用接口", description = "通用接口")
@RestController
@RequestMapping(value = "api/common", method = RequestMethod.POST)
public class ApiCommonController {


    @Autowired
    private BannerService bannerService;
    @Autowired
    private AboutService aboutService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private SMSCodeService smsCodeService;
    @Autowired
    private OSSClient ossClient;
    @Autowired
    private BaomingInfosRepository baomingInfosRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UrlFilterList urlFilterList;


    @RequestMapping(value = "/mainJs" ,method=RequestMethod.GET)
    public void mainJs(
            HttpServletResponse resp
    )throws IOException{
        if(isNotEmpty(redisTemplate.opsForValue().get("mainjs"))){
            resp.sendRedirect((String)redisTemplate.opsForValue().get("mainjs"));
        }else{
            resp.sendRedirect("http://sa.51shenga.com/application/app.js");
        }

    }


    @RequestMapping(value = "/getSmsCode" ,method=RequestMethod.POST)
    @ApiOperation(value = "获取短信验证码" , httpMethod = "POST", notes = "获取短信验证码如注册,忘记密码等")
    public Rst<SMSCodeVo> getSmsCode(
            @ApiParam(value = "手机号",required = true) @RequestParam(required = true,value = "mobile") String mobile,
            @ApiParam(value = "短信类型 1.注册,2.忘记密码 见SMSCodeType 3.绑定手机",required = true) @RequestParam(required = true,value = "smsType") String smsType,
            HttpServletRequest request
    )throws BusinessException {

        Rst<SMSCodeVo> rst = new Rst<>();

        String ip = Util.getRemoteHost(request);

        smsCodeService.sendSms(mobile,smsType,ip);

        return rst;
    }

    @RequestMapping(value = "/refreshResource", method = RequestMethod.POST)
    @ApiOperation(value = "更新资源", httpMethod = "POST", notes = "更新资源")
    public Rst<Object> refreshResource(
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Object> rst = new Rst<>();
        urlFilterList.initFilterList();
        return rst ;
    }



    @RequestMapping(value = "/getBanners", method = RequestMethod.POST)
    @ApiOperation(value = "获取banner列表", httpMethod = "POST", notes = "获取banner列表")
    public Rst<List<BannerVo>> getBanners(
            @ApiParam(value = "经度",required = false) @RequestParam(required = false, value = "longitude") String longitude,
            @ApiParam(value = "维度",required = false) @RequestParam(required = false, value = "latitude") String latitude,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<List<BannerVo>> rst = new Rst<>();

        rst.setObject(bannerService.findBannerList(longitude,latitude));
        return rst ;
    }


    @RequestMapping(value = "/aboutUs", method = RequestMethod.POST)
    @ApiOperation(value = "获取关于我们", httpMethod = "POST", notes = "获取关于我们")
    public Rst<About> aboutUs(
            @ApiParam(value = "系统类型") @RequestParam(required = true, value = "osType") Integer osType,
            HttpServletRequest request
    ) throws BusinessException {
        Rst<About> rst = new Rst<>();
        rst.setObject(aboutService.findByOSType(osType));
        return rst ;
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    @ApiOperation(value = "意见反馈", httpMethod = "POST", notes = "意见反馈")
    public Rst<Object> feedback(
            @ApiParam(value = "用户id",required = false) @RequestParam(required = false, value = "uid") Long uid,
            @ApiParam(value = "反馈类型 1.产品问题.2账号异常,3其他",required = true) @RequestParam(required = true, value = "fbType") Integer fbType,
            @ApiParam(value = "反馈类型描述 1.产品问题.2账号异常,3其他",required = true) @RequestParam(required = true, value = "fbTypeDesc") String fbTypeDesc,
            @ApiParam(value = "反馈内容",required = true) @RequestParam(required = true, value = "content") String content,
            @ApiParam(value = "反馈内容图片 json数组",required = true) @RequestParam(required = true, value = "images") String images,
            HttpServletRequest request
    ) throws BusinessException {
        Rst<Object> rst = new Rst<>();

        feedbackService.addFeedback(uid,fbType,fbTypeDesc,content,images);

        return rst ;
    }


    @RequestMapping(value = "uploadFile",method = RequestMethod.POST)
    @ApiOperation(value = "文件上传" , httpMethod = "POST", notes = "文件上传")
    public Rst<String> uploadFile(
            @ApiParam("文件") @RequestParam(value="file",required=true) MultipartFile file,
            @ApiParam("存储路径") @RequestParam(value="path",required=true) String path,
            @ApiParam(value="bucketName",required=false) @RequestParam(value="bucketName",defaultValue = "51shenga") String bucketName,
            HttpServletRequest request
    )throws BusinessException{
        Rst<String> rst = new Rst<>();

        // 上传文件流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PutObjectResult result = ossClient.putObject(bucketName, path, inputStream);

        rst.setObject("http://oss"+bucketName+".51shenga.com/"+path);
        return rst;
    }



    @RequestMapping(value = "baoMing",method = RequestMethod.POST)
    @ApiOperation(value = "报名" , httpMethod = "POST", notes = "报名")
    public Rst<BaomingInfos> baoMing(
            @ApiParam("姓名") @RequestParam(value="realname",required=true) String realname,
            @ApiParam("电话") @RequestParam(value="mobile",required=true) String mobile,
            @ApiParam(value = "qq",required = false) @RequestParam(value="qq",required=false,defaultValue = "") String qq,
            @ApiParam(value = "wx",required = false) @RequestParam(value="wx",required=false,defaultValue = "") String wx,
            @ApiParam(value = "email",required = false) @RequestParam(value="email",required=false,defaultValue = "") String email,
            @ApiParam(value = "渠道",required = false) @RequestParam(value="qudao",required=false,defaultValue = "1") Integer qudao,
            HttpServletRequest request
    )throws BusinessException{
        Rst<BaomingInfos> rst = new Rst<>();


        BaomingInfos baomingInfos = new BaomingInfos();
        baomingInfos.setRealname(realname);
        baomingInfos.setMobile(mobile);
        baomingInfos.setQq(qq);
        baomingInfos.setWx(wx);
        baomingInfos.setEmail(email);
        baomingInfos.setQudao(qudao);

        baomingInfos = baomingInfosRepository.save(baomingInfos);
        rst.setObject(baomingInfos);

        return rst;
    }


    @RequestMapping(value = "getAllBaoMingInfos",method = RequestMethod.POST)
    @ApiOperation(value = "获取所有报名" , httpMethod = "POST", notes = "获取所有报名")
    public Rst< Page<BaomingInfos>> getAllBaoMingInfos(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "起始时间",required = false) @RequestParam(required = false, value = "startTime",defaultValue = "0") Long  startTime,
            @ApiParam(value = "结束时间",required = false) @RequestParam(required = false, value = "endTime",defaultValue = "3723724800000") Long  endTime,
            HttpServletRequest request
    )throws BusinessException{
        Rst< Page<BaomingInfos>> rst = new Rst<>();


        Page<BaomingInfos> bms = baomingInfosRepository.findAll(startTime,endTime,pageable);
        rst.setObject(bms);

        return rst;
    }



    @RequestMapping(value = "/duibaIntergral",method = RequestMethod.GET)
    @ApiOperation(value = "兑吧扣除积分" , httpMethod = "GET", notes = "兑吧扣除积分")
    public void duibaIntergral(
            @ApiParam(value = "用户id",required = true) @RequestParam(required = true, value = "uid") Long  uid,
            @ApiParam(value = "本次兑换扣除的积分",required = true) @RequestParam(required = true, value = "credits") Integer  credits,
            @ApiParam(value = "appkey",required = true) @RequestParam(required = true, value = "appKey") String  appKey,
            @ApiParam(value = "timestamp",required = true) @RequestParam(required = true, value = "timestamp") String  timestamp,
            @ApiParam(value = "orderNum",required = true) @RequestParam(required = true, value = "orderNum") String  orderNum,
            @ApiParam(value = "兑换类型：alipay(支付宝), qb(Q币), coupon(优惠券), object(实物), phonebill(话费), phoneflow(流量), virtual(虚拟商品), turntable(大转盘), singleLottery(单品抽奖)，hdtoolLottery(活动抽奖),htool(新活动抽奖),manualLottery(手动开奖),gameLottery(游戏),ngameLottery(新游戏),questionLottery(答题),quizzLottery(测试题),guessLottery(竞猜) 所有类型不区分大小写",required = true) @RequestParam(required = true, value = "type") String  type,
            @ApiParam(value = "此次兑换实际扣除开发者账户费用，单位为分",required = true) @RequestParam(required = true, value = "actualPrice") Integer  actualPrice,
            @ApiParam(value = "sign",required = true) @RequestParam(required = true, value = "sign") String   sign,




            @ApiParam(value = "自有商品商品编码(非必须字段)",required = false) @RequestParam(required = false, value = "itemCode",defaultValue = "0") String  itemCode,
            @ApiParam(value = "本次积分消耗的描述(带中文，请用utf-8进行url解码)",required = false) @RequestParam(required = false, value = "description",defaultValue = "0") String  description,
            @ApiParam(value = "兑换商品的市场价值，单位是分，请自行转换单位",required = false) @RequestParam(required = false, value = "facePrice",defaultValue = "0") Integer  facePrice,
            @ApiParam(value = "ip",required = false) @RequestParam(required = false, value = "ip",defaultValue = "0") String ip,
            @ApiParam(value = "是否需要审核(如需在自身系统进行审核处理，请记录下此信息)",required = false) @RequestParam(required = false, value = "waitAudit",defaultValue = "0") String waitAudit,
            @ApiParam(value = "详情参数，不同的类型，返回不同的内容，中间用英文冒号分隔。(支付宝类型带中文，请用utf-8进行解码) 实物商品：返回收货信息(姓名:手机号:省份:城市:区域:详细地址)、支付宝：返回账号信息(支付宝账号:实名)、话费：返回手机号、QB：返回QQ号",required = false) @RequestParam(required = false, value = "params",defaultValue = "") String params,


            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception{

        String rst = orderService.duibaRaduceIntergral(uid,credits,appKey,timestamp,orderNum,type,sign,itemCode,description,facePrice,waitAudit,params);

        if(isNotEmpty(rst)){
            CreditConsumeResult result=new CreditConsumeResult(true);
            result.setBizId(rst);
            response.getWriter().write(result.toString());
        }else{
            CreditConsumeResult result=new CreditConsumeResult(false);
            result.setErrorMessage("积分不足");
            response.getWriter().write(result.toString());
        }
    }





    @RequestMapping(value = "/duibaRst",method = RequestMethod.GET)
    @ApiOperation(value = "兑吧结果通知" , httpMethod = "GET", notes = "兑吧结果通知")
    public String duibaRst(
            @ApiParam(value = "appkey",required = true) @RequestParam(required = true, value = "appKey") String  appKey,
            @ApiParam(value = "timestamp",required = true) @RequestParam(required = true, value = "timestamp") String  timestamp,
            @ApiParam(value = "orderNum",required = true) @RequestParam(required = true, value = "orderNum") String  orderNum,
            @ApiParam(value = "sign",required = true) @RequestParam(required = true, value = "sign") String   sign,
            @ApiParam(value = "兑换是否成功，状态是true和false",required = true) @RequestParam(required = true, value = "success") String   success,
            @ApiParam(value = "出错原因(带中文，请用utf-8进行解码)",required = false) @RequestParam(required = false, value = "errorMessage",defaultValue = "0") String  errorMessage,
            @ApiParam(value = "bizId",required = false) @RequestParam(required = false, value = "bizId",defaultValue = "0") String  bizId,


            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception{

        return orderService.duibaRst(appKey,timestamp,orderNum,sign,success,errorMessage,bizId);
    }



    /**
     * 充值微信回调
     */
    @RequestMapping(value="/wxRechargeNotice/{wxname}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "账户充值微信回调", httpMethod = "POST", notes = "账户充值回调")
    public String rechargeNotice(
            @ApiParam(required = true, name = "wxname", value = "用户名") @PathVariable String wxname,
            HttpServletRequest request
    )throws BusinessException {

        try {
            String cbString = com.sxp.sa.basic.utils.wxpay.Util.inputStreamToString(request.getInputStream());
            Map<String,Object> rstMap = XMLParser.getMapFromXML(cbString);

            String appid = (String)rstMap.get("appid");
            String feeType = (String)rstMap.get("fee_type");
            String nonceStr = (String)rstMap.get("nonce_str");
            String outTradeNo = (String)rstMap.get("out_trade_no");
            String tradeType = (String)rstMap.get("trade_type"); //"JSAPI"->公众号支付

            String resultCode = (String) rstMap.get("result_code");
            String sign = (String) rstMap.get("sign");
            String mchId = (String) rstMap.get("mch_id");
            String totalFee = (String) rstMap.get("total_fee");//2 分
            String timeEnd = (String) rstMap.get("time_end");  //支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则

            String openid = (String) rstMap.get("openid");
            String bankType = (String) rstMap.get("bank_type");//银行类型，采用字符串类型的银行标识，银行类型见银行列表


            String returnCode = (String) rstMap.get("return_code");
            String cashFee = (String) rstMap.get("cash_fee");//现金支付金额订单现金支付金额，详见支付金额


            String key = "";
            switch (wxname){
                case WxConst.UserWeixinAccount.USER :{
                    key = WxConst.UserWeixinAccount.KEY;
                    break;
                }
            }

            Boolean checkRst = Signature.checkIsSignValidFromResponseString(cbString, WxConst.UserWeixinAccount.KEY);
            //验签 返回成功, 且非正在处理
            if(resultCode.equals("SUCCESS") && returnCode.equals("SUCCESS")  && checkRst && Util.isEmpty(redisTemplate.opsForValue().get(outTradeNo))){





                Boolean rst = orderService.payCallBackWork(outTradeNo,(Double.parseDouble(totalFee)/100.0), Const.PayType.WX_PAY);

                if(rst){
                    //告诉微信  欧拉
                    return Util.setXML("SUCCESS","OK");
                }
                return null;


            }else{
                //TODO 记录回调失败的数据
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }


}
