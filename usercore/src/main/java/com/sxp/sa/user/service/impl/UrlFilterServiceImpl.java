package com.sxp.sa.user.service.impl;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.user.authc.ShiroFilerChainManager;
import com.sxp.sa.user.entity.UrlFilter;
import com.sxp.sa.user.repository.UrlFilterRepository;
import com.sxp.sa.user.service.UrlFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
@Service
public class UrlFilterServiceImpl implements UrlFilterService {

    @Autowired
    private UrlFilterRepository urlFilterRepository;

    @Autowired
    private ShiroFilerChainManager shiroFilerChainManager;

    @Override
    public UrlFilter createUrlFilter(UrlFilter urlFilter) {
        urlFilterRepository.save(urlFilter);
        initFilterChain();
        return urlFilter;
    }



    @Override
    public UrlFilter updateUrlFilter(UrlFilter urlFilter) {
        urlFilterRepository.save(urlFilter);
        initFilterChain();
        return urlFilter;
    }

    @Override
    public void deleteUrlFilter(Long urlFilterId) {
        UrlFilter urlFilter = urlFilterRepository.findById(urlFilterId);
        if(Util.isNotEmpty(urlFilter)){
            urlFilter.setStatus(Const.Status.delete);
        }
        initFilterChain();
    }

    @Override
    public UrlFilter findOne(Long urlFilterId) {
        return urlFilterRepository.findById(urlFilterId);
    }

    @Override
    public List<UrlFilter> findAll() {
        return urlFilterRepository.findAllUrlFilter();
    }

    @PostConstruct
    public void initFilterChain() {
        shiroFilerChainManager.initFilterChains(findAll());
    }

}
