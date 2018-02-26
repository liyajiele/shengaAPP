package com.sxp.sa.basic.repository;

import com.sxp.sa.basic.entity.HotCity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface HotCityRepository extends PagingAndSortingRepository<HotCity,Long>,JpaSpecificationExecutor<HotCity> {

    /**
     * 查询所有热门城市
     * @return
     */
    @Query("from HotCity h order by h.city ")
    List<HotCity> findAllHotCity();
}
