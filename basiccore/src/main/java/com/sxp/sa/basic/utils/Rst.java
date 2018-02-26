package com.sxp.sa.basic.utils;


import com.sxp.sa.basic.constant.Const;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by miss on 2015/10/19.
 */
public class Rst<T> {

    @ApiModelProperty(value = "code : 返回代码，1表示OK，其它的都有对应问题")
    private int code= Const.Code.SUCCESS;



    @ApiModelProperty(value = "message : 如果code!=1,错误信息")
    private String message=Const.Message.SUCCESS;

    @ApiModelProperty(value = "code为1时返回结果集")
    private T object=(T) new Object();


    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getObject() {
        return object;
    }
    public void setObject(T object) {
        this.object = object;
    }

    public Rst(int code ,String errorMsg){
        this.message = errorMsg;
        this.code = code;
        this.object=(T) new Object();
    }
    public Rst(String errorMsg){
        this.message = errorMsg;
        this.code = Const.Code.ERROR;
        this.object=(T) new Object();
    }

    public Rst(){
        this.object=(T) new Object();
    }

    public void success(T object){
        this.code = Const.Code.SUCCESS;
        this.message = Const.Message.SUCCESS;
        this.object = object;
    }
    public void error(){
        this.code = Const.Code.ERROR;
        this.message = Const.Message.ERROR;
        this.object=(T) new Object();
    }
    public void error(String message){
        this.code = Const.Code.ERROR;
        this.message = message;
        this.object=(T) new Object();
    }
}
