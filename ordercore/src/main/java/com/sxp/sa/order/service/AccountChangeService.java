package com.sxp.sa.order.service;

import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.order.vo.RBAccountRecordVo;

public interface AccountChangeService {

    RBAccountRecordVo getRedBag(Long uid)throws BusinessException;
}
