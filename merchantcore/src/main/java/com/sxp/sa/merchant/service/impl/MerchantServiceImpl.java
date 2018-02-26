package com.sxp.sa.merchant.service.impl;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.entity.City;
import com.sxp.sa.basic.entity.District;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.entity.Province;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.repository.CityRepository;
import com.sxp.sa.basic.repository.DistrictRepository;
import com.sxp.sa.basic.repository.ProvinceRepository;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BaiduLngLonUtil;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.merchant.entity.*;
import com.sxp.sa.merchant.repository.*;
import com.sxp.sa.merchant.service.MerchantService;
import com.sxp.sa.merchant.vo.MerchantAuditVo;
import com.sxp.sa.merchant.vo.MerchantDetailVo;
import com.sxp.sa.merchant.vo.MerchantVo;
import com.sxp.sa.user.entity.SearchRecord;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.SearchRecodRepository;
import com.sxp.sa.user.repository.UserRepository;
import com.sxp.sa.user.vo.OtherUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.sxp.sa.basic.constant.Const.Code.*;
import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.*;

@Service
public class MerchantServiceImpl extends BaseService implements MerchantService {


    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private DistrictRepository districtRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantTypeRepository merchantTypeRepository;

    @Autowired
    private MerchantOwnTypeRepository merchantOwnTypeRepository;

    @Autowired
    private MerchantAuditRepository auditRepository;

    @Autowired
    private SearchRecodRepository searchRecodRepository;

    @Override
    public Pager<MerchantVo> nearByMerchant(Long uid,Long typeId ,Integer districtId,String searchStr, Pageable pageable) throws BusinessException {

        User user = userRepository.findByIdAndStatus(uid,valid);

        //用户不存在
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }
        //经纬度
        Double longitude = 0.0;
        Double latitude = 0.0;

        if(isEmpty(user.getLongitude())){ longitude = Const.DEFAULT_LONGITUDE;}else{
            longitude = Double.parseDouble(user.getLongitude());
        }
        if(isEmpty(user.getLatitude())){ latitude = Const.DEFAULT_LATITUDE;}else{
            latitude = Double.parseDouble(user.getLatitude());
        }




        Page<MerchantVo> mvo =null;
        SearchRecord sr = null;
        String searchStrBack = searchStr;

        if(isEmpty(districtId)){
            if(isEmpty(typeId)){
                if(isEmpty(searchStr)){
                    mvo = merchantRepository.nearByMerchant(longitude,latitude,pageable);
                }else{
                    sr = searchRecodRepository.findBySearchStr(uid,searchStr);
                    searchStr = "%"+searchStr+"%";
                    mvo = merchantRepository.nearByMerchantNameLike(longitude,latitude,searchStr,pageable);

                }

            }else{
                if(isEmpty(searchStr)){
                    mvo = merchantOwnTypeRepository.nearByMerchant(longitude,latitude,typeId,pageable);
                }else{
                    sr = searchRecodRepository.findBySearchStr(uid,searchStr);
                    searchStr = "%"+searchStr+"%";
                    mvo = merchantOwnTypeRepository.nearByMerchantNameLike(longitude,latitude,typeId,searchStr,pageable);
                }

            }
        }
//        else{
//            if(isEmpty(typeId)){
//                if(isEmpty(searchStr)){
//                    mvo = merchantRepository.nearByMerchantByDistrict(longitude,latitude,districtId,pageable);
//                }else{
//                    sr = searchRecodRepository.findBySearchStr(uid,searchStr);
//                    searchStr = "%"+searchStr+"%";
//                    mvo = merchantRepository.nearByMerchantNameLikeAndDistrict(longitude,latitude,searchStr,districtId,pageable);
//
//                }
//
//            }else{
//                if(isEmpty(searchStr)){
//                    mvo = merchantOwnTypeRepository.nearByMerchantByDistrict(longitude,latitude,typeId,districtId,pageable);
//                }else{
//                    sr = searchRecodRepository.findBySearchStr(uid,searchStr);
//                    searchStr = "%"+searchStr+"%";
//                    mvo = merchantOwnTypeRepository.nearByMerchantNameLikeAndDistrict(longitude,latitude,typeId,searchStr,districtId,pageable);
//                }
//
//            }
//
//
//        }



