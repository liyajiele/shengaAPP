package com.sxp.sa.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.sxp.sa.agent.entity.AgentInfos;
import com.sxp.sa.agent.repository.AgentInfosRepository;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.constant.WxConst;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.merchant.entity.Merchant;
import com.sxp.sa.merchant.repository.MerchantRepository;
import com.sxp.sa.merchant.vo.CommonVo;
import com.sxp.sa.merchant.vo.MerchantDetailVo;
import com.sxp.sa.order.entity.*;
import com.sxp.sa.order.repository.*;
import com.sxp.sa.order.service.OrderService;
import com.sxp.sa.order.service.WxService;
import com.sxp.sa.order.vo.EvlOrderVo;
import com.sxp.sa.user.entity.Account;
import com.sxp.sa.user.entity.ThirdInfo;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.AccountRepository;
import com.sxp.sa.user.repository.ThirdInfoRepository;
import com.sxp.sa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sxp.sa.basic.constant.Const.Code.*;
import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.*;

@Service
public class OrderServiceImpl extends BaseService implements OrderService{

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ThirdInfoRepository thirdInfoRepository;

    @Autowired
    private WxService wxService;

    @Autowired
    private DuibaOrderRepository duibaOrderRepository;


    @Autowired
    private AccountRepository accountDao;

    @Autowired
    private AccountRecordRepository accountRecordRepository;

    @Autowired
    private MerchantAccountRecordRepository merchantAccountRecordRepository;

    @Autowired
    private AgentAccountRecordRepository agentAccountRecordRepository;

    @Autowired
    private AgentInfosRepository agentInfosRepository;


    /**
     * 获取商家详情,增加最近两条评论
     * @param mvo
     * @return
     */
    @Override
    public MerchantDetailVo addCommons(MerchantDetailVo mvo) {



        Page<Order> commons = orderRepository.findOrderByStatus(mvo.getId(), Const.TradeStatus.USER_ALREADY_COMMENT_GOODS,new PageRequest(0,Const.COMMON_NUMS,null));

        if(commons.getNumberOfElements()>0){
            List<CommonVo> commonVos = new ArrayList<>();

            for(int i =0 ;i<commons.getNumberOfElements();i++){
                CommonVo cVo = new CommonVo();

                if(1 == commons.getContent().get(i).getRetaType()){
                    cVo.setAvatar(commons.getContent().get(i).getUser().getAvatar());
                    cVo.setNickname(commons.getContent().get(i).getUser().getNickname());
                }else{
                    cVo.setAvatar("");
                    cVo.setNickname("匿名");
                }
                cVo.setStars(commons.getContent().get(i).getRetaStra().toString());
                cVo.setRetaCont(commons.getContent().get(i).getRetaCont());
                cVo.setCreateTime(commons.getContent().get(i).getCreateTime());

                commonVos.add(cVo);
            }
            mvo.setCommons(commonVos);
        }


        return mvo;
    }

