package com.sxp.sa.agent.service;


import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.agent.vo.BannerVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2017/2/24.
 */
public interface BannerService {

    /**
     * 查询banner列表
     * @return
     */
    List<BannerVo> findBannerList(String longitude,String latitude);


    /**
     * 管理员增加banner
     * @param aid
     * @param image
     * @param url
     * @param content
     * @param status
     * @param carouselTime
     * @param priority
     * @return
     * @throws BusinessException
     */
    BannerVo adminAddBanner(Long aid,String image,String url,String content,Integer status,Long carouselTime,Integer priority)throws BusinessException;


    /**
     * 代理商增加banner
     * @param aid
     * @param image
     * @param url
     * @param content
     * @param status
     * @param carouselTime
     * @param priority
     * @return
     * @throws BusinessException
     */
    BannerVo agentAddBanner(Long aid,Integer districtId,String image,String url,String content,Long carouselTime,Integer priority)throws BusinessException;


    /**
     * 管理员修改banner
     * @param aid
     * @param bannerId
     * @param image
     * @param url
     * @param content
     * @param status
     * @param carouselTime
     * @param priority
     * @return
     * @throws BusinessException
     */
    BannerVo adminModifyBanner(Long aid,Long bannerId,String image,String url,String content,Integer status,Long carouselTime,Integer priority)throws BusinessException;

    /**
     * 代理商修改banner
     * @param aid
     * @param bannerId
     * @param image
     * @param url
     * @param content
     * @param status
     * @param carouselTime
     * @param priority
     * @return
     * @throws BusinessException
     */
    BannerVo agentModifyBanner(Long aid,Long bannerId,String image,String url,String content,Integer status,Long carouselTime,Integer priority)throws BusinessException;

    /**
     * 管理员查看banner列表
     * @param aid
     * @param status
     * @param districtId
     * @return
     * @throws BusinessException
     */
    Pager<BannerVo> adminFindBannerList(Long aid,Integer status,Integer districtId,Pageable pageable)throws BusinessException;


    /**
     * 代理商查看bannerList
     * @param aid
     * @param status
     * @param districtId
     * @param pageable
     * @return
     * @throws BusinessException
     */
    Pager<BannerVo> agentFindBannerList(Long aid,Integer status,Integer districtId,Pageable pageable)throws BusinessException;
}
