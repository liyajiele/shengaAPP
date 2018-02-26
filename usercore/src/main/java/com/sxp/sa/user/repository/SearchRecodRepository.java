package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.SearchRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SearchRecodRepository extends PagingAndSortingRepository<SearchRecord,Long>,JpaSpecificationExecutor<SearchRecord> {

    @Query("from SearchRecord sr where sr.status=1 and sr.user.id=?1 order by sr.searchTimes desc")
    Page<SearchRecord> findMySearchRecord(Long uid, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update SearchRecord sr set sr.status=0 where sr.user.id=?1")
    void cleanMySearchRecord(Long uid);

    @Query("from SearchRecord sr where sr.status=1 and sr.user.id=?1 and sr.content=?2")
    SearchRecord findBySearchStr(Long uid,String searchStr);

}
