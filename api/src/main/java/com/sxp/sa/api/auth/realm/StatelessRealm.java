package com.sxp.sa.api.auth.realm;

import com.sxp.sa.api.auth.token.StatelessToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class StatelessRealm extends AuthorizingRealm {
	
	private Cache<String,String> tokenCache;
	
	public StatelessRealm(){
		
	}
	public StatelessRealm(Cache cache){
		this.tokenCache = cache;
	}
	
	
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //根据用户名查找角色，请根据需求实现
        String sid = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
        return authorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) token;
        String sid = statelessToken.getUid();
        
      //在服务器端生成客户端参数消息摘要
        String[] url = (String[])statelessToken.getParams().get("url");
        //获取 token(给客户端的str)
        String key =  tokenCache.get("tk"+sid); //根据用户名获取密钥（和客户端的一样）


        String serverDigest = new SimpleHash("MD5", url[0]+sid+key).toString();
        
        //然后进行客户端消息摘要和服务器端消息摘要的匹配
        return new SimpleAuthenticationInfo(
                sid,
                serverDigest,
                getName());
    }

    
}