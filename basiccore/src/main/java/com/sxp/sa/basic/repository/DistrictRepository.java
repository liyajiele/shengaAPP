package com.sxp.sa.basic.repository;

import com.sxp.sa.basic.entity.District;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface DistrictRepository extends PagingAndSortingRepository<District,Long>,JpaSpecificationExecutor<District> {

    /**
     * 查询市内所有的区
     * @param cityId
     * @return
     */
    @Query("from District d where d.cityId=?1  order by d.name ASC ")
    List<District> findByCityIdAllDistrict(Integer cityId);


    /**
     * 区名字 查询市信息
     * @param name
     * @return
     */
    @Query("from District d where d.name=?1 and d.cityId=?2 ")
    District findByNameAndCityId(String name,Integer cityId);


    @Query("from District d where d.tId=?1")
    District findById(Integer id);

}
