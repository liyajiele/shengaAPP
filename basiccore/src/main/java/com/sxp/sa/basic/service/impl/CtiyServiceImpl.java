package com.sxp.sa.basic.service.impl;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.entity.City;
import com.sxp.sa.basic.entity.Province;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.repository.CityRepository;
import com.sxp.sa.basic.repository.ProvinceRepository;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.service.CityService;
import com.sxp.sa.basic.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sxp.sa.basic.constant.Const.Code.PROVINCE_NOT_EXISTS_MSG;

/**
 * Created by Administrator on 2017/3/7.
 */
@Service
public class CtiyServiceImpl extends BaseService implements CityService {


    @Autowired
    private CityRepository cityDao;

    @Autowired
    private ProvinceRepository provinceDao;
    /**
     * 查询省所有的城市
     * @param name
     * @return
     */
    @Override
    public List<City> queryProvinceIdAllCity(String name) throws BusinessException {

        Province province=provinceDao.findByName(name);
        if(Util.isEmpty(province)){
            throw new BusinessException(Const.Code.PROVINCE_NOT_EXISTS,PROVINCE_NOT_EXISTS_MSG);
        }
        Integer provinceId=province.gettId();
        return cityDao.findByProvinceIdAllCity(provinceId);
    }

    /**
     * 查询所有城市
     * @return
     * @throws BusinessException
     */
    @Override
    public List<City> findAll() throws BusinessException {
        return cityDao.findAll();
    }

    /**
     * 根据省id 获取城市
     * @param provinceId
     * @return
     * @throws BusinessException
     */
    @Override
    public List<City> findCityListByProvinceId(Integer provinceId) throws BusinessException {
        return cityDao.findByProvinceIdAllCity(provinceId);
    }
}
