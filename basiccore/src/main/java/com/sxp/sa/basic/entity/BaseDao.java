package com.sxp.sa.basic.entity;

import com.sxp.sa.basic.constant.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Created by miss on 2017/2/16.
 */
public class BaseDao {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;


    /**
     * 查询缓存中是否有该key
     * @param key
     * @return
     */
    public boolean check(String key){
        Boolean hasCache = template.hasKey(key);
        if(hasCache && Const.OPNE_REDIS_CACHE.equals("1")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 添加缓存
     * @param key
     * @param cacheObj
     */
    public void cache(String key ,Object cacheObj){
        if(Const.OPNE_REDIS_CACHE.equals("1")){
            ValueOperations<String,Object> valueOps = template.opsForValue();
            valueOps.set(key,cacheObj);
        }
    }

    /**
     * 获取缓存的对象
     * @param key
     * @return
     */
    public Object getCache(String key){
        return template.opsForValue().get(key);
    }
}
