package com.sxp.sa.basic.service;

import com.sxp.sa.basic.entity.HotSearch;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import org.springframework.data.domain.Pageable;

public interface HotSearchService {

    Pager<HotSearch> nearHotSearch(Integer cdId, Integer type , Pageable pageable)throws BusinessException;
}
