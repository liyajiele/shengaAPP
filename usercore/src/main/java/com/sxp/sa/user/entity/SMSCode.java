package com.sxp.sa.user.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by miss on 2015/12/2.
 */
@Entity
@Table(name = "tb_u_smscode")
@ApiModel(value = "短信验证码")
public class SMSCode extends BaseEntity {

    @ApiModelProperty(value = "短信验证吗")
    private String code;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "验证码类型 1.注册 2.忘记密码 SMSCodeType")
    private String type;

    @ApiModelProperty(value = "IP地址")
    private String ip;


    public SMSCode() {
    }

    public SMSCode(String code, String mobile, String type, String ip) {
        this.code = code;
        this.mobile = mobile;
        this.type = type;
        this.ip = ip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
