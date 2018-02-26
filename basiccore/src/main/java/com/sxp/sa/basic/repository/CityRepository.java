package com.sxp.sa.basic.repository;

import com.sxp.sa.basic.entity.City;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface CityRepository extends PagingAndSortingRepository<City,Long>,JpaSpecificationExecutor<City> {

    /**
     * 根据城市的名字查询 城市信息
     * @param name
     * @return
     */
    @Query("from City c where c.name=?1  ")
    City findByName(String name);


    @Query("from City c where c.tId=?1")
    City findById(Integer cityId);

    /**
     * 查询省内所有市
     * @param provinceId
     * @return
     */
    @Query("from City c where c.provinceId=?1  order by c.name ASC ")
    List<City>  findByProvinceIdAllCity(Integer provinceId);

    @Query("from City c order by c.name ASC")
    List<City> findAll();

}
