package com.sxp.sa.merchant.repository;

import com.sxp.sa.merchant.entity.NewsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NewsInfoRepository extends PagingAndSortingRepository<NewsInfo,Long>,JpaSpecificationExecutor<NewsInfo> {

    @Query("from NewsInfo ni where ni.status=1 order by ni.id desc")
    Page<NewsInfo> findInfosByPage(Pageable pageable);


    @Query("from NewsInfo ni where ni.status=1 and ni.newsType=?1 order by ni.id desc")
    Page<NewsInfo> getInfosByType(Integer infosType,Pageable pageable);
}
