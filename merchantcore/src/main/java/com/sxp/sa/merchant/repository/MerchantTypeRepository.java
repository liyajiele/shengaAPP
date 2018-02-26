package com.sxp.sa.merchant.repository;

import com.sxp.sa.merchant.entity.Merchant;
import com.sxp.sa.merchant.entity.MerchantType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MerchantTypeRepository extends PagingAndSortingRepository<MerchantType,Long>,JpaSpecificationExecutor<MerchantType> {

    @Query("from MerchantType mt where mt.status=?1")
    List<MerchantType> findAll(Integer status);

    @Query("from MerchantType mt where mt.status=1 and mt.id=?1")
    MerchantType findById(Long id);
}
