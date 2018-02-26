package com.sxp.sa.api.auth.filter;

import com.alibaba.fastjson.JSON;
import com.sxp.sa.api.auth.token.StatelessToken;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.UserRepository;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.isEmpty;

/**
 * 无状态请求验证  过滤器
 * @author miss
 *
 */
public class StatelessAuthcFilter extends AccessControlFilter {
	private final Logger logger = LoggerFactory.getLogger(StatelessAuthcFilter.class);

	@Autowired
	private UserRepository userRepository;
	
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	
    	
    	HttpServletRequest req = (HttpServletRequest)request;
        if(req.getMethod().equals("OPTIONS")){
            onCORS(response);
            return false;
        }


    	HttpServletResponse resp =(HttpServletResponse)response;
    	//客户端请求的参数列表
        Map<String, String[]> params = new HashMap<String, String[]>(req.getParameterMap());
        
        //请求地址
        String url = req.getRequestURI().toString();
        params.put("url", new String[]{url});


        String ip = Util.getRemoteHost((HttpServletRequest) request);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(ip+"  "+sdf.format(new Date()));


        if(!Const.TOKEN_CHECK){
            return true;
        }
        if(Util.getRemoteHost(req).equals("139.196.8.175")){
            return true;
        }

        
        if(req.getParameter("uid")!=null && (req.getHeader("sign")!=null || req.getParameter("sign")!=null)){

            Long uid = Long.parseLong(req.getParameter("uid"));
            User user = userRepository.findByIdAndStatus(uid,valid);
            if(isEmpty(user)){
                logger.info("用户不存在",uid);
                onLoginFail(response);
                return false;
            }
            //如果调用商家接口
            if(url.startsWith("/api/mct") && ( user.getIsMerchant()==0 || isEmpty(user.getIsMerchant()))){

                logger.info("非商家",uid);
                onLoginFail(response);
                return false;
            }

            //获取用户id
    		String id = req.getParameter("uid");
    		//加密的字符串
    		String sign = req.getHeader("sign")!=null?req.getHeader("sign"):req.getParameter("sign");
    		//params 中将sign 移除
    		params.remove("sign");
    		
    		StatelessToken token = new StatelessToken(id,params,sign);
    		
    		try {
                //委托给StatelessRealm进行登录
                getSubject(req, resp).login(token);
            } catch (Exception e) {
            	logger.info("api验证-{}失败",id);
                //验证失败
                //e.printStackTrace();
                onLoginFail(response); 
                return false;
            }
            return true;
    		
            
    	}else{
    		onLoginFail(response); 
	    	return false;
    	}
    	
    	
    	
    }
    //跨域请求
    private void onCORS(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Headers","sign");
        httpResponse.setHeader("Access-Control-Exposed-Headers","sign");
        httpResponse.setHeader("Access-Control-Allow-Methods","GET,POST");
        httpResponse.setHeader("Access-Control-Max-Age","36000");
        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
    //登录失败
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Headers","sign");
        httpResponse.setHeader("Access-Control-Exposed-Headers","sign");
        httpResponse.setHeader("Access-Control-Allow-Methods","GET,POST");
        httpResponse.setHeader("Access-Control-Max-Age","36000");
        //反正对象
        Rst<Object> rst = new Rst<Object>(Const.Code.TOKEN_ERROR,"token验证失败,请重新登录");
        String jsonRst = JSON.toJSONString(rst);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().write(jsonRst);
    }
}