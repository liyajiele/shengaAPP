package com.sxp.sa.basic.service.impl;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.entity.City;
import com.sxp.sa.basic.entity.District;
import com.sxp.sa.basic.entity.Province;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.repository.CityRepository;
import com.sxp.sa.basic.repository.DistrictRepository;
import com.sxp.sa.basic.repository.ProvinceRepository;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.service.DistrictService;
import com.sxp.sa.basic.utils.BaiduLngLonUtil;
import com.sxp.sa.basic.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sxp.sa.basic.constant.Const.Code.CITY_NOT_EXISTS_MSG;

/**
 * Created by Administrator on 2017/3/7.
 */
@Service
public class DistrictServiceImpl extends BaseService implements DistrictService {


    @Autowired
    private DistrictRepository districtDao;

    @Autowired
    private CityRepository cityDao;

    @Autowired
    private ProvinceRepository provinceRepository;
    /**
     * 查询市所有的区
     * @param name
     * @return
     */
    @Override
    public List<District> queryCityIdAllDistrict(String name) throws BusinessException {
        City city=cityDao.findByName(name);
        if(Util.isEmpty(city)){
            throw new BusinessException(Const.Code.CITY_NOT_EXISTS,CITY_NOT_EXISTS_MSG);
        }
        Integer cityId=city.gettId();
        return districtDao.findByCityIdAllDistrict(cityId);
    }

    @Override
    public List<District> findDistrictByCityId(Integer cityId) throws BusinessException {
        return districtDao.findByCityIdAllDistrict(cityId);
    }


    /**
     * 获取地址信息
     * @param longitude
     * @param latitude
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> getLocationInfo(Double longitude, Double latitude) throws BusinessException {


        Map<String,String> locationsInfosMap1 = BaiduLngLonUtil.getLocationInfo(longitude,latitude);


        Map<String,Object> rst = new HashMap<String,Object>();


        Province province = provinceRepository.findByName(locationsInfosMap1.get("province"));
        rst.put("province",province);

        City city = cityDao.findByName(locationsInfosMap1.get("city"));
        rst.put("city",city);


        District district = districtDao.findByNameAndCityId(locationsInfosMap1.get("district"),city.gettId());
        rst.put("district",district);


        return rst;
    }
}
