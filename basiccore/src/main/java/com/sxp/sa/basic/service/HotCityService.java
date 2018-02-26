package com.sxp.sa.basic.service;


import com.sxp.sa.basic.entity.HotCity;
import com.sxp.sa.basic.exception.BusinessException;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface HotCityService {

    /**
     * 查询所有热门城市
     * @return
     */
    List<HotCity> queryAllHotCity()throws BusinessException;
}
