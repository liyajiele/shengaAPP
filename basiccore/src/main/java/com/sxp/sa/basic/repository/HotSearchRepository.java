package com.sxp.sa.basic.repository;

import com.sxp.sa.basic.entity.HotSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HotSearchRepository  extends PagingAndSortingRepository<HotSearch,Long>,JpaSpecificationExecutor<HotSearch> {

    @Query("from HotSearch hs where hs.status=1 and hs.city.id=?1")
    Page<HotSearch> findCityHotSearch(Integer cityId, Pageable pageable);

    @Query("from HotSearch hs where hs.status=1 and hs.district.id=?1")
    Page<HotSearch> findDistrictHotSearch(Integer districtId, Pageable pageable);


}
