package com.sxp.sa.merchant.service;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.merchant.vo.NewsInfoVo;
import org.springframework.data.domain.Pageable;

public interface NewsInfoService {


    Pager<NewsInfoVo> findByPage(Pageable pageable)throws BusinessException;

    Pager<NewsInfoVo> getRedBagInfos(Pageable pageable)throws BusinessException;
}
