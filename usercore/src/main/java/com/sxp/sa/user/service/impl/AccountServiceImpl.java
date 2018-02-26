package com.sxp.sa.user.service.impl;

import com.aliyun.oss.OSSClient;
import com.github.sd4324530.fastweixin.api.QrcodeAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.enums.QrcodeType;
import com.github.sd4324530.fastweixin.api.response.QrcodeResponse;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.user.entity.Account;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.AccountRepository;
import com.sxp.sa.user.repository.UserRepository;
import com.sxp.sa.user.service.AccountService;
import com.sxp.sa.user.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.isEmpty;

/**
 * Created by dell on 2017/7/18.
 */
@Service
public class AccountServiceImpl extends BaseService implements AccountService {

    @Autowired
    private AccountRepository accountDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiConfig userApiConfig;

    @Autowired
    @Qualifier("ossClient")
    private OSSClient ossClient;


    @Override
    public AccountVo getAccountInfo(Long uid) throws BusinessException {

        User u = userRepository.findByIdAndStatus(uid,valid);
        if(isEmpty(u)){
            throw new BusinessException(Const.Code.USER_NOT_EXISTS,Const.Code.USER_NOT_EXISTS_MSG);
        }

        Account account = accountDao.findByUser(uid);
        if(isEmpty(account)){
            throw new BusinessException(Const.Code.ACCOUNT_ERROR,Const.Code.ACCOUNT_ERROR_MSG);
        }

//查看qrcode 是否过期
        if(System.currentTimeMillis()>=account.getUser().getQrcodeTimeout()){

            QrcodeAPI  qrcodeApi = new QrcodeAPI(userApiConfig);
            User user =  account.getUser();
            QrcodeResponse qrcodeResp = qrcodeApi.createQrcode(QrcodeType.QR_SCENE,user.getId().toString(),2592000);
            user.setQrcode(qrcodeResp.getUrl());
            user.setQrcodeTimeout(System.currentTimeMillis()+2550000);
            user.setQrcodeTicket(qrcodeResp.getTicket());

            user = userRepository.save(user);

            //上传二维码图片
            try {
                String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+qrcodeResp.getTicket();
                ossClient.putObject(Const.OSS_BUCKET_NAME, "user/qrcode/"+user.getId()+".jpg", new URL(url).openStream());

            } catch (IOException e) {
                e.printStackTrace();
            }

            account.setUser(user);
        }

        return BeanMapper.map(account,AccountVo.class);
    }



}
