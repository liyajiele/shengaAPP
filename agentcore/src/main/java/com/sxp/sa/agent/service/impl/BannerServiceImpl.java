package com.sxp.sa.agent.service.impl;

import com.sxp.sa.agent.entity.AgentInfos;
import com.sxp.sa.agent.entity.Banner;
import com.sxp.sa.agent.repository.AgentInfosRepository;
import com.sxp.sa.agent.repository.BannerRepository;
import com.sxp.sa.agent.service.BannerService;
import com.sxp.sa.agent.vo.BannerVo;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.entity.City;
import com.sxp.sa.basic.entity.District;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.repository.CityRepository;
import com.sxp.sa.basic.repository.DistrictRepository;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BaiduLngLonUtil;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.user.entity.Admin;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.sxp.sa.basic.constant.Const.Code.*;
import static com.sxp.sa.basic.utils.Util.*;

/**
 * Created by Administrator
 */

@Service
public class BannerServiceImpl extends BaseService implements BannerService {

    @Autowired
    private BannerRepository bannerDao;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AgentInfosRepository agentInfosRepository;


    @Override
    public List<BannerVo> findBannerList(String longitude,String latitude) {
        //官网的banner
        List<Banner> bannerList =  bannerDao.findGuangFangBannerList();

        //区域代理商banner
        if(isNotEmpty(longitude) && isNotEmpty(latitude)){
            Map<String,String> locationInfo = BaiduLngLonUtil.getLocationInfo(Double.parseDouble(longitude),Double.parseDouble(latitude));
            //定位到的区域
            if(isNotEmpty(locationInfo) && isNotEmpty(locationInfo.get("district"))){
                City city = cityRepository.findByName(locationInfo.get("city"));
                //查询系统中的区域信息
                District district = districtRepository.findByNameAndCityId(locationInfo.get("district"),city.gettId());

                //查询区域中的 banner
                if(isNotEmpty(district)){
                    List<Banner> agentBanner = bannerDao.findBannerByDistrict(district.gettId());

                    bannerList.addAll(agentBanner);
                }
            }

        }
        return BeanMapper.mapList(bannerList,BannerVo.class);
    }

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
    @Override
    public BannerVo adminAddBanner(Long aid, String image, String url, String content, Integer status, Long carouselTime, Integer priority) throws BusinessException {

        Banner banner = new Banner();
        banner.setCreateUser(aid);
        banner.setUrl(url);
        banner.setImage(image);
        banner.setContent(content);
        banner.setStatus(status);
        banner.setPriority(priority);

        //默认
        if(isEmpty(carouselTime)){
            carouselTime= Const.BANNER_CAROUSEL;
        }
        banner.setCarouselTime(carouselTime);
        banner.setModifyDesc("add");

        banner = bannerDao.save(banner);
        return BeanMapper.map(banner,BannerVo.class);
    }

    /**
     * 代理商增加banner
     * @param aid
     * @param districtId
     * @param image
     * @param url
     * @param content
     * @param carouselTime
     * @param priority
     * @return
     * @throws BusinessException
     */
    @Override
    public BannerVo agentAddBanner(Long aid, Integer districtId, String image, String url, String content, Long carouselTime, Integer priority) throws BusinessException {


        User user = adminRepository.findByAdminId(aid).getUser();

        //查询代理信息
        AgentInfos agentInfos = agentInfosRepository.findByDistrictAndUserId(user.getId(),districtId);
        if(isEmpty(agentInfos)){
            throw new BusinessException(AGENT_INFO_NOT_EXISTS,AGENT_INFO_NOT_EXISTS_MSG);
        }



        Banner banner = new Banner();
        banner.setCreateUser(aid);
        banner.setUrl(url);
        banner.setImage(image);
        banner.setContent(content);
        banner.setPriority(priority);
        banner.setAgentId(aid);
        banner.setDistrictId(districtId);

        //默认
        if(isEmpty(carouselTime)){
            carouselTime= Const.BANNER_CAROUSEL;
        }
        banner.setCarouselTime(carouselTime);
        banner.setModifyDesc("agent add");

        banner = bannerDao.save(banner);
        return BeanMapper.map(banner,BannerVo.class);

    }

    /**
     * 管理员修改banner (修改,发布,下架,审核)
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
    @Override
    public BannerVo adminModifyBanner(Long aid, Long bannerId, String image, String url, String content, Integer status, Long carouselTime, Integer priority) throws BusinessException {


        Banner banner = bannerDao.findOne(bannerId);

        if(isEmpty(banner)){
            throw new BusinessException(BANNER_NOT_EXISTS,BANNER_NOT_EXISTS_MSG);
        }

        banner.setModifyUser(aid);
        banner.setImage(image);
        banner.setUrl(url);
        banner.setContent(content);
        banner.setStatus(status);
        banner.setCarouselTime(carouselTime);
        banner.setPriority(priority);

        if(isNotEmpty(banner.getAgentId())){
            banner.setModifyDesc("shenghe");
        }else{
            banner.setModifyDesc("modify");
        }
        banner = bannerDao.save(banner);
        return BeanMapper.map(banner,BannerVo.class);
    }

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
    @Override
    public BannerVo agentModifyBanner(Long aid, Long bannerId, String image, String url, String content, Integer status, Long carouselTime, Integer priority) throws BusinessException {



        Banner banner = bannerDao.findOne(bannerId);

        if(isEmpty(banner)){
            throw new BusinessException(BANNER_NOT_EXISTS,BANNER_NOT_EXISTS_MSG);
        }
        if(banner.getAgentId()!=aid){
            throw new BusinessException(UNAUTHORIZED,"权限不足");
        }

        banner.setModifyUser(aid);
        banner.setImage(image);
        banner.setUrl(url);
        banner.setContent(content);
        if(status==1){
            throw new BusinessException(UNAUTHORIZED,"权限不足");
        }
        banner.setStatus(status);
        banner.setCarouselTime(carouselTime);
        banner.setPriority(priority);


        banner.setModifyDesc("agent modify");
        banner = bannerDao.save(banner);
        return BeanMapper.map(banner,BannerVo.class);
    }

    /**
     * 管理员查看banner列表
     * @param aid
     * @param status
     * @param districtId
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<BannerVo> adminFindBannerList(Long aid, Integer status, Integer districtId,Pageable pageable) throws BusinessException {

        //查询 区域banner
        if(isEmpty(districtId)){

            Page bannerPage = bannerDao.adminFindBannerList(status,pageable);

            return p2pr(bannerPage,BannerVo.class);


        }else{

            Page bannerPage = bannerDao.adminFindBannerList(status,districtId,pageable);

            return p2pr(bannerPage,BannerVo.class);
        }

    }

    /**
     * 代理商查看banner列表
     * @param aid
     * @param status
     * @param districtId
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<BannerVo> agentFindBannerList(Long aid, Integer status, Integer districtId, Pageable pageable) throws BusinessException {


        //查询是否是自己的区域
        Admin admin = adminRepository.findByAdminId(aid);

        AgentInfos agentInfos = agentInfosRepository.findByDistrictAndUserId(admin.getUser().getId(),districtId);

        if(isEmpty(agentInfos)){
            throw new BusinessException(UNAUTHORIZED,"权限不足");
        }



            Page bannerPage = bannerDao.adminFindBannerList(status,districtId,pageable);

            return p2pr(bannerPage,BannerVo.class);



    }
}
