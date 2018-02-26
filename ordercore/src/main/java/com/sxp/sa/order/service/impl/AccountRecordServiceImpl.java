package com.sxp.sa.order.service.impl;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.order.entity.AccountRecord;
import com.sxp.sa.order.repository.AccountRecordRepository;
import com.sxp.sa.order.service.AccountRecordService;
import com.sxp.sa.order.vo.AccountRecordVo;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sxp.sa.basic.constant.Const.Code.USER_NOT_EXISTS;
import static com.sxp.sa.basic.constant.Const.Code.USER_NOT_EXISTS_MSG;
import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.isEmpty;
import static com.sxp.sa.basic.utils.Util.p2pr;

@Service
public class AccountRecordServiceImpl extends BaseService implements AccountRecordService{


    @Autowired
    private AccountRecordRepository accountRecordRepository;

    @Autowired
    private UserRepository userDao;


    /**
     * 获取自己的账户记录
     * @param uid
     * @param recordType
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<AccountRecordVo> getMyAccountRecord(Long uid, Integer recordType, Long startTime, Long endTime, Pageable pageable) throws BusinessException {


        User user = userDao.findByIdAndStatus(uid,valid);
        //用户不存在
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }
        Page<AccountRecord> records ;

        if(0 == recordType){
            records = accountRecordRepository.getMyAllRecord(uid,startTime,endTime,pageable);
        }else{
            records = accountRecordRepository.getMyRecordByType(uid,startTime,endTime,recordType,pageable);
        }

        return p2pr(records,AccountRecordVo.class);
    }
}
