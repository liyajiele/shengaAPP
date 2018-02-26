package com.sxp.sa.merchant.repository;

import com.sxp.sa.merchant.entity.MerchantOwnType;
import com.sxp.sa.merchant.entity.MerchantType;
import com.sxp.sa.merchant.vo.MerchantVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MerchantOwnTypeRepository extends PagingAndSortingRepository<MerchantOwnType,Long>,JpaSpecificationExecutor<MerchantOwnType> {

    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(mot.merchant.id,mot.merchant.createTime,mot.merchant.nickname,mot.merchant.stars,mot.merchant.addr,mot.merchant.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-mot.merchant.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(mot.merchant.latitude*pi()/180)* pow(sin( (?1*pi()/180-mot.merchant.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from MerchantOwnType mot where mot.merchant.status=1 and  mot.merchantType.id=?3 and ?1 is Not Null and ?2 is not null ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchant(Double longitude, Double latitude,Long typeId, Pageable pageable);


    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(mot.merchant.id,mot.merchant.createTime,mot.merchant.nickname,mot.merchant.stars,mot.merchant.addr,mot.merchant.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-mot.merchant.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(mot.merchant.latitude*pi()/180)* pow(sin( (?1*pi()/180-mot.merchant.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from MerchantOwnType mot where mot.merchant.status=1 and  mot.merchantType.id=?3 and mot.merchant.nickname like ?4 and ?1 is Not Null and ?2 is not null ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchantNameLike(Double longitude, Double latitude,Long typeId,String nickname, Pageable pageable);





    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(mot.merchant.id,mot.merchant.createTime,mot.merchant.nickname,mot.merchant.stars,mot.merchant.addr,mot.merchant.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-mot.merchant.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(mot.merchant.latitude*pi()/180)* pow(sin( (?1*pi()/180-mot.merchant.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from MerchantOwnType mot where mot.merchant.status=1 and mot.merchant.district=?4 and  mot.merchantType.id=?3 and ?1 is Not Null and ?2 is not null ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchantByDistrict(Double longitude, Double latitude,Long typeId,Integer district, Pageable pageable);


    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(mot.merchant.id,mot.merchant.createTime,mot.merchant.nickname,mot.merchant.stars,mot.merchant.addr,mot.merchant.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-mot.merchant.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(mot.merchant.latitude*pi()/180)* pow(sin( (?1*pi()/180-mot.merchant.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from MerchantOwnType mot where mot.merchant.status=1 and  mot.merchantType.id=?3 and mot.merchant.district=?5 and mot.merchant.nickname like ?4 and ?1 is Not Null and ?2 is not null ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchantNameLikeAndDistrict(Double longitude, Double latitude,Long typeId,String nickname,Integer district, Pageable pageable);





    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(mot.merchant.id,mot.merchant.createTime,mot.merchant.nickname,mot.merchant.stars,mot.merchant.addr,mot.merchant.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-mot.merchant.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(mot.merchant.latitude*pi()/180)* pow(sin( (?1*pi()/180-mot.merchant.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from MerchantOwnType mot where mot.merchant.status=1 and mot.merchant.cityId=?4 and  mot.merchantType.id=?3 and ?1 is Not Null and ?2 is not null ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchantByCity(Double longitude, Double latitude,Long typeId,Integer cityId, Pageable pageable);


    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(mot.merchant.id,mot.merchant.createTime,mot.merchant.nickname,mot.merchant.stars,mot.merchant.addr,mot.merchant.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-mot.merchant.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(mot.merchant.latitude*pi()/180)* pow(sin( (?1*pi()/180-mot.merchant.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from MerchantOwnType mot where mot.merchant.status=1 and  mot.merchantType.id=?3 and mot.merchant.cityId=?5 and mot.merchant.nickname like ?4 and ?1 is Not Null and ?2 is not null ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchantNameLikeAndCity(Double longitude, Double latitude,Long typeId,String nickname,Integer cityId, Pageable pageable);








    @Query("from MerchantOwnType mot where mot.merchant.id=?1 and mot.merchant.status=1 and  mot.status=1")
    List<MerchantOwnType> findTypeByMerchant(Long merchantId);
}
