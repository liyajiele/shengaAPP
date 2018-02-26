package com.sxp.sa.order.repository;

import com.sxp.sa.order.entity.RedBagPool;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RedBagPoolRepository  extends PagingAndSortingRepository<RedBagPool,Long>,JpaSpecificationExecutor<RedBagPool> {

    @Query("from RedBagPool r where r.status=?1")
    List<RedBagPool> findPool(Integer status);
}
