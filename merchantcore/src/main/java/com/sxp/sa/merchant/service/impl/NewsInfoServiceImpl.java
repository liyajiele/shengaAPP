package com.sxp.sa.merchant.service.impl;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.merchant.entity.NewsInfo;
import com.sxp.sa.merchant.repository.NewsInfoRepository;
import com.sxp.sa.merchant.service.NewsInfoService;
import com.sxp.sa.merchant.vo.NewsInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sxp.sa.basic.utils.Util.p2pr;

@Service
public class NewsInfoServiceImpl extends BaseService implements NewsInfoService{

    @Autowired
    private NewsInfoRepository newsInfoRepository;


    /**
     * 分页获取 news
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<NewsInfoVo> findByPage(Pageable pageable) throws BusinessException {

        Page<NewsInfo> page = newsInfoRepository.findInfosByPage(pageable);
        if(page.getNumberOfElements()>0){

            return p2pr(page, NewsInfoVo.class);
        }
        return null;
    }

    @Override
    public Pager<NewsInfoVo> getRedBagInfos(Pageable pageable) throws BusinessException {
        Page<NewsInfo> page = newsInfoRepository.getInfosByType(1,pageable);
        if(page.getNumberOfElements()>0){
            return p2pr(page, NewsInfoVo.class);
        }
        return null;
    }
}
