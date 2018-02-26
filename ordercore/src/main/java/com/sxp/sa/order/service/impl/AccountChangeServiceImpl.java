package com.sxp.sa.order.service.impl;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.merchant.entity.NewsInfo;
import com.sxp.sa.merchant.repository.NewsInfoRepository;
import com.sxp.sa.order.entity.AccountRecord;
import com.sxp.sa.order.entity.RedBagPool;
import com.sxp.sa.order.repository.AccountRecordRepository;
import com.sxp.sa.order.repository.RedBagPoolRepository;
import com.sxp.sa.order.service.AccountChangeService;
import com.sxp.sa.order.vo.RBAccountRecordVo;
import com.sxp.sa.user.entity.Account;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.AccountRepository;
import com.sxp.sa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sxp.sa.basic.constant.Const.Code.*;
import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.isEmpty;

@Service
public class AccountChangeServiceImpl extends BaseService implements AccountChangeService{

    @Autowired
    private UserRepository userDao;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRecordRepository accountRecordRepository;

    @Autowired
    private RedBagPoolRepository redBagPoolRepositoryl;

    @Autowired
    private NewsInfoRepository newsInfoRepository;

    /**
     * 获取红包
     * @param uid
     * @return
     * @throws BusinessException
     */
    @Override
    public RBAccountRecordVo getRedBag(Long uid) throws BusinessException {

        User user = userDao.findByIdAndStatus(uid,valid);



        if(isEmpty(user)){
            throw new BusinessException(Const.Code.USER_NOT_EXISTS,Const.Code.USER_NOT_EXISTS_MSG);
        }
        Account account = accountRepository.findByUser(uid);
        //账户异常
        if(isEmpty(account)){
            throw new BusinessException(Const.Code.ACCOUNT_ERROR,ACCOUNT_ERROR_MSG);
        }
        if(account.getCanGetRebNum()<=0){
            throw new BusinessException(ACCOUNT_GET_RB_NUMS_LOW,ACCOUNT_GET_RB_NUMS_LOW_MSG);
        }
        //获取当天的红包池
        List<RedBagPool> redList = redBagPoolRepositoryl.findPool(valid);
        if(isEmpty(redList) || redList.size()==0 || redList.size()>1){
            throw new BusinessException(RED_POOL_EXCEPTION,RED_POOL_EXCEPTION_MSG);
        }
        RedBagPool pool = redList.get(0);
        //如果余额不足
        if(pool.getBalance() <=0d){
            throw new BusinessException(RED_POOL_EMPTY,RED_POOL_EMPTY_MSG);
        }
        Double redBag = Math.random()*(pool.getMaxSingleBag()-pool.getMinSingleBag())+pool.getMinSingleBag();
        //如果超过池中余额
        if(redBag>pool.getBalance()){
            redBag = pool.getBalance();
        }

        //账户增加redBalance
        if(isEmpty(account.getRedBalance())){
            account.setRedBalance(0d);
        }
        account.setRedBalance(account.getRedBalance()+redBag);

        //减少可抢红包次数
        account.setCanGetRebNum(account.getCanGetRebNum()-1);

        accountRepository.save(account);


        //生成AccountRecord
        AccountRecord ar = new AccountRecord();
        ar.setUser(user);
        ar.setAmount(redBag);
        ar.setAmountType(1);
        ar.setRecordType(3); //红包
        ar = accountRecordRepository.save(ar);

        //减少红包池
        pool.setBalance(pool.getBalance()-redBag);
        redBagPoolRepositoryl.save(pool);


        //生成NewsInfo
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setNewsType(1);
        newsInfo.setContent(user.getNickname()+" 抢到大红包一个");
        newsInfo.setNewsTypeDesc("红包");

        newsInfoRepository.save(newsInfo);


        return BeanMapper.map(ar,RBAccountRecordVo.class);
    }
}
