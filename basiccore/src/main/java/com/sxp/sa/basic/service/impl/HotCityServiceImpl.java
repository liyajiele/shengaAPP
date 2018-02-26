package com.sxp.sa.basic.service.impl;

import com.sxp.sa.basic.entity.HotCity;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.repository.HotCityRepository;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.service.HotCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
@Service
public class HotCityServiceImpl  extends BaseService implements HotCityService {


    @Autowired
    private HotCityRepository hotCityDao;


    /**
     * 查询所有热门城市
     * @return
     */
    @Override
    public List<HotCity> queryAllHotCity()throws BusinessException {
        return hotCityDao.findAllHotCity();
    }
}
