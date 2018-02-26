package com.sxp.sa.order.service.impl;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.order.entity.MerchantAccountRecord;
import com.sxp.sa.order.repository.MerchantAccountRecordRepository;
import com.sxp.sa.order.service.MerchantAccountRecordService;
import com.sxp.sa.order.vo.AccountRecordVo;
import com.sxp.sa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sxp.sa.basic.utils.Util.p2pr;

@Service
public class MerchantAccountRecordServiceImpl extends BaseService implements MerchantAccountRecordService {


    @Autowired
    private MerchantAccountRecordRepository accoutRecordRepository;

    @Autowired
    private UserRepository userDao;


    /**
     * 获取自己的账户记录(商户)
     * @param mid
     * @param recordType
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<AccountRecordVo> getMyAccountRecord(Long mid, Integer recordType, Long startTime, Long endTime, Pageable pageable) throws BusinessException {


        Page<MerchantAccountRecord> records ;

        if(0 == recordType){
            records = accoutRecordRepository.getMyAllRecord(mid,startTime,endTime,pageable);
        }else{
            records = accoutRecordRepository.getMyRecordByType(mid,startTime,endTime,recordType,pageable);
        }

        return p2pr(records,AccountRecordVo.class);
    }
}
