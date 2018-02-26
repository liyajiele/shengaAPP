package com.sxp.sa.user.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/2/18.
 */
public class SMSCodeVo extends BaseVo {


    @ApiModelProperty(value = "短信验证吗")
    private String code;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "验证码类型 1.注册 2.忘记密码 SMSCodeType")
    private String type;



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
}
