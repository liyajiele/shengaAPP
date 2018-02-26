package com.sxp.sa.user.service.impl;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.user.entity.Account;
import com.sxp.sa.user.entity.Draw;
import com.sxp.sa.user.entity.DrawChannel;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.AccountRepository;
import com.sxp.sa.user.repository.DrawChannelRepository;
import com.sxp.sa.user.repository.DrawRepository;
import com.sxp.sa.user.repository.UserRepository;
import com.sxp.sa.user.service.DrawService;
import com.sxp.sa.user.vo.DrawVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.sxp.sa.basic.constant.Const.Code.*;
import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.isEmpty;
import static com.sxp.sa.basic.utils.Util.isNotEmpty;


@Service
public class DrawServiceImpl extends BaseService implements DrawService {



    @Autowired
    private UserRepository userDao;

    @Autowired
    private AccountRepository accountDao;

    @Autowired
    private DrawRepository drawDao;

    @Autowired
    private DrawChannelRepository drawChannelDao;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public DrawChannel getDrawChannel(Long uid, String alipay, String concatUser) throws BusinessException {

        DrawChannel drawChannel = drawChannelDao.findByUserAndAlipayUsernameAndNick(uid,alipay,concatUser);

        if(isEmpty(drawChannel)){
            drawChannel = new DrawChannel();
            drawChannel.setUser(userDao.findByIdAndStatus(uid,valid));
            drawChannel.setAliPay(alipay);
            drawChannel.setConcatUser(concatUser);
            drawChannel.setChannelType(Const.DrawChannelType.ALI_PAY);

            drawChannel = drawChannelDao.save(drawChannel);

        }

        return drawChannel;
    }

    /**
     * 申请提现
     *
     * @param
     * @return
     */
    @Transactional
    @Override
    public DrawVo appylForDraw(Long uid, Long drawCId, Double drawNum) throws BusinessException {
        //1.先看用户账户余额 够不够
        //添加，对账户金额操作
        //以上封装成一个方法  写一个公用的服务，调用服务 －余额，加冻结资金
        //需要参数，Account id 和 金额
        //2.够生成draw
        //3.生成Order
        //4.保存
        riCheckStart("appylForDraw" + uid );




        DrawChannel drawChannel = drawChannelDao.findById(drawCId);
        if (Util.isEmpty(drawChannel)) {
            throw new BusinessException(Const.Code.DRAW_CHANNEL_NOT_EXISTS, "提现地址不存在", redisTemplate, "appylForDraw" + uid );
        }
        User user = userDao.findByIdAndStatus(uid,valid);
        if (Util.isEmpty(user)) {
            throw new BusinessException(Const.Code.USER_NOT_EXISTS, "用户不存在", redisTemplate, "appylForDraw" + uid );
        }
        Account account = accountDao.findByUser(uid);

        //判断提现次数
        if(isNotEmpty(account.getCanDrawNum()) && account.getCanDrawNum()<1){
            throw new BusinessException(CAN_DRAW_NUMS_LESS,CAN_DRAW_NUMS_LESS_MSG,"appylForDraw" + uid );
        }

        //余额够提现
        if(drawNum <= account.getBalance()){
            account.setBalance(account.getBalance()-drawNum);
            account.setFrozenBalance(account.getFrozenBalance()+drawNum);
        }else if(drawNum <= account.getBalance()+account.getRetateBalance()){
            Double balance = account.getBalance();
            account.setBalance(0.0);
            account.setRetateBalance(account.getRetateBalance()-(drawNum-balance));
            account.setFrozenBalance(balance);
            account.setFrozenRetateBalance(drawNum-balance);
        }else if(drawNum <= account.getBalance()+account.getRetateBalance()+account.getRedBalance()){
            Double balance = account.getBalance();
            Double retateBalance = account.getRetateBalance();

            account.setBalance(0.0);
            account.setRetateBalance(0.0);
            account.setRedBalance(account.getRedBalance()-(drawNum-balance-retateBalance));

            account.setFrozenBalance(balance);
            account.setFrozenRetateBalance(retateBalance);
            account.setFrozenRedBlc(drawNum-balance-retateBalance);
        }else{
            //账户余额不足
            throw new BusinessException(DRAW_NUM_ERROR,DRAW_NUM_ERROR_MSG,"appylForDraw" + uid );
        }
        if(account.getBalance()<0 || account.getRedBalance()<0 || account.getRetateBalance()<0){
            //账户余额不足
            throw new BusinessException(DRAW_NUM_ERROR,DRAW_NUM_ERROR_MSG,"appylForDraw" + uid );
        }

        //减少提现次数
        account.setCanDrawNum(account.getCanGetRebNum()-1);
        accountDao.save(account);
        String drawOrderNum = Util.getOrderNum("TX");//下面生成Order需要复用

        Draw draw = new Draw();
        draw.setUser(user);
        draw.setDrawNum(drawNum);
        draw.setDrawOrderNum(drawOrderNum);
        draw.setDescr("您提现了" + drawNum + "元");
        draw.setDrawStatus(Const.DrawStatus.LAUNCH);
        draw.setDrawChannel(drawChannel);
        draw = drawDao.save(draw);

        //返回处理
        DrawVo drawVo = BeanMapper.map(draw, DrawVo.class);
        riCheckEnd("appylForDraw" + uid );
        return drawVo;
    }

