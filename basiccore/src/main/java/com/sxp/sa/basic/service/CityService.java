package com.sxp.sa.basic.service;


import com.sxp.sa.basic.entity.City;
import com.sxp.sa.basic.exception.BusinessException;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface CityService {

    /**
     * 查询省所有的城市
     * @param name
     * @return
     */
    List<City> queryProvinceIdAllCity(String name) throws BusinessException;

    List<City> findAll()throws  BusinessException;

    List<City> findCityListByProvinceId(Integer provinceId)throws BusinessException;
}
