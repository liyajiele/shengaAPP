package com.sxp.sa.basic.service;


import com.sxp.sa.basic.entity.Province;
import com.sxp.sa.basic.exception.BusinessException;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface ProvinceService {

    /**
     * 查询区域所有的省
     * @param areaName
     * @return
     */
    List<Province> queryAreaIdAllProvince(String areaName) throws BusinessException;

    List<Province> queryAllProvince()throws BusinessException;
}
