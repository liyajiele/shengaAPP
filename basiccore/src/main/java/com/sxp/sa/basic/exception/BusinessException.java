package com.sxp.sa.basic.exception;


import com.sxp.sa.basic.utils.Rst;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by miss on 2015/10/20.
 */
public class BusinessException extends BaseException{
    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
    }

    public BusinessException(String errorMsg) {
        super(errorMsg);
    }

    public BusinessException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }
    public BusinessException(int errorCode, String errorMsg, RedisTemplate redisTemplate,String key){
        super(errorMsg);
        this.errorCode = errorCode;
        redisTemplate.delete(key);
    }

    public BusinessException(int errorCode, String errorMsg, Object dataResult) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }

    public BusinessException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

    public BusinessException(int errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(int errorCode, String errorMsg, Rst dataResult, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }

}
