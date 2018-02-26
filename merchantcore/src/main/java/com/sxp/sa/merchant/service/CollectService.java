package com.sxp.sa.merchant.service;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.merchant.vo.MerchantVo;
import org.springframework.data.domain.Pageable;


public interface CollectService {

    Integer queryMyCollectNum(Long uid)throws BusinessException;

    Pager<MerchantVo> queryMyCollects(Long uid, Pageable pageable)throws BusinessException;

    MerchantVo collectOrCancel(Long uid,Long mid,Integer type)throws BusinessException;
}
