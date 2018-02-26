package com.sxp.sa.user.authc;

import com.sxp.sa.user.entity.UrlFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Service
public class ShiroFilerChainManager {

    @Autowired
    private CustomDefaultFilterChainManager filterChainManager;

    private Map<String, NamedFilterList> defaultFilterChains;

    @PostConstruct
    public void init() {
        defaultFilterChains = new LinkedHashMap<String, NamedFilterList>(filterChainManager.getFilterChains());
    }

    public void initFilterChains(List<UrlFilter> urlFilters) {
//        //1、首先删除以前老的filter chain并注册默认的
//        filterChainManager.getFilterChains().clear();
//        if(defaultFilterChains != null) {
//            filterChainManager.getFilterChains().putAll(defaultFilterChains);
//        }
//
//        //2、循环URL Filter 注册filter chain
//        for (UrlFilter urlFilter : urlFilters) {
//            String url = urlFilter.getUrl();
//            //注册roles filter
//            if (!StringUtils.isEmpty(urlFilter.getRoles())) {
//                filterChainManager.addToChain(url, "roles", urlFilter.getRoles());
//            }
//            //注册perms filter
//            if (!StringUtils.isEmpty(urlFilter.getPermissions())) {
//                filterChainManager.addToChain(url, "perms", urlFilter.getPermissions());
//            }
//        }


        //1、首先删除以前老的filter chain并注册默认的
        filterChainManager.getFilterChains().clear();
        //2、循环URL Filter 注册filter chain
        for (UrlFilter urlFilter : urlFilters) {
            String url = urlFilter.getUrl();
            //注册roles filter
            if (!StringUtils.isEmpty(urlFilter.getRoles())) {
                filterChainManager.addToChain(url, "roles", urlFilter.getRoles());
            }
            //注册perms filter
            if (!StringUtils.isEmpty(urlFilter.getPermissions())) {
                filterChainManager.addToChain(url, "perms", urlFilter.getPermissions());
            }
        }
        if(defaultFilterChains != null) {
            filterChainManager.getFilterChains().putAll(defaultFilterChains);
        }


    }

}
