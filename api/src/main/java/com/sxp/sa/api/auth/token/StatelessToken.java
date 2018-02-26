package com.sxp.sa.api.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * 无状态 token
 * @author miss
 *
 */
public class StatelessToken implements AuthenticationToken {

    private String uid;
    private Map<String, ?> params;
    private String sign;

    public StatelessToken(String uid, Map<String, ?> params, String sign) {
        this.uid = uid;
        this.params = params;
        this.sign = sign;
    }


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Map<String, ?> getParams() {
		return params;
	}



	public void setParams(Map<String, ?> params) {
		this.params = params;
	}



	public String getSign() {
		return sign;
	}



	public void setSign(String sign) {
		this.sign = sign;
	}



	@Override
    public Object getPrincipal() {
       return uid;
    }

    @Override
    public Object getCredentials() {
        return sign;
    }
}
