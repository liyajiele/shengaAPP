package com.sxp.sa.merchant.repository;

import com.sxp.sa.merchant.entity.MerchantAudit;
import com.sxp.sa.merchant.vo.MerchantAuditVo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MerchantAuditRepository extends PagingAndSortingRepository<MerchantAudit,Long>,JpaSpecificationExecutor<MerchantAudit> {

    @Query("from MerchantAudit ma where ma.status=1 and ma.user.id=?1")
    List<MerchantAuditVo> getMyAuditInfo(Long uid);
}
