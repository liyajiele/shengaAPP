package com.sxp.sa.merchant.repository;

import com.sxp.sa.merchant.entity.About;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AboutRepository extends PagingAndSortingRepository<About,Long>,JpaSpecificationExecutor<About> {

    @Query("from About a where a.status=1 and a.osType=?1")
    About findByOSType(Integer osType);
}