    /**
     * 查询评论信息
     * @param mid
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> getMerchantCommons(Long mid, Pageable pageable) throws BusinessException {

        Merchant merchant = merchantRepository.findById(mid,valid);
        if(isEmpty(merchant)){
            throw new BusinessException(Const.Code.MERCHANT_NOT_EXISTS,Const.Code.MERCHANT_NOT_EXISTS_MSG);
        }

        Page<Order> commons = orderRepository.findOrderByStatus(mid, Const.TradeStatus.USER_ALREADY_COMMENT_GOODS,pageable);

        Pager<CommonVo> rst =  new Pager<>();
        List<CommonVo> commonVoList = new ArrayList<>();

        for(int i = 0 ;i<commons.getNumberOfElements();i++){
            CommonVo cVo = new CommonVo();

            if(1 == commons.getContent().get(i).getRetaType()){
                cVo.setAvatar(commons.getContent().get(i).getUser().getAvatar());
                cVo.setNickname(commons.getContent().get(i).getUser().getNickname());
            }else{
                cVo.setAvatar("");
                cVo.setNickname("匿名");
            }
            cVo.setStars(commons.getContent().get(i).getRetaStra().toString());
            cVo.setRetaCont(commons.getContent().get(i).getRetaCont());

            commonVoList.add(cVo);
        }

        rst.setLast(commons.isLast());
        rst.setFirst(commons.isFirst());
        rst.setNumber(commons.getNumber());
        rst.setNumberOfElements(commons.getNumberOfElements());
        rst.setSize(commons.getSize());
        rst.setTotalElements(commons.getTotalElements());
        rst.setTotalPages(commons.getTotalPages());
        rst.setContent(commonVoList);


        Integer commonsNum = 0;
        if(isNotEmpty(merchant.getCommentNum())){
            commonsNum = merchant.getCommentNum();
        }

        Map<String,Object> comRst = new HashMap<>();
        comRst.put("commont",rst);
        comRst.put("commonNum",commonsNum);

        return comRst;
    }


    /**
     * 获取待评价订单
     * @param uid
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<EvlOrderVo> getMineOrderToBeEvaluated(Long uid, Pageable pageable) throws BusinessException {

        User user = userDao.findByIdAndStatus(uid,valid);
        //用户不存在
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }

        Page<Order> orders = orderRepository.findOrderByTradeStatus(uid,Const.TradeStatus.PAY_SUCCESS,pageable);

        Pager<EvlOrderVo> rst = p2pr(orders,EvlOrderVo.class);


        return rst;
    }

    /**
     * 评价订单
     * @param uid
     * @param orderId
     * @param retaType
     * @param retaCont
     * @param retaStar
     * @return
     * @throws BusinessException
     */
    @Override
    public EvlOrderVo evalorder(Long uid, Long orderId, Integer retaType, String retaCont, Double retaStar) throws BusinessException {

        Order order = orderRepository.findById(orderId,valid);

        //不是完成支付 无法评价
        if(isEmpty(order) || order.getTradeStatus()!=4){
            throw new BusinessException(ORDER_CANT_EVL,ORDER_CANT_EVL_MSG);
        }

        order.setTradeStatus(Const.TradeStatus.USER_ALREADY_COMMENT_GOODS);
        order.setRetaType(retaType);
        order.setRetaCont(retaCont);
        order.setRetaStra(retaStar);

        order = orderRepository.save(order);

        //修改星级
        Double stars = orderRepository.selectAvgStar(order.getMerchant().getId());

        Merchant merchant = merchantRepository.findById(order.getMerchant().getId(),valid);

        merchant.setStars(stars.toString());
        merchant.setCommentNum(merchant.getCommentNum()+1);

        return BeanMapper.map(order,EvlOrderVo.class);
    }

    /**
     * 获取返利成功订单
     * @param uid
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> getMineRebetaOrders(Long uid, Long startTime, Long endTime, Pageable pageable) throws BusinessException {

        User user = userDao.findByIdAndStatus(uid,valid);
        //用户不存在
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }
        Page<Order> orders = orderRepository.findOrderByRetaAndTime(uid,Const.TradeStatus.PAY_SUCCESS,startTime,endTime,pageable);

        Pager<EvlOrderVo> evlOrderVoPager = p2pr(orders,EvlOrderVo.class);

        Map<String,Object> rst = new HashMap<>();
        rst.put("orders",evlOrderVoPager);
        rst.put("ordersNum",orderRepository.queryRetaOrderNum(uid,Const.TradeStatus.PAY_SUCCESS,startTime,endTime));

        Double rebateNum = orderRepository.queryRetaOrderRebateNum(uid,Const.TradeStatus.PAY_SUCCESS,startTime,endTime);
        if(isEmpty(rebateNum)){
            rebateNum = 0.0;
        }
        rst.put("rebateNum",rebateNum);

        return rst;
    }

    /**
     * 获取返利成功订单
     * @param uid
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> getMineFinishedOrders(Long uid, Long startTime, Long endTime, Pageable pageable) throws BusinessException {

        User user = userDao.findByIdAndStatus(uid,valid);
        //用户不存在
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }
        Page<Order> orders = orderRepository.findOrderByTradeStatusAndTime(uid,Const.TradeStatus.USER_ALREADY_COMMENT_GOODS,startTime,endTime,pageable);

        Pager<EvlOrderVo> evlOrderVoPager = p2pr(orders,EvlOrderVo.class);

        Map<String,Object> rst = new HashMap<>();
        rst.put("orders",evlOrderVoPager);
        rst.put("ordersNum",orderRepository.queryOrderNum(uid,Const.TradeStatus.USER_ALREADY_COMMENT_GOODS,startTime,endTime));
        rst.put("rebateNum",orderRepository.queryOrderRebateNum(uid,Const.TradeStatus.USER_ALREADY_COMMENT_GOODS,startTime,endTime));

        return rst;
    }

    /**
     * 未评价订单数
     * @param uid
     * @return
     * @throws BusinessException
     */
    @Override
    public Integer getMineOrderToBeEvaluatedNums(Long uid) throws BusinessException {
        return orderRepository.queryOrderNotEvlNums(uid);
    }

