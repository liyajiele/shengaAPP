package com.sxp.sa.basic.service;


import com.sxp.sa.basic.entity.District;
import com.sxp.sa.basic.exception.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface DistrictService {

    /**
     * 查询市所有的区
     * @param name
     * @return
     */
    List<District> queryCityIdAllDistrict(String name) throws BusinessException;

    List<District> findDistrictByCityId(Integer cityId)throws BusinessException;

    Map<String,Object> getLocationInfo(Double longitude, Double latitude)throws BusinessException;
}
