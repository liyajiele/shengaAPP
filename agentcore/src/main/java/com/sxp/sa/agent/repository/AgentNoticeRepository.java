package com.sxp.sa.agent.repository;

import com.sxp.sa.agent.entity.AgentNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgentNoticeRepository extends PagingAndSortingRepository<AgentNotice,Long>,JpaSpecificationExecutor<AgentNotice> {



    @Query("from AgentNotice an where an.status=?1 order by an.id desc")
    Page<AgentNotice> findByStatus(Integer status, Pageable pageable);

    @Query("from AgentNotice an where an.id=?1")
    AgentNotice findById(Long  id);
}

