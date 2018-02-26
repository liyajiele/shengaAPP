package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.UrlFilter;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by sxp
 * on 2016/12/29.
 */
public interface UrlFilterRepository extends PagingAndSortingRepository<UrlFilter,Long>,JpaSpecificationExecutor<UrlFilter> {

    @Query("from UrlFilter uf where uf.id=?1 and status=1")
    UrlFilter findById(Long id);

    @Query("from UrlFilter uf where uf.status=1")
    List<UrlFilter> findAllUrlFilter();
}
