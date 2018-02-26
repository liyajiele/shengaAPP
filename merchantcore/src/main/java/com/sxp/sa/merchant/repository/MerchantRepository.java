package com.sxp.sa.merchant.repository;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.merchant.entity.Merchant;
import com.sxp.sa.merchant.vo.MerchantVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MerchantRepository extends PagingAndSortingRepository<Merchant,Long>,JpaSpecificationExecutor<Merchant> {


    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(m.id,m.createTime,m.nickname,m.stars,m.addr,m.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-m.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(m.latitude*pi()/180)* pow(sin( (?1*pi()/180-m.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from Merchant m ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchant(Double longitude,Double latitude,Pageable pageable);

    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(m.id,m.createTime,m.nickname,m.stars,m.addr,m.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-m.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(m.latitude*pi()/180)* pow(sin( (?1*pi()/180-m.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from Merchant m where m.nickname like ?3 ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchantNameLike(Double longitude,Double latitude,String nickname,Pageable pageable);





    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(m.id,m.createTime,m.nickname,m.stars,m.addr,m.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-m.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(m.latitude*pi()/180)* pow(sin( (?1*pi()/180-m.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from Merchant m where m.district=?3 and ?3 is not null ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchantByDistrict(Double longitude,Double latitude,Integer districtId,Pageable pageable);

    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(m.id,m.createTime,m.nickname,m.stars,m.addr,m.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-m.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(m.latitude*pi()/180)* pow(sin( (?1*pi()/180-m.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from Merchant m where m.nickname like ?3 and m.district=?4 and ?4 is not null ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchantNameLikeAndDistrict(Double longitude,Double latitude,String nickname,Integer districtId,Pageable pageable);




    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(m.id,m.createTime,m.nickname,m.stars,m.addr,m.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-m.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(m.latitude*pi()/180)* pow(sin( (?1*pi()/180-m.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from Merchant m where m.cityId=?3 and ?3 is not null ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchantByCity(Double longitude,Double latitude,Integer cityId,Pageable pageable);

    @Query("select new com.sxp.sa.merchant.vo.MerchantVo(m.id,m.createTime,m.nickname,m.stars,m.addr,m.mainImage," +
            " ROUND(6378.138*2*asin(sqrt(pow(sin( (?2*pi()/180-m.latitude*pi()/180)/2),2)+cos(?2*pi()/180)*cos(m.latitude*pi()/180)* pow(sin( (?1*pi()/180-m.longitude*pi()/180)/2),2)))*1000)  as distance) " +
            " from Merchant m where m.nickname like ?3 and m.cityId=?4 and ?4 is not null ORDER BY distance DESC  ")
    Page<MerchantVo> nearByMerchantNameLikeAndCity(Double longitude,Double latitude,String nickname,Integer cityId,Pageable pageable);





    @Query("from Merchant m where m.id=?1 and m.status=?2")
    Merchant findById(Long id,Integer status);
    @Query("from Merchant m where m.id=?1 and m.uid=?2 and m.status=?3")
    Merchant findByIdAndUser(Long id,Long uid,Integer status);

    @Query("from Merchant m where m.id=?1 and m.uid=?2")
    Merchant findByIdAndUserId(Long id,Long uid);

    @Query("from Merchant m where m.uid=?1 and m.status=1")
    Page<Merchant> findMyMerchant(Long uid,Pageable pageable);



    //区域内的商家数
    @Query("select count(m.id) from Merchant m where m.status=1 and m.district=?1")
    Integer findCountMctNumsByDistrict(Integer districtId);

}
