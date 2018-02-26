package com.sxp.sa.basic.service.impl;

import com.sxp.sa.basic.entity.Area;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.repository.AreaRepository;
import com.sxp.sa.basic.service.AreaService;
import com.sxp.sa.basic.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
@Service
public class AreaServiceImpl extends BaseService implements AreaService {

    @Autowired
    private AreaRepository areaDao;
    /**
     * 查询所有的区域
     * @return
     */
    @Override
    public List<Area> queryAllArea() throws BusinessException {
        return areaDao.findAllArea();
    }
}
