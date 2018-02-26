package com.sxp.sa.api.config;

import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.constant.WxConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@Import({RedisConfig.class})
public class CommonConfig {

    /**
     * 短信发送客户端
     * @return
     */
    @Bean
    public IAcsClient getIAcsClient() {
        try {
            IClientProfile profile = DefaultProfile.getProfile(Const.SMSParams.SMS_ENDPOINT, Const.AliParam.ALI_KEY_SA, Const.AliParam.ALI_SECRET_SA);

            DefaultProfile.addEndpoint(Const.SMSParams.SMS_ENDPOINT, Const.SMSParams.SMS_REGION_ID, Const.SMSParams.SMS,  Const.SMSParams.SMS_DOMAIN);

            IAcsClient client = new DefaultAcsClient(profile);

            return client;
        }catch (ClientException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Bean("ossClient")
    public OSSClient ossClient(){
        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
        // accessKey请登录https://ak-console.aliyun.com/#/查看
        String accessKeyId = Const.AliParam.ALI_KEY_SA;
        String accessKeySecret = Const.AliParam.ALI_SECRET_SA;
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }


    @Bean("userApiConfig")
    public ApiConfig yiApiConfig(RedisTemplate redisTemplate){
         ApiConfig apiConfig = new  ApiConfig(WxConst.UserWeixinAccount.APPID,WxConst.UserWeixinAccount.SECRET,WxConst.UserWeixinAccount.OPEN_JSAPI,WxConst.UserWeixinAccount.USER,redisTemplate);
        return apiConfig;
    }

}
