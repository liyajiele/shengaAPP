package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.BaomingInfos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BaomingInfosRepository  extends PagingAndSortingRepository<BaomingInfos,Long>,JpaSpecificationExecutor<BaomingInfos> {

    @Query("select max(b.id) from BaomingInfos b ")
    Integer findMaxid();

    @Query("from BaomingInfos b  where b.createTime>=?1 and b.createTime<=?2")
    Page<BaomingInfos> findAll(Long startTime,Long endTime,Pageable pageable);
}
