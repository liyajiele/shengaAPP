package com.sxp.sa.merchant.service.impl;


import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.merchant.entity.About;
import com.sxp.sa.merchant.repository.AboutRepository;
import com.sxp.sa.merchant.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutServiceImpl extends BaseService implements AboutService{


    @Autowired
    private AboutRepository aboutRepository;

    @Override
    public About findByOSType(Integer osType) throws BusinessException {
        return aboutRepository.findByOSType(osType);
    }
}
