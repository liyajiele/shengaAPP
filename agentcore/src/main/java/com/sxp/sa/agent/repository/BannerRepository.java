package com.sxp.sa.agent.repository;


import com.sxp.sa.agent.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/2/24.
 */
public interface BannerRepository extends PagingAndSortingRepository<Banner,Long>,JpaSpecificationExecutor<Banner> {

    @Query("from Banner b where b.status=1 and b.agentId is null and b.districtId is null Order By b.priority DESC ,b.id DESC")
    List<Banner> findGuangFangBannerList();

    @Query("from Banner b where b.status=1 and b.districtId=?1 order by b.priority DESC,b.id desc")
    List<Banner> findBannerByDistrict(Integer districtId);



    @Query("from Banner b where b.status=?1")
    Page<Banner> adminFindBannerList(Integer status, Pageable pageable);

    @Query("from Banner b where b.status=?1 and b.districtId=?2")
    Page<Banner> adminFindBannerList(Integer status, Integer districtId, Pageable pageable);
}
