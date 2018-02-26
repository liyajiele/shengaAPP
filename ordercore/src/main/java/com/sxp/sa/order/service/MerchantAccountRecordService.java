package com.sxp.sa.order.service;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.order.vo.AccountRecordVo;
import org.springframework.data.domain.Pageable;

public interface MerchantAccountRecordService {

    Pager<AccountRecordVo> getMyAccountRecord(Long mid, Integer recordType, Long startTime, Long endTime, Pageable pageable)throws BusinessException;
}
