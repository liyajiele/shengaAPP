package com.sxp.sa.basic.service;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class BaseService {

    @Autowired
    protected RedisTemplate redisTemplate;



    /**
     * 重入检查开始
     * @param obj
     * @throws BusinessException
     */
    public void riCheckStart(String obj)throws BusinessException {
        if(Util.isNotEmpty(redisTemplate.opsForValue().get(obj))){
            throw new BusinessException(Const.Code.ERROR,"请不要重复操作");
        }
        redisTemplate.opsForValue().set(obj,"1");
    }

    public void riCheckEnd(String obj){
        redisTemplate.delete(obj);
    }

}
