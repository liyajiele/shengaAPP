package com.sxp.sa.order.repository;

import com.sxp.sa.order.entity.AccountRecord;
import com.sxp.sa.order.entity.MerchantAccountRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MerchantAccountRecordRepository extends PagingAndSortingRepository<MerchantAccountRecord,Long>,JpaSpecificationExecutor<MerchantAccountRecord> {

    @Query("from MerchantAccountRecord ar where ar.status=1 and ar.merchant.id=?1 and ar.createTime>=?2 and ar.createTime<=?3 order by ar.id desc")
    Page<MerchantAccountRecord> getMyAllRecord(Long uid, Long startTime, Long endTime, Pageable pageable);

    @Query("from MerchantAccountRecord ar where ar.status=1 and ar.merchant.id=?1 and ar.createTime>=?2 and ar.createTime<=?3 and ar.recordType=?4  order by ar.id desc")
    Page<MerchantAccountRecord> getMyRecordByType(Long uid, Long startTime, Long endTime, Integer type, Pageable pageable);
}
