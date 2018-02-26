package com.sxp.sa.order.repository;

import com.sxp.sa.order.entity.AgentAccountRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgentAccountRecordRepository extends PagingAndSortingRepository<AgentAccountRecord,Long>,JpaSpecificationExecutor<AgentAccountRecord> {
}
