package com.sxp.sa.basic.utils.eascmob;

import com.google.gson.Gson;
import com.sxp.sa.basic.utils.Util;
import io.swagger.client.ApiException;
import io.swagger.client.api.AuthenticationApi;
import io.swagger.client.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by easemob on 2017/3/14.
 */
public class TokenUtil {
    public   String GRANT_TYPE;
    private   String CLIENT_ID;
    private   String CLIENT_SECRET;
    private   Token BODY;

    private String orgName;
    private String appName;

    private   AuthenticationApi API = new AuthenticationApi();
    private   final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    private RedisTemplate redisTemplate;


    public TokenUtil(String clientId, String grantType, String clientSecret, String orgName ,String appName ,RedisTemplate redisTemplate){
        GRANT_TYPE = grantType;
        CLIENT_ID = clientId;
        CLIENT_SECRET = clientSecret;
        BODY = new Token().clientId(clientId).grantType(grantType).clientSecret(clientSecret);

        this.orgName = orgName;
        this.appName = appName;

        this.redisTemplate = redisTemplate;
    }

    public  HashMap<String,String>  initTokenByProp() {
        String resp = null;
        try {
            resp = API.orgNameAppNameTokenPost(orgName, appName,  BODY);
        } catch (ApiException e) {
            logger.error(e.getMessage());
        }
        Gson gson = new Gson();
        Map map = gson.fromJson(resp, Map.class);
        String token = " Bearer " + map.get("access_token");
        String expires = (System.currentTimeMillis() + (Double) map.get("expires_in"))+"";

        HashMap<String,String> tokenInfo = new HashMap<String,String>();
        tokenInfo.put("token",token);
        tokenInfo.put("expires",expires);

        this.redisTemplate.opsForHash().putAll("yiEaseToken",tokenInfo);

        return tokenInfo;

    }

    /**
     * get Token from memory
     *
     * @return
     */
    public String getAccessToken() {
        Map<String,String> tokenInfo = redisTemplate.opsForHash().entries("yiEaseToken");
        if(Util.isEmpty(tokenInfo) || isExpired(tokenInfo.get("expires"))){
            initTokenByProp();
        }
        return tokenInfo.get("token");
    }

    private  Boolean isExpired(String expires) {
        return System.currentTimeMillis() > Long.parseLong(expires);
    }

}

