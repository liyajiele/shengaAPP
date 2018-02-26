package com.sxp.sa.user.service;

import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.user.vo.AccountVo;

/**
 * Created by dell on 2017/7/18.
 */
public interface AccountService {

    AccountVo getAccountInfo(Long uid)throws BusinessException;



}
