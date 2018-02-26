package com.sxp.sa.api.controller;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.exception.DatabaseException;
import com.sxp.sa.basic.exception.ValidationException;
import com.sxp.sa.basic.utils.Rst;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by miss on 2015/10/20.
 */


@ControllerAdvice
public class MyControllerAdvice {

    private static final Logger LOGGER = Logger.getLogger(MyControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    Rst handleUncaughtException(Exception ex) {			//系统异常
        LOGGER.error(ex.getMessage(), ex.getCause());
        return new Rst(Const.Code.ERROR,ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public @ResponseBody
    Rst handleValidationException(ValidationException validationEx) {		//数据校验异常
        LOGGER.error(validationEx.getMessage(), validationEx.getCause());
        return new Rst(Const.Code.ERROR,validationEx.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public @ResponseBody
    Rst handleBusinessException(BusinessException businessEx) {	//业务逻辑异常
        LOGGER.error(businessEx.getMessage(), businessEx.getCause());
        return new Rst(businessEx.getErrorCode(),businessEx.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    public @ResponseBody
    Rst handleValidationException(DatabaseException dbEx) {		//数据库操作异常
        LOGGER.error(dbEx.getMessage(), dbEx.getCause());
        return new Rst(Const.Code.ERROR,dbEx.getMessage());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public @ResponseBody
    Rst handleJSONConvertException(HttpMessageNotWritableException jsonEx) {	//JSON格式转换异常
        LOGGER.error(jsonEx.getMessage(), jsonEx.getCause());
        return new Rst(Const.Code.ERROR,"JSON格式转换异常");
    }


    @ExceptionHandler(UnauthorizedException.class)
    public @ResponseBody
    Rst handleUnauthorizedException(UnauthorizedException rtex) {	//授权异常
        LOGGER.error(rtex.getMessage(), rtex.getCause());
        rtex.printStackTrace();
        return new Rst(Const.Code.UNAUTHORIZED,"权限不足");
    }

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    Rst handleRuntimeException(RuntimeException rtex) {	//JSON格式转换异常
        LOGGER.error(rtex.getMessage(), rtex.getCause());
        rtex.printStackTrace();
        return new Rst(Const.Code.RUNTIME_EXCEPTION,"内部异常");
    }
}