        //扩大到 city 查询
        if(isNotEmpty(districtId) && (isEmpty(mvo) || isEmpty(mvo.getContent()) || mvo.getContent().size()<1) ){

            District district = districtRepository.findById(districtId);
            if(isNotEmpty(district)){
                City city = cityRepository.findById(district.getCityId());

                if(isNotEmpty(city)){
                    if(isEmpty(typeId)){
                        if(isEmpty(searchStr)){
                            mvo = merchantRepository.nearByMerchantByCity(longitude,latitude,city.gettId(),pageable);
                        }else{
                            sr = searchRecodRepository.findBySearchStr(uid,searchStr);
                            searchStr = "%"+searchStr+"%";
                            mvo = merchantRepository.nearByMerchantNameLikeAndCity(longitude,latitude,searchStr,city.gettId(),pageable);

                        }

                    }else{
                        if(isEmpty(searchStr)){
                            mvo = merchantOwnTypeRepository.nearByMerchantByCity(longitude,latitude,typeId,city.gettId(),pageable);
                        }else{
                            sr = searchRecodRepository.findBySearchStr(uid,searchStr);
                            searchStr = "%"+searchStr+"%";
                            mvo = merchantOwnTypeRepository.nearByMerchantNameLikeAndCity(longitude,latitude,typeId,searchStr,city.gettId(),pageable);
                        }

                    }
                }


            }

        }


        //增加我的搜索记录
        if(isEmpty(sr)){
            sr = new SearchRecord();
            sr.setUser(user);
            sr.setContent(searchStrBack);
            sr.setLongitude(user.getLongitude());
            sr.setLatitude(user.getLatitude());
            // 各种 区县省
            Map<String ,String > areaInfos = BaiduLngLonUtil.getLocationInfo(Double.parseDouble(user.getLongitude()),Double.parseDouble(user.getLatitude()));
            if(isNotEmpty(areaInfos) && isNotEmpty(areaInfos.get("city")) && isNotEmpty(areaInfos.get("province")) && isNotEmpty(areaInfos.get("district")) ){
                String city = areaInfos.get("city");
                String province = areaInfos.get("province");
                String district = areaInfos.get("district");

                City c = cityRepository.findByName(city);
                Province p = provinceRepository.findByName(province);
                District d = districtRepository.findByNameAndCityId(district,c.gettId());

                sr.setCity(c);
                sr.setProvince(p);
                sr.setDistrict(d);
            }

            sr.setSearchTimes(1);
        }else{
            sr.setSearchTimes(sr.getSearchTimes()+1);
        }
        searchRecodRepository.save(sr);






        Pager<MerchantVo> mPager = p2pr(mvo, MerchantVo.class);

