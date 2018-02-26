package com.sxp.sa.merchant.service;

import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.merchant.entity.About;

public interface AboutService {

    About findByOSType(Integer osType)throws BusinessException;
}
