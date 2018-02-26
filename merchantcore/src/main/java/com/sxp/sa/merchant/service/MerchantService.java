package com.sxp.sa.merchant.service;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.merchant.entity.Merchant;
import com.sxp.sa.merchant.entity.MerchantType;
import com.sxp.sa.merchant.entity.MyMerchantVo;
import com.sxp.sa.merchant.vo.MerchantAuditVo;
import com.sxp.sa.merchant.vo.MerchantDetailVo;
import com.sxp.sa.merchant.vo.MerchantVo;
import com.sxp.sa.user.vo.OtherUserVo;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MerchantService {

    Pager<MerchantVo> nearByMerchant(Long uid,Long typeId,Integer districtId,String searchStr, Pageable pageable)throws BusinessException;

    List<MerchantType> allMerchantTypes()throws BusinessException;

    MerchantDetailVo getMerchantDetail(Long mid,Long uid)throws  BusinessException;










    MerchantAuditVo createMerchant(Long aid,Long uid,Integer areaId,Integer provinceId,Integer cityId,Integer districtId,
                                   String nickname,String addr,String mobile,String shopHours,String types,String ownerRealName,String ownerIdCard,
                                   String idcardImage1,String idcardImage2,Integer licenceType,Integer licenceStatus,String licenceImage,String mainImage,String images,String longitude,String latitude,
                                   Integer consumerption,Double profits,Integer rebateType )throws BusinessException;

    Merchant updateMerchantInfo(Long mid,String mctName,String tags,String stars,Integer consumerption,String shopHours,String addr,
            String mobile,Integer commentNum,String mainImage,String images,String longitude,String latitude,
                                Double profits,Integer rebateType,Double rebate,Double rebateToMerchant,Double retateToParent1,
                                Double retateToParent2,Double retateToParent3,Double retateToProvinceAgent,Double retateToCityAgent,
                                Double retateToDistrictAgent,Double retateToAreaAgent,Integer areaId,Integer provinceId,Integer cityId,Integer district,
                                String ownerRealName,String ownerIdcard,Integer licenceType,Integer licenceStatus,String licenceImage,
                                Double tumover,Integer orderNum)throws BusinessException;






    List<MerchantAuditVo> getMyAuditInfo(Long uid)throws BusinessException;


    Pager<OtherUserVo> getMyFans(Long mid,Pageable pageable)throws BusinessException;

    Pager<MyMerchantVo> getMyMerchant(Long uid, Pageable pageable)throws BusinessException;


    MyMerchantVo updateMerchantInfos(Long uid,Long mid,Integer areaId,Integer provinceId,Integer cityId,Integer districtId,
                                     String nickname,String addr,String mobile,String shopHours,String types,String ownerRealName,String ownerIdCard,
                                     String idcardImage1,String idcardImage2,Integer licenceType,Integer licenceStatus,String licenceImage,String mainImage,String images,String longitude,String latitude ,Integer consumerption,Double profits ,Integer rebateType)throws BusinessException;

}
