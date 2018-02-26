package com.sxp.sa.basic.repository;

import com.sxp.sa.basic.entity.Area;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface AreaRepository extends PagingAndSortingRepository<Area,Long>,JpaSpecificationExecutor<Area> {

    /**
     * 查询所有区域
     * @return
     */
    @Query("from Area a order by a.name")
    List<Area> findAllArea();

    /**
     * 根据区域名字查询，区域信息
     * @param name
     * @return
     */
    @Query("from Area a where a.name=?1")
    Area findByName(String name);
}
