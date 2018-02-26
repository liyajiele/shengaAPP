package com.sxp.sa.api.config;

import com.sxp.sa.api.auth.filter.AdminFilter;
import com.sxp.sa.api.auth.filter.StatelessAuthcFilter;
import com.sxp.sa.api.auth.realm.AdminRealm;
import com.sxp.sa.api.auth.realm.StatelessRealm;
import com.sxp.sa.basic.utils.MyRedisManager;
import com.sxp.sa.user.authc.CustomDefaultFilterChainManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.crazycake.shiro.RedisCache;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import redis.clients.jedis.JedisPool;

import javax.servlet.Filter;
import java.util.*;

/**
 * Created by miss on 2017/6/27.
 */

@Configuration
@Import({RedisConfig.class})
public class ShiroConfig {


    //连接工厂相关
    @Value("${spring.redis.host}")
    private String hostName;

    @Value("${spring.redis.port}")
    private Integer port;


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(
            AdminRealm adminRealm,
            StatelessRealm statelessRealm
    ){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        //sessionManager
        defaultWebSecurityManager.setSessionManager(new ServletContainerSessionManager());
        //cacheManager TODO
//        defaultWebSecurityManager.setCacheManager(null);
        //remeberMeManager TODO
//        defaultWebSecurityManager.setRememberMeManager(null);

        //realms
        Collection<Realm> realms = new HashSet<Realm>();
        realms.add(adminRealm);
        realms.add(statelessRealm);



        defaultWebSecurityManager.setRealms(realms);

        return defaultWebSecurityManager;
    }

    @Bean(name = "shiroFilter")
    public AbstractShiroFilter getShiroFilter(DefaultWebSecurityManager securityManager
//                                              ,CustomPathMatchingFilterChainResolver customPathMatchingFilterChainResolver
    ){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();

//            shiroFilter.setFilterChainResolver(customPathMatchingFilterChainResolver);
            return shiroFilter;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    //自定义PathMatchingFitlerChainResolver
//    @Bean(name = "filterChainResolver")
//    public CustomPathMatchingFilterChainResolver getCustomPathMatchingFilterChainResolver(CustomDefaultFilterChainManager customDefaultFilterChainManager){
//        CustomPathMatchingFilterChainResolver resolver = new CustomPathMatchingFilterChainResolver();
//        resolver.setCustomDefaultFilterChainManager(customDefaultFilterChainManager);
//
//        return resolver;
//    }

    @Bean(name = "filterChainManager")
    public CustomDefaultFilterChainManager getCustomDefaultFilterChainManager(
//            FormAuthenticationFilter formAuthenticationFilter,
            StatelessAuthcFilter statelessAuthcFilter,
            AdminFilter adminFilter
    ){
        CustomDefaultFilterChainManager customDefaultFilterChainManager = new CustomDefaultFilterChainManager();

        customDefaultFilterChainManager.setLoginUrl("/sb");
        customDefaultFilterChainManager.setSuccessUrl("/index.html");
        customDefaultFilterChainManager.setUnauthorizedUrl("/unauthorized.html");

        //自定义filter
        Map<String,Filter> customFilters = new HashMap<String,Filter>();
//        customFilters.put("authc",formAuthenticationFilter);
        customFilters.put("statelessAuthc",statelessAuthcFilter);
        customFilters.put("adminFilter",adminFilter);




        customDefaultFilterChainManager.setCustomFilters(customFilters);


        //设置 filterChainDefinitions
        LinkedHashMap<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();


        filterChainDefinitionManager.put("/api/admin/**","adminFilter");

        filterChainDefinitionManager.put("/**/authc/**","statelessAuthc");

        filterChainDefinitionManager.put("/api/admin/user/login","anon");
//
//        filterChainDefinitionManager.put("/**","anon");






        customDefaultFilterChainManager.setFilterChainDefinitionMap(filterChainDefinitionManager);
        return customDefaultFilterChainManager;
    }







    @Bean(name = "adminRealm")
    public AdminRealm getAdminRealm(){
        AdminRealm adminRealm = new AdminRealm();

        adminRealm.setCachingEnabled(false);

        return adminRealm;
    }

    @Bean(name = "statelessRealm")
    public StatelessRealm getStatelessRealm(RedisCache redisCache){
        StatelessRealm statelessRealm = new StatelessRealm(redisCache);


        return statelessRealm;
    }

//    @Bean(name = "formAuthenticationFilter")
//    public CustomFormAuthenticationFilter getFormAuthenticationFilter(){
//        CustomFormAuthenticationFilter formAuthenticationFilter = new CustomFormAuthenticationFilter();
//
//        formAuthenticationFilter.setUsernameParam("username");
//        formAuthenticationFilter.setPasswordParam("password");
//        formAuthenticationFilter.setRememberMeParam("rememberMe");
//        formAuthenticationFilter.setLoginUrl("/api/admin/user/login");
//
//        return formAuthenticationFilter;
//    }
    @Bean(name = "statelessAuthcFilter")
    public StatelessAuthcFilter getStatelessAuthcFilter(){
        StatelessAuthcFilter statelessAuthcFilter = new StatelessAuthcFilter();

        return statelessAuthcFilter;
    }

    @Bean(name = "adminFilter")
    public AdminFilter getAdminFilter(){
        AdminFilter adminFilter = new AdminFilter();

        return adminFilter;
    }





    /**
     * 自定义cacheManager
     * @return
     */
    @Bean(name = "redisCache")
    public RedisCache redisCache(JedisPool jedisPool){
        RedisCache redisCache = new RedisCache(myRedisManager(jedisPool));
        return redisCache;
    }



    @Bean(name = "redisManager")
    @DependsOn({"jedisPool"})
    public MyRedisManager myRedisManager(JedisPool jedisPool){
        MyRedisManager redisManager = new MyRedisManager();
        redisManager.setHost(hostName);
        redisManager.setPort(port);
        redisManager.setJedisPool(jedisPool);

        return  redisManager;
    }

    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager(MyRedisManager myRedisManager){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(myRedisManager);
        return redisCacheManager;
    }
}


