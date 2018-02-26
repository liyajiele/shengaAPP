package com.sxp.sa.basic.repository;

import com.sxp.sa.basic.entity.Province;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface ProvinceRepository extends PagingAndSortingRepository<Province,Long>,JpaSpecificationExecutor<Province> {
    /**
     * 查询区域下所有的省
     * @param areaId
     * @return
     */
    @Query("from Province p where p.areaId=?1 order by p.name ASC")
    List<Province> findByAreaIdAllProvince(Integer areaId);

    List<Province> findAll();

    /**
     * 根据省名，查询省的信息
     * @param name
     * @return
     */
    @Query("from Province p where p.name=?1 order by p.name ASC")
    Province findByName(String name);
}
