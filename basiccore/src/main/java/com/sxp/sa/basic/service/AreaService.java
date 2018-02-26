package com.sxp.sa.basic.service;


import com.sxp.sa.basic.entity.Area;
import com.sxp.sa.basic.exception.BusinessException;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface AreaService {

    /**
     * 查询所有的区域
     * @return
     */
    List<Area> queryAllArea() throws BusinessException;
}
