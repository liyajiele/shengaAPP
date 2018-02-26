package com.sxp.sa.user.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.user.entity.SMSCode;
import com.sxp.sa.user.repository.SMSCodeRepository;
import com.sxp.sa.user.service.SMSCodeService;
import com.sxp.sa.user.vo.SMSCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sxp.sa.basic.constant.Const.Code.SMS_SEND_ERROR_MSG;

/**
 * Created by Administrator on 2017/2/18.
 */

@Service
public class SMSCodeServiceImpl extends BaseService implements SMSCodeService {


    //发送短信 client
    @Autowired
    private IAcsClient iAcsClient;

    @Autowired
    private SMSCodeRepository smsCodeDao;


    /**
     *
     * @param mobile
     * @return
     * @throws BusinessException
     */
    @Override
    public SMSCodeVo sendSms(String mobile, String smsType, String ip) throws BusinessException {
        //随机短信验证码
        String randomCode = Util.getRandomNumber(Const.SMSParams.SMS_LENGTH);

        try {
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(mobile);

            //必填:短信签名-可在短信控制台中找到
            request.setSignName(Const.SMSParams.SMS_SIGN_NAME);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(Const.SMSParams.SMS_TPL_NAME);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam("{\"code\":\""+randomCode+"\"}");//短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"


            SendSmsResponse sendSmsResponse = iAcsClient.getAcsResponse(request);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
            }else{
                throw new BusinessException(Const.Code.SMS_SEND_ERROR,SMS_SEND_ERROR_MSG);
            }


            SMSCode smsCode = new SMSCode(randomCode,mobile,smsType,ip);

            smsCodeDao.save(smsCode);
            //转换类型
            SMSCodeVo rst = BeanMapper.map(smsCodeDao.save(smsCode),SMSCodeVo.class);
            return rst;

        }catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
            throw new BusinessException(Const.Code.SMS_SEND_ERROR,SMS_SEND_ERROR_MSG);
        }


    }

    /**
     * 删除使用过短信验证
     * @param smsCode
     * @throws BusinessException
     */
    @Override
    public void deleteSms(SMSCode smsCode) throws BusinessException {
        smsCode.setStatus(3);

        smsCodeDao.save(smsCode);
    }
}
