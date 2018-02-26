package com.sxp.sa.basic.service.impl;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.entity.Area;
import com.sxp.sa.basic.entity.Province;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.repository.AreaRepository;
import com.sxp.sa.basic.repository.ProvinceRepository;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.service.ProvinceService;
import com.sxp.sa.basic.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sxp.sa.basic.constant.Const.Code.AREA_NOT_EXISTS_MSG;

/**
 * Created by Administrator on 2017/3/7.
 */
@Service
public class ProvinceServiceImpl  extends BaseService implements ProvinceService {


    @Autowired
    private ProvinceRepository provinceDao;

    @Autowired
    private AreaRepository areaDao;


    /**
     * 查询区域所有的省
     * @param areaName
     * @return
     */
    @Override
    public List<Province> queryAreaIdAllProvince(String areaName) throws BusinessException {
        Area area=areaDao.findByName(areaName);
        if(Util.isEmpty(area)){
            throw new BusinessException(Const.Code.AREA_NOT_EXISTS,AREA_NOT_EXISTS_MSG);
        }
        Integer areaId=area.gettId();
        return provinceDao.findByAreaIdAllProvince(areaId);

    }


    /**
     * 查询所有省
     * @return
     * @throws BusinessException
     */
    @Override
    public List<Province> queryAllProvince() throws BusinessException {

        return provinceDao.findAll();
    }
}
