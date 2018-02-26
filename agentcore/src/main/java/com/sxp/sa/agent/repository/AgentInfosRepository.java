package com.sxp.sa.agent.repository;

import com.sxp.sa.agent.entity.AgentInfos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgentInfosRepository  extends PagingAndSortingRepository<AgentInfos,Long>,JpaSpecificationExecutor< AgentInfos> {


    @Query("from AgentInfos ai where ai.agentLevel=?2 and ai.district.tId=?1 and ai.status=1")
    AgentInfos findByDistrictIdAndAgentLevel(Integer tid,Integer level);

    @Query("from AgentInfos ai where  ai.district.tId=?2 and ai.user.id=?1 and ai.status=1")
    AgentInfos findByDistrictAndUserId(Long uid,Integer tid );

    @Query("from AgentInfos ai where  ai.district.tId=?1 and ai.status=1")
    AgentInfos findByDistrictId(Integer tid);


    @Query("from AgentInfos ai where ai.status=1")
    Page<AgentInfos> findByPage(Pageable pageable);

    @Query("from AgentInfos ai where ai.status=1 and ai.user.nickname like ?1 or ai.district.name like ?1")
    Page<AgentInfos> findByPageAndSearchStr(String searchStr,Pageable pageable);

    @Query("from AgentInfos ai where ai.status=1 and ai.user.id=?1")
    Page<AgentInfos> findAgentListByAgentUid(Long uid,Pageable pageable);
}