        return mPager;
    }

    /**
     * 获取所有的店铺分类
     * @return
     * @throws BusinessException
     */
    @Override
    public List<MerchantType> allMerchantTypes() throws BusinessException {

        return merchantTypeRepository.findAll(valid);
    }

    /**
     * 查看商家详情
     * @param mid
     * @param uid
     * @return
     * @throws BusinessException
     */
    @Override
    public MerchantDetailVo getMerchantDetail(Long mid, Long uid) throws BusinessException {


        Merchant merchant = merchantRepository.findById(mid,valid);
        if(isEmpty(merchant)){
            throw new BusinessException(MERCHANT_NOT_EXISTS,MERCHANT_NOT_EXISTS_MSG);
        }
        MerchantDetailVo merchantDetailVo = BeanMapper.map(merchant,MerchantDetailVo.class);

        //经纬度
        Double longitude =  Const.DEFAULT_LONGITUDE;
        Double latitude =  Const.DEFAULT_LATITUDE;

        if(isEmpty(longitude)) longitude = Const.DEFAULT_LONGITUDE;
        if(isEmpty(latitude)) latitude = Const.DEFAULT_LATITUDE;

        //如果登陆状态
        if(isNotEmpty(uid)){
            //查询是否收藏
            User user = userRepository.findByIdAndStatus(uid,valid);
            //用户不存在
            if(isEmpty(user)){
                throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
            }
            longitude = Double.parseDouble(user.getLongitude());
            latitude = Double.parseDouble(user.getLatitude());
            Integer collect = collectRepository.queryCollectNum(user,merchant,1);
            if(isNotEmpty(collect) && collect>0){
                merchantDetailVo.setHasCollect(1);
            }else{
                merchantDetailVo.setHasCollect(0);
            }


        }else{
            //未登录,未收藏
            merchantDetailVo.setHasCollect(0);
        }
        merchantDetailVo.setDistance(Util.getDistance(Double.parseDouble(merchantDetailVo.getLatitude()),Double.parseDouble(merchantDetailVo.getLongitude()),latitude,longitude));

        //查询最近两条评论
        //controller 中调用order 相关

        //查询附近同类推荐
        List<MerchantOwnType> types = merchantOwnTypeRepository.findTypeByMerchant(mid);
        //店铺没有分类
        if(isNotEmpty(types)){
            Pageable pageable = new PageRequest(0,Const.RECOMMOND_NUMS);
            Page<MerchantVo> mvo = merchantOwnTypeRepository.nearByMerchant(longitude,latitude,types.get(0).getMerchantType().getId(),pageable);

            merchantDetailVo.setRecommonds(mvo.getContent());
        }





        return merchantDetailVo;
    }



    //创建店铺
    @Override
    public MerchantAuditVo createMerchant(Long aid,Long uid, Integer areaId, Integer provinceId, Integer cityId, Integer districtId, String nickname, String addr, String mobile, String shopHours, String types, String ownerRealName,String ownerIdCard, String idcardImage1, String idcardImage2, Integer licenceType, Integer licenceStatus, String licenceImage,String mainImage,String images,String longitude,String latitude,
    Integer consumerption,Double profits,Integer rebateType) throws BusinessException {


        //检查用户  账号 , 密码 , isMerchant
        User user = userRepository.findByIdAndStatus(uid,valid);
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }
        user.setIdcardImage1(idcardImage1);
        user.setIdcardImage2(idcardImage2);
        userRepository.save(user);



        //创建 status -1 ,Merchant
        Merchant merchant = new Merchant();
        merchant.setStatus(Const.Status.locked);
        if(isNotEmpty(aid)){
            merchant.setStatus(Const.Status.valid);
            merchant.setCreateUser(aid);
            merchant.setModifyUser(aid);
            merchant.setModifyDesc("管理员增加");
        }
        merchant.setUid(uid);
        merchant.setNickname(nickname);

        merchant.setStars(Const.MerchantInitInfo.MERCHANT_INIT_STARTS);
        merchant.setConsumerption(Const.MerchantInitInfo.MERCHANT_COMSMERPTION);

        merchant.setShopHours(shopHours);
        merchant.setAddr(addr);
        merchant.setMobile(mobile);
        merchant.setCommentNum(0);

        merchant.setMainImage(mainImage);
        merchant.setImages(images);

        merchant.setLongitude(longitude);
        merchant.setLatitude(latitude);

        merchant.setProfits(Const.MerchantInitInfo.profits);
        merchant.setRebateType(Const.MerchantInitInfo.rebateType);

        merchant.setRebate(Const.MerchantInitInfo.rebate);
        if(isNotEmpty(profits)){
            merchant.setProfits(profits);
        }
        merchant.setRebateToMerchant(Const.MerchantInitInfo.rebateToMerchant*merchant.getProfits());
        merchant.setRetateToParent1(Const.MerchantInitInfo.retateToParent1*merchant.getProfits());
        merchant.setRetateToParent2(Const.MerchantInitInfo.retateToParent2*merchant.getProfits());
        merchant.setRetateToParent3(Const.MerchantInitInfo.retateToParent3*merchant.getProfits());

        merchant.setRetateToProvinceAgent(Const.MerchantInitInfo.retateToProvinceAgent*merchant.getProfits());
        merchant.setRetateToCityAgent(Const.MerchantInitInfo.retateToCityAgent*merchant.getProfits());
        merchant.setRetateToDistrictAgent(Const.MerchantInitInfo.retateToDistrictAgent*merchant.getProfits());
        merchant.setRetateToAreaAgent(Const.MerchantInitInfo.retateToAreaAgent*merchant.getProfits());

        merchant.setAreaId(areaId);
        merchant.setProvinceId(provinceId);
        merchant.setCityId(cityId);
        merchant.setDistrict(districtId);

        merchant.setOwnerRealName(ownerRealName);
        merchant.setOwnerIdcard(ownerIdCard);
        merchant.setLicenceType(licenceType);
        merchant.setLicenceStatus(licenceStatus);
        merchant.setLicenceImage(licenceImage);

        merchant.setTumover(0.0);
        merchant.setOrderNum(0);

        if(isNotEmpty(consumerption)){
            merchant.setConsumerption(consumerption);
        }

        if(isNotEmpty(rebateType)){
            merchant.setRebateType(rebateType);
        }



        merchant = merchantRepository.save(merchant);



        //设置店铺分类
        String[] typeArray = types.split("\\|");
        for(int i = 0 ;i<typeArray.length;i++){
            MerchantType merchantType = merchantTypeRepository.findById(Long.parseLong(typeArray[i]));
            if(isNotEmpty(merchantType)){
                MerchantOwnType mot = new MerchantOwnType();
                mot.setMerchant(merchant);
                mot.setMerchantType(merchantType);

                merchantOwnTypeRepository.save(mot);

            }
        }


        //创建  AuditStatus ,0 AuditStatus 并返回.
        MerchantAudit audit = new MerchantAudit();
        audit.setMerchant(merchant);
        audit.setUser(user);
        audit.setAuditStatus(Const.AuditStatus.create);
        audit = auditRepository.save(audit);

        return BeanMapper.map(audit,MerchantAuditVo.class);
    }

    /**
     * 查询我的审核信息
     * @param uid
     * @return
     * @throws BusinessException
     */
    @Override
    public List<MerchantAuditVo> getMyAuditInfo(Long uid) throws BusinessException {

        return BeanMapper.mapList(auditRepository.getMyAuditInfo(uid),MerchantAuditVo.class);
    }

    /**
     * 修改店铺信息
     * @return
     * @throws BusinessException
     */
    @Override
    public Merchant updateMerchantInfo(Long mid,String mctName, String tags, String stars, Integer consumerption, String shopHours, String addr, String mobile, Integer commentNum, String mainImage, String images, String longitude, String latitude, Double profits, Integer rebateType, Double rebate, Double rebateToMerchant, Double retateToParent1, Double retateToParent2, Double retateToParent3, Double retateToProvinceAgent, Double retateToCityAgent, Double retateToDistrictAgent, Double retateToAreaAgent,Integer areaId,Integer provinceId,Integer cityId,Integer district,
                                       String ownerRealName,String ownerIdcard,Integer licenceType,Integer licenceStatus,String licenceImage,
                                       Double tumover,Integer orderNum) throws BusinessException {

        Merchant m = merchantRepository.findById(mid,valid);
        if(isEmpty(m)){
            throw new BusinessException(Const.Code.MERCHANT_NOT_EXISTS,Const.Code.MERCHANT_NOT_EXISTS_MSG);
        }

        if(isNotEmpty(mctName)) m.setNickname(mctName);
        if(isNotEmpty(tags)) m.setTags(tags);
        if(isNotEmpty(stars)) m.setStars(stars);
        if(isNotEmpty(consumerption)) m.setConsumerption(consumerption);
        if(isNotEmpty(shopHours)) m.setShopHours(shopHours);
        if(isNotEmpty(addr)) m.setAddr(addr);
        if(isNotEmpty(mobile)) m.setMobile(mobile);
        if(isNotEmpty(commentNum)) m.setCommentNum(commentNum);
        if(isNotEmpty(mainImage)) m.setMainImage(mainImage);
        if(isNotEmpty(images)) m.setImages(images);
        if(isNotEmpty(longitude)) m.setLongitude(longitude);
        if(isNotEmpty(latitude)) m.setLatitude(latitude);
        if(isNotEmpty(profits)) m.setProfits(profits);
        if(isNotEmpty(rebateType)) m.setRebateType(rebateType);
        if(isNotEmpty(rebate)) m.setRebate(rebate);
        if(isNotEmpty(rebateToMerchant)) m.setRebateToMerchant(rebateToMerchant);
        if(isNotEmpty(retateToParent1)) m.setRetateToParent1(retateToParent1);
        if(isNotEmpty(retateToParent2)) m.setRetateToParent2(retateToParent2);
        if(isNotEmpty(retateToParent3)) m.setRetateToParent3(retateToParent3);
        if(isNotEmpty(retateToProvinceAgent)) m.setRetateToProvinceAgent(retateToProvinceAgent);
        if(isNotEmpty(retateToCityAgent)) m.setRetateToCityAgent(retateToCityAgent);
        if(isNotEmpty(retateToDistrictAgent)) m.setRetateToDistrictAgent(retateToDistrictAgent);
        if(isNotEmpty(retateToAreaAgent)) m.setRetateToAreaAgent(retateToAreaAgent);

        if(isNotEmpty(areaId)) m.setAreaId(areaId);
        if(isNotEmpty(provinceId)) m.setProvinceId(provinceId);
        if(isNotEmpty(cityId)) m.setCityId(cityId);
        if(isNotEmpty(district)) m.setDistrict(district);
        if(isNotEmpty(ownerRealName)) m.setOwnerRealName(ownerRealName);
        if(isNotEmpty(ownerIdcard)) m.setOwnerIdcard(ownerIdcard);
        if(isNotEmpty(licenceType)) m.setLicenceType(licenceType);
        if(isNotEmpty(licenceStatus)) m.setLicenceStatus(licenceStatus);
        if(isNotEmpty(licenceImage)) m.setLicenceImage(licenceImage);
        if(isNotEmpty(tumover)) m.setTumover(tumover);
        if(isNotEmpty(orderNum)) m.setOrderNum(orderNum);

        m = merchantRepository.save(m);


        return m;
    }

    /**
     * 商家分页获取粉丝列表
     * @param mid
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<OtherUserVo> getMyFans(Long mid,Pageable pageable) throws BusinessException {
        Merchant merchant = merchantRepository.findById(mid,valid);
        if(isEmpty(merchant)){
            throw new BusinessException(Const.Code.MERCHANT_NOT_EXISTS,Const.Code.MERCHANT_NOT_EXISTS_MSG);
        }

        Page<User> users = userRepository.findByMerchantId(mid,pageable);

        return p2pr(users,OtherUserVo.class);
    }

    /**
     * 获取我的门店
     * @param uid
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<MyMerchantVo> getMyMerchant(Long uid, Pageable pageable) throws BusinessException {
        Page<Merchant> merchants = merchantRepository.findMyMerchant(uid,pageable);
        return p2pr(merchants,MyMerchantVo.class);
    }

    /**
     * 设置门店地址
     * @param uid
     * @param mid
     * @param addr
     * @param longitude
     * @param latitude
     * @return
     * @throws BusinessException
     */
    @Override
    public MyMerchantVo updateMerchantInfos(Long uid,Long mid,Integer areaId,Integer provinceId,Integer cityId,Integer districtId,
                                            String nickname,String addr,String mobile,String shopHours,String types,String ownerRealName,String ownerIdCard,
                                            String idcardImage1,String idcardImage2,Integer licenceType,Integer licenceStatus,String licenceImage,String mainImage,String images,String longitude,String latitude ,Integer consumerption,Double profits ,Integer rebateType)throws BusinessException {
        Merchant merchant = merchantRepository.findByIdAndUserId(mid,uid);
        if(isEmpty(merchant)){
            throw new BusinessException(Const.Code.MERCHANT_NOT_EXISTS,Const.Code.MERCHANT_NOT_EXISTS_MSG);
        }

        //检查用户  账号 , 密码 , isMerchant
        User user = userRepository.findByIdAndStatus(uid,valid);
        user.setIdcardImage1(idcardImage1);
        user.setIdcardImage2(idcardImage2);
        userRepository.save(user);


        merchant.setNickname(nickname);

        merchant.setConsumerption(consumerption);

        merchant.setShopHours(shopHours);
        merchant.setAddr(addr);
        merchant.setMobile(mobile);

        merchant.setMainImage(mainImage);
        merchant.setImages(images);

        merchant.setLongitude(longitude);
        merchant.setLatitude(latitude);

        merchant.setProfits(profits);
        merchant.setRebateType(rebateType);


        merchant.setAreaId(areaId);
        merchant.setProvinceId(provinceId);
        merchant.setCityId(cityId);
        merchant.setDistrict(districtId);

        merchant.setOwnerRealName(ownerRealName);
        merchant.setOwnerIdcard(ownerIdCard);
        merchant.setLicenceType(licenceType);
        merchant.setLicenceStatus(licenceStatus);
        merchant.setLicenceImage(licenceImage);


        merchant = merchantRepository.save(merchant);


        //设置店铺分类
        String[] typeArray = types.split("\\|");
        for(int i = 0 ;i<typeArray.length;i++){
            MerchantType merchantType = merchantTypeRepository.findById(Long.parseLong(typeArray[i]));
            if(isNotEmpty(merchantType)){
                MerchantOwnType mot = new MerchantOwnType();
                mot.setMerchant(merchant);
                mot.setMerchantType(merchantType);

                merchantOwnTypeRepository.save(mot);

            }
        }

        return BeanMapper.map(merchant,MyMerchantVo.class);
    }
}
