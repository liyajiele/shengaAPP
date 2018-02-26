package com.sxp.sa.api.auth.filter;

import com.alibaba.fastjson.JSON;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.user.entity.Admin;
import com.sxp.sa.user.entity.Role;
import com.sxp.sa.user.entity.UrlFilter;
import com.sxp.sa.user.entity.UrlFilterList;
import com.sxp.sa.user.repository.AdminRepository;
import com.sxp.sa.user.repository.AdminRoleRepository;
import com.sxp.sa.user.repository.UserRepository;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sxp.sa.basic.utils.Util.isEmpty;
import static com.sxp.sa.basic.utils.Util.isNotEmpty;

/**
 * 无状态请求验证  过滤器
 * @author miss
 *
 */
public class AdminFilter extends AccessControlFilter {
	private final Logger logger = LoggerFactory.getLogger(AdminFilter.class);


	@Autowired
    private RedisTemplate<String,String> redisTemplate;

	@Autowired
	private UrlFilterList urlFilterList;


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private AdminRoleRepository adminRoleRepository;
	
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



        if(!Const.ADMIN_TOKEN_CHECK){
            return true;
        }



        
        if(req.getParameter("aid")!=null && (req.getHeader("sign")!=null || req.getParameter("sign")!=null)){

            Long aid = Long.parseLong(req.getParameter("aid"));

            Admin admin = adminRepository.findByAdminId(aid);
            if(isEmpty(admin)){
                logger.info("管理员不存在",aid);
                onLoginFail(response);
                return false;
            }
            //获取用户id
    		String id = req.getParameter("aid");
    		//加密的字符串
    		String sign = req.getHeader("sign")!=null?req.getHeader("sign"):req.getParameter("sign");


    		//验证sign
            String redisToken =  redisTemplate.opsForValue().get("admin"+aid);
            String serverDigest = new SimpleHash("MD5", url+aid+redisToken).toString();
            //登录失败
            if(!sign.equals(serverDigest)){
                this.onLoginFail(response);
                return false;
            }

            //验证权限
            UrlFilter urlFilter = urlFilterList.getFilterList().get(url);
            if(isNotEmpty(urlFilter)) {
                String[] roles = urlFilter.getRoles().split(",");
                List<Role> roleList =  adminRoleRepository.findByAdminId(aid);

                for(int k=0;k<roles.length;k++){
                    for(int i=0;i<roleList.size();i++){
                        if(roles[k].equals(roleList.get(i).getRole())){
                            return true;
                        }
                    }
                }
                onAuthzFail(response);
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

    //权限不足
    private void onAuthzFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Headers","sign");
        httpResponse.setHeader("Access-Control-Exposed-Headers","sign");
        httpResponse.setHeader("Access-Control-Allow-Methods","GET,POST");
        httpResponse.setHeader("Access-Control-Max-Age","36000");
        //反正对象
        Rst<Object> rst = new Rst<Object>(Const.Code.TOKEN_ERROR,"权限不足");
        String jsonRst = JSON.toJSONString(rst);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().write(jsonRst);
    }
}