    /**
     * 分页查询提现列表信息
     *
     * @param uid
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<DrawVo> findDrawByPage(Long uid, Pageable pageable) throws BusinessException {
        User user = userDao.findByIdAndStatus(uid,valid);

        if (Util.isEmpty(user)) {
            throw new BusinessException(Const.Code.USER_NOT_EXISTS, "用户不存在");
        }

        Page<Draw> draws = drawDao.findByUserAndPage(user, pageable);


        Pager<DrawVo> drawVoPager = Util.p2pr(draws, DrawVo.class);

        return drawVoPager;
    }

    /**
     * 管理员分页查询所有的提成信息
     *
     * @param uid
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<DrawVo> adminQueryAllDrawByPage(Long uid, String startTime, String endTime, String scope, Pageable pageable) throws BusinessException {
        User user = userDao.findByIdAndStatus(uid,valid);
        if (Util.isEmpty(user)) {
            throw new BusinessException(Const.Code.USER_NOT_EXISTS, "用户不存在");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = sdf.parse(startTime);
            end = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long startL = start.getTime();

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(end);
        calendarEnd.add(Calendar.DAY_OF_YEAR, 1);

        Long endL = calendarEnd.getTimeInMillis() - 1 * 1000;//转换结束的时间

        Page<Draw> drawPage = null;

        drawPage = drawDao.adminFindAllDrawByPage(startL, endL, scope, pageable);


        Pager<DrawVo> drawVoPager = Util.p2pr(drawPage, DrawVo.class);

        return drawVoPager;
    }


    /**
     * 管理员批准 提成
     *
     * @param uid
     * @param did
     * @param directive
     * @return
     * @throws BusinessException
     */
    @Transactional
    @Override
    public DrawVo adminRatifyDraw(Long uid, Long did, String directive, String rejectDescr) throws BusinessException {

        riCheckStart("adminRatifyDraw" + uid + did.toString());//反重入检查

        User user = userDao.findByIdAndStatus(uid,valid);
        if (Util.isEmpty(user)) {
            riCheckEnd("adminRatifyDraw" + uid + did.toString());
            throw new BusinessException(Const.Code.USER_NOT_EXISTS, "用户不存在", redisTemplate, did.toString());
        }
        Draw draw = drawDao.findById(did);
        if (Util.isEmpty(draw)) {
            riCheckEnd("adminRatifyDraw" + uid + did.toString());
            throw new BusinessException(Const.Code.DRAW_NOT_EXISTS, DRAW_NOT_EXISTS_MSG);
        }
        Account account = accountDao.findByUser(uid);
        DrawChannel drawChannel = draw.getDrawChannel();
        if (Util.isEmpty(drawChannel)) {
            riCheckEnd("adminRatifyDraw" + uid + did.toString());
            throw new RuntimeException("提现地址为空，数据库缺少");
        }
        if (Util.isEmpty(draw)) {
            riCheckEnd("adminRatifyDraw" + uid + did.toString());
            throw new BusinessException(Const.Code.DRAW_NOT_EXISTS, "该订单不存在", redisTemplate, did.toString());
        }


        Double drawNum = draw.getDrawNum();




        if (Const.AdminRatifyDraw.ADMIN_AGREE.equals(directive)) {//管理员同意
            //余额够提现
            if(drawNum <= account.getFrozenBalance()){
                account.setFrozenBalance(account.getFrozenBalance()-drawNum);
            }else if(drawNum <= account.getFrozenBalance()+account.getFrozenRetateBalance()){
                Double balance = account.getFrozenBalance();
                account.setFrozenBalance(0.0);
                account.setFrozenRetateBalance(account.getFrozenRetateBalance()-(drawNum-balance));

            }else if(drawNum <= account.getFrozenBalance()+account.getFrozenRetateBalance()+account.getFrozenRedBlc()){
                Double balance = account.getFrozenBalance();
                Double retateBalance = account.getFrozenRetateBalance();

                account.setFrozenBalance(0.0);
                account.setFrozenRetateBalance(0.0);
                account.setFrozenRedBlc(account.getFrozenRedBlc()-(drawNum-balance-retateBalance));
            }else{
                //账户余额不足
                throw new BusinessException(DRAW_NUM_ERROR,DRAW_NUM_ERROR_MSG);
            }



            draw.setDrawStatus(Const.DrawStatus.ADMIN_AGREE_SUCCESS);
            draw.setRejectDescr("同意");
        } else if (Const.AdminRatifyDraw.ADMIN_REJECT.equals(directive)) {
            //将关于这笔 提现   冻结的 加到 用户余额上面去

           if(drawNum<=account.getFrozenBalance()){
               account.setFrozenBalance(account.getFrozenBalance()-drawNum);
               account.setBalance(account.getBalance()+drawNum);
           }else if(drawNum <= account.getFrozenBalance()+account.getFrozenRetateBalance()){
               Double balance = account.getFrozenBalance();
               account.setFrozenBalance(0.0);
               account.setFrozenRetateBalance(account.getFrozenRetateBalance()-(drawNum-balance));

               account.setBalance(account.getBalance()+balance);
               account.setRetateBalance(account.getRetateBalance()+(drawNum-balance));
           }else if(drawNum <= account.getFrozenBalance()+account.getFrozenRetateBalance()+account.getFrozenRedBlc()){
               Double balance = account.getFrozenBalance();
               Double retateBalance = account.getFrozenRetateBalance();

               account.setFrozenBalance(0.0);
               account.setFrozenRetateBalance(0.0);
               account.setFrozenRedBlc(account.getFrozenRedBlc()-(drawNum-balance-retateBalance));

               account.setBalance(account.getBalance()+balance);
               account.setRetateBalance(account.getRetateBalance()+retateBalance);
               account.setRedBalance(account.getRedBalance()+(drawNum-balance-retateBalance));

           }else{
               //账户余额不足
               throw new BusinessException(DRAW_NUM_ERROR,DRAW_NUM_ERROR_MSG);
           }



            draw.setDrawStatus(Const.DrawStatus.ADMIN_REJECT);
            if (Util.isEmpty(rejectDescr)) {
                rejectDescr = "拒绝";
            }
            draw.setRejectDescr(rejectDescr);
        }

        account = accountDao.save(account);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm SS");
        String gmtdealTime = sdf.format(date);
        draw.setGmtDealTime(gmtdealTime);
        draw = drawDao.save(draw);

        DrawVo drawVo = BeanMapper.map(draw, DrawVo.class);
        riCheckEnd("adminRatifyDraw" + uid + did.toString());
        return drawVo;
    }

    /**
     * 根据提现id ，查询提现信息
     *
     * @param did
     * @return
     * @throws BusinessException
     */
    @Override
    public DrawVo queryDrawById(Long uid, Long did) throws BusinessException {
        User user = userDao.findByIdAndStatus(uid,valid);
        if (Util.isEmpty(user)) {
            throw new BusinessException(Const.Code.USER_NOT_EXISTS, "用户不存在");
        }
        Draw draw = drawDao.findById(did);
        if (Util.isEmpty(draw)) {
            throw new BusinessException(Const.Code.DRAW_NOT_EXISTS, "该提现不存在");
        }
        DrawVo drawVo = BeanMapper.map(draw, DrawVo.class);
        return drawVo;
    }


}
