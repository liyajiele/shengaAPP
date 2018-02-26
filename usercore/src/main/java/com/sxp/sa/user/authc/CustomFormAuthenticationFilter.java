package com.sxp.sa.user.authc;

import com.alibaba.fastjson.JSON;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.utils.Rst;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by miss on 2017/6/28.
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        //TODO getUserInfoByUsername
        Object userVo = null;
        onLogin(response,"登录成功",userVo);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        try {
            onLogin(response,"登录失败",null);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    //登录失败/成功
    private void onLogin(ServletResponse response,String rstString,Object userVo) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Headers","sign");
        httpResponse.setHeader("Access-Control-Exposed-Headers","sign");
        httpResponse.setHeader("Access-Control-Allow-Methods","GET,POST");
        httpResponse.setHeader("Access-Control-Max-Age","36000");

        //反正对象
        Rst<Object> rst = new Rst<Object>(Const.Code.SUCCESS,rstString);
        rst.setObject(userVo);

        String jsonRst = JSON.toJSONString(rst);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().write(jsonRst);
    }
}