    /**
     * 下单
     * @param mid
     * @param totalAmount
     * @param rebateAmount
     * @param balanceAmount
     * @return
     * @throws BusinessException
     */
    @Override
    @Transactional
    public String pay(Long uid,Long mid,String payType, Double totalAmount, Double rebateAmount, Double balanceAmount,String ip,String longitude,String latitude) throws BusinessException {

        if(rebateAmount>totalAmount){
            throw new BusinessException(REBATE_AMOUNT_ERROR,REBATE_AMOUNT_ERROR_MSG);
        }
        //余额支付 全款
        if(payType.equals(Const.PayType.BALANCE_PAY) && totalAmount>balanceAmount){
            throw new BusinessException(Const.Code.ACCOUNT_ERROR,Const.Code.ACCOUNT_ERROR_MSG);
        }

        User user = null;
        if(isNotEmpty(uid)){
            user = userDao.findByIdAndStatus(uid,valid);
            //如果用户不存在
            if(isEmpty(user)){
                throw new BusinessException(Const.Code.USER_NOT_EXISTS,Const.Code.USER_NOT_EXISTS_MSG);
            }
            Account account = accountRepository.findByUser(uid);
            //余额不足支付
            if(account.getBalance()+account.getRetateBalance()+account.getRedBalance() < balanceAmount){
                throw new BusinessException(Const.Code.ACCOUNT_ERROR,Const.Code.ACCOUNT_ERROR_MSG);
            }
        }else{
            //无用户则不能有 余额付款
            if(balanceAmount>0){
                throw new BusinessException(Const.Code.ACCOUNT_ERROR,Const.Code.ACCOUNT_ERROR_MSG);
            }
        }

        Merchant merchant = merchantRepository.findById(mid,valid);
        if(isEmpty(merchant)){
            throw new BusinessException(Const.Code.MERCHANT_NOT_EXISTS,Const.Code.MERCHANT_NOT_EXISTS_MSG);
        }
        Long curTime = System.currentTimeMillis();

        Order order = new Order();
        order.setUser(user);
        order.setMerchant(merchant);
        String orderNum = Util.getOrderNum("SA");
        order.setOrderNum(orderNum);
        order.setTradeStatus(Const.TradeStatus.LAUNCH);
        order.setTotalAmount(totalAmount);
        order.setRebateAmount(rebateAmount);
        //实收金额
        if(totalAmount-balanceAmount<=0){
            order.setReceiptAmount(0.0);
        }
        order.setReceiptAmount(totalAmount-balanceAmount);

        order.setBalanceAmount(balanceAmount);
        order.setSubject("付款");
        order.setOrderContent("付款");

        order.setGmtCreate(curTime);

        order.setPayType(payType);

        //返利金额
        order.setRebate(rebateAmount*merchant.getRebate());



        order.setIp(ip);
        order.setLongitude(longitude);
        order.setLatitude(latitude);

        order = orderRepository.save(order);

        if(isEmpty(user.getMerchantId())){
            user.setMerchantId(merchant.getId());
        }
        if(isEmpty(user.getAgentId())){
            AgentInfos agentInfos = agentInfosRepository.findByDistrictIdAndAgentLevel(merchant.getDistrict(),5);
            if(isNotEmpty(agentInfos)){
                user.setAgentId(agentInfos.getUser().getId());
            }

        }

        //执行 结算
        if(payType.equals(Const.PayType.BALANCE_PAY)){
            Boolean b = this.payCallBackWork(orderNum,totalAmount,Const.PayType.BALANCE_PAY);
            order.setUser(null);
            if(b){
                /*支付成功*/
                Rst<Order> rst = new Rst<>();
                rst.setObject(order);
                return JSON.toJSONString(rst);
            }else{
                Rst<Order> rst = new Rst<>();
                rst.setObject(order);
                //失败
                return JSON.toJSONString(rst);
            }
        }
        String rstStr = null;

        switch (payType) {
            //支付宝 wap 支付
//            case Const.PayType.ALI_PAY: {
//                String tokenAndId = tokenCache.get("tk" + user.getId()) + user.getId();
//                tokenAndId = Base64.encodeBase64String(tokenAndId.getBytes());
//                rstStr = alipayService.alipayWapOrder(Const.CUR_ALIPAY_APPNAME,orderNum,receiptAmount,subject,tokenAndId,null);
//                break;
//            }
            //微信公众号支付
            case Const.PayType.WX_PAY: {
                ThirdInfo thirdInfo = thirdInfoRepository.findByUserIdAndThirdTypeAndThirdName(uid, Const.ThirdType.WX, WxConst.UserWeixinAccount.USER);
                rstStr = wxService.wxPayUnifiedOrder(WxConst.UserWeixinAccount.USER, thirdInfo.getOpenId(), "购买商品", orderNum, (int) ((totalAmount-balanceAmount) * 100), ip);

                break;
            }
        }


        return rstStr;



    }


