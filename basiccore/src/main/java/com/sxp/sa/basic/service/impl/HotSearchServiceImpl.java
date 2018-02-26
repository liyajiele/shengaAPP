package com.sxp.sa.basic.service.impl;


import com.sxp.sa.basic.entity.HotSearch;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.repository.HotSearchRepository;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.service.HotSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sxp.sa.basic.utils.Util.p2pr;


@Service
public class HotSearchServiceImpl extends BaseService implements HotSearchService{

    @Autowired
    private HotSearchRepository hotSearchRepository;


    @Override
    public Pager<HotSearch> nearHotSearch(Integer cityId, Integer type, Pageable pageable) throws BusinessException {
        Page<HotSearch> hs ;
        if(type==1){
           hs = hotSearchRepository.findCityHotSearch(cityId,pageable);
        }else{
            hs = hotSearchRepository.findDistrictHotSearch(cityId,pageable);
        }
        return p2pr(hs,HotSearch.class);
    }
}
