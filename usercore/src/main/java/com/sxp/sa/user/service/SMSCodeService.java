package com.sxp.sa.user.service;


import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.user.entity.SMSCode;
import com.sxp.sa.user.vo.SMSCodeVo;

/**
 * Created by Administrator on 2017/2/18.
 */
public interface SMSCodeService {

    SMSCodeVo sendSms(String mobile, String smsType, String ip)throws BusinessException;

    /**
     * 删除使用过的短信验证
     * @param smsCode
     * @throws BusinessException
     */
    void deleteSms(SMSCode smsCode) throws BusinessException;
}
