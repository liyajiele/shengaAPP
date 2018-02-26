package com.sxp.sa.agent.repository;

import com.sxp.sa.agent.entity.Agent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgentRepository extends PagingAndSortingRepository<Agent,Long>,JpaSpecificationExecutor<Agent> {



}