    //商户相关-------------------------------------------


    /**
     * 商家获取订单信息
     * @param mid
     * @param tradeStatus
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> getMineOrders(Long mid,Integer tradeStatus,Long startTime,Long endTime, Pageable pageable) throws BusinessException {
        Page<Order> orders = orderRepository.findMerchantOrderByTradeStatusAndTime(mid,tradeStatus,startTime,endTime,pageable);

        Pager<EvlOrderVo> evlOrderVoPager = p2pr(orders,EvlOrderVo.class);

        Map<String,Object> rst = new HashMap<>();
        rst.put("orders",evlOrderVoPager);
        rst.put("ordersNum",orderRepository.queryMerchantOrderNum(mid,tradeStatus,startTime,endTime));
        rst.put("rebateNum",orderRepository.queryMerchantOrderRebateNum(mid,Const.TradeStatus.USER_ALREADY_COMMENT_GOODS,startTime,endTime));

        return rst;
    }




    // ------兑吧



    @Override
    public String  duibaRaduceIntergral(Long uid, Integer credits, String appkey, String timestamp, String orderNum, String type, String sign, String itemCode, String description, Integer facePrice, String waitAudit, String params) {


        Account account = accountDao.findByUser(uid);
        if(isEmpty(account) || account.getIntergral()<credits){
            return null;
        }


        account.setIntergral(account.getIntergral()-credits);
        accountDao.save(account);

        DuibaOrder d = new DuibaOrder();
        d.setUid(uid);
        d.setCredits(credits);
        d.setAppKey(appkey);
        d.setTimestamp(timestamp);
        d.setOrderNum(orderNum);
        d.setType(type);
        d.setSign(sign);
        d.setItemCode(itemCode);
        d.setDescription(description);
        d.setFacePrice(facePrice);
        d.setWaitAudit(waitAudit);
        d.setParams(params);

        String bizId=uid+"-"+Util.getOrderNum("duiba");//返回开发者系统中的业务订单id

        d.setBizOrder(bizId);
        d.setResult("2");

        duibaOrderRepository.save(d);



        return bizId;
    }



    @Override
    public String duibaRst(String appKey, String timestamp, String orderNum, String sign, String success, String errorMessage, String bizId) {

        DuibaOrder duibaOrder = duibaOrderRepository.findByappKeyAndOrderNum(appKey,orderNum);

        if(isEmpty(duibaOrder)){
            return "fail";
        }
        if(duibaOrder.getResult().equals("2")){
            if(success.equals("true")){
                duibaOrder.setResult("1");
                duibaOrder.setStatus(0);
                duibaOrderRepository.save(duibaOrder);
                return "ok";
            }else{

                    Account account = accountRepository.findByUser(duibaOrder.getUid());
                    account.setIntergral(account.getIntergral()+duibaOrder.getCredits());
                    accountRepository.save(account);

                    duibaOrder.setStatus(0);
                    duibaOrder.setResult("0");
                    duibaOrderRepository.save(duibaOrder);


                return "ok";
            }
        }else{
            return "ok";
        }
    }

    //支付成功回调  处理提成信息
    @Override
    public Boolean payCallBackWork(String outNums, Double totalFee,String payType) throws BusinessException {

        //冲入检查
        riCheckEnd(outNums);
        //是否存在订单
        Order order = orderRepository.findByOrderNum(outNums,valid);
        if(isEmpty(order)){
            //订单不存在
            return false;
        }
        //金额校对
        Account account = accountRepository.findByUser(order.getUser().getId());
        //余额不足支付
        if(payType.equals(Const.PayType.BALANCE_PAY) && account.getBalance()+account.getRetateBalance()+account.getRedBalance() < totalFee){
            return false;
        }
        if(!payType.equals(Const.PayType.BALANCE_PAY) && totalFee<=order.getReceiptAmount()){
            return false;
        }
        //订单状态校对
        if(order.getTradeStatus()!=Const.TradeStatus.LAUNCH){
            return false;
        }
        //余额支付扣余额 设为已支付
        if(payType.equals(Const.PayType.BALANCE_PAY)){
            //余额够提现
            if(totalFee <= account.getBalance()){
                account.setBalance(account.getBalance()-totalFee);
            }else if(totalFee <= account.getBalance()+account.getRetateBalance()){
                Double balance = account.getBalance();
                account.setBalance(0.0);
                account.setRetateBalance(account.getRetateBalance()-(totalFee-balance));
            }else if(totalFee <= account.getBalance()+account.getRetateBalance()+account.getRedBalance()){
                Double balance = account.getBalance();
                Double retateBalance = account.getRetateBalance();

                account.setBalance(0.0);
                account.setRetateBalance(0.0);
                account.setRedBalance(account.getRedBalance()-(totalFee-balance-retateBalance));

            }else{
                //账户余额不足
                return false;
            }
            if(account.getBalance()<0 || account.getRedBalance()<0 || account.getRetateBalance()<0){
                //账户余额不足
                return false;
            }
        }
        //如果是未支付状态  设置为已支付
        order.setTradeStatus(Const.TradeStatus.PAY_SUCCESS);

        // 加积分
        account.setIntergral(account.getIntergral()+ (int)Math.floor(totalFee));

        //参与返利的额度
        Double rebateAmount = order.getRebateAmount();

        Merchant orderOwnerMct = order.getMerchant();

        //增加各种提成+记录
        //1.如果是余额支付 增加消费记录
        if(payType.equals(Const.PayType.BALANCE_PAY)){
            AccountRecord accountRecord = new AccountRecord();
            accountRecord.setUser(order.getUser());
            accountRecord.setMerchant(order.getMerchant());
            accountRecord.setOrder(order);
            accountRecord.setAmount(order.getTotalAmount());
            accountRecord.setAmountType(2);
            accountRecord.setRecordType(4);
            accountRecordRepository.save(accountRecord);
        }
        //2.自己的返利
        Double selfRebate = rebateAmount*Const.MerchantInitInfo.rebate*orderOwnerMct.getProfits();
        AccountRecord selfAccountRecord = new AccountRecord();
        selfAccountRecord.setUser(order.getUser());
        selfAccountRecord.setMerchant(order.getMerchant());
        selfAccountRecord.setOrder(order);
        selfAccountRecord.setAmount(selfRebate);
        selfAccountRecord.setAmountType(1);
        selfAccountRecord.setRecordType(1);
        accountRecordRepository.save(selfAccountRecord);
            //2.1 自己账户返利
            account.setRetateBalance(account.getRetateBalance()+selfRebate);
            accountRepository.save(account);


        //3.上级用户的提成
        User parent1 = userDao.findByIdAndStatus(order.getUser().getParent1(),valid);
        if(isNotEmpty(parent1)){
            Double p1Rebate = rebateAmount*Const.MerchantInitInfo.retateToParent1*orderOwnerMct.getProfits();

            AccountRecord p1AccountRecord = new AccountRecord();
            p1AccountRecord.setUser(parent1);
            p1AccountRecord.setMerchant(order.getMerchant());
            p1AccountRecord.setOrder(order);
            p1AccountRecord.setFans(order.getUser());
            p1AccountRecord.setAmount(p1Rebate);
            p1AccountRecord.setAmountType(1);
            p1AccountRecord.setRecordType(2);
            accountRecordRepository.save(p1AccountRecord);

            //3.1上级用户账户返利
            Account p1Account = accountDao.findByUser(parent1.getId());
            p1Account.setRetateBalance(p1Account.getRetateBalance()+p1Rebate);
            accountRepository.save(p1Account);
        }
        User parent2 = userDao.findByIdAndStatus(order.getUser().getParent2(),valid);
        if(isNotEmpty(parent2)){
            Double p2Rebate = rebateAmount*Const.MerchantInitInfo.retateToParent2*orderOwnerMct.getProfits();

            AccountRecord p2AccountRecord = new AccountRecord();
            p2AccountRecord.setUser(parent2);
            p2AccountRecord.setMerchant(order.getMerchant());
            p2AccountRecord.setOrder(order);
            p2AccountRecord.setFans(order.getUser());
            p2AccountRecord.setAmount(p2Rebate);
            p2AccountRecord.setAmountType(1);
            p2AccountRecord.setRecordType(2);
            accountRecordRepository.save(p2AccountRecord);

            //3.1上级用户账户返利
            Account p2Account = accountDao.findByUser(parent2.getId());
            p2Account.setRetateBalance(p2Account.getRetateBalance()+p2Rebate);
            accountRepository.save(p2Account);
        }
        //4.归属饭店 不是此消费饭店   饭店提成
        if(order.getMerchant().getId()!=order.getUser().getMyMerchant()){
            User mctOwner = userDao.findByIdAndStatus(order.getMerchant().getUid(),valid);
            if(isNotEmpty(mctOwner)){
                Double mctRebate = rebateAmount*Const.MerchantInitInfo.rebateToMerchant*orderOwnerMct.getProfits();

                MerchantAccountRecord actAccountRecord = new MerchantAccountRecord();
                actAccountRecord.setUser(mctOwner);
                actAccountRecord.setMerchant(order.getMerchant());
                actAccountRecord.setOrder(order);
                actAccountRecord.setFans(order.getUser());
                actAccountRecord.setAmount(mctRebate);
                actAccountRecord.setAmountType(1);
                actAccountRecord.setRecordType(2);
                merchantAccountRecordRepository.save(actAccountRecord);

                //4.1 增加饭店返利
                Account mctAccount = accountDao.findByUser(mctOwner.getId());
                mctAccount.setRetateBalance(mctAccount.getRetateBalance()+mctRebate);
                accountDao.save(mctAccount);
            }

        }
        //5.区代理提成
        AgentInfos agentInfos = agentInfosRepository.findByDistrictIdAndAgentLevel(order.getMerchant().getDistrict(),5);
        if(isNotEmpty(agentInfos) && isNotEmpty(agentInfos.getUser())){

            Double agtRebate = rebateAmount*Const.MerchantInitInfo.retateToDistrictAgent*orderOwnerMct.getProfits();

            AgentAccountRecord agtAccountRecord = new AgentAccountRecord();
            agtAccountRecord.setUser(agentInfos.getUser());
            agtAccountRecord.setMerchant(order.getMerchant());
            agtAccountRecord.setOrder(order);
            agtAccountRecord.setFans(order.getUser());
            agtAccountRecord.setAmount(agtRebate);
            agtAccountRecord.setAmountType(1);
            agtAccountRecord.setRecordType(2);

            agentAccountRecordRepository.save(agtAccountRecord);

            //5.1 增加代理返利
            Account agtAccount = accountDao.findByUser(agentInfos.getUser().getId());
            agtAccount.setRetateBalance(agtAccount.getRetateBalance()+agtRebate);
            accountDao.save(agtAccount);
        }






        riCheckEnd(outNums);
        return true;
    }
}
