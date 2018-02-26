package com.sxp.sa.merchant.repository;

import com.sxp.sa.merchant.entity.Collect;
import com.sxp.sa.merchant.entity.Merchant;
import com.sxp.sa.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CollectRepository extends PagingAndSortingRepository<Collect,Long>,JpaSpecificationExecutor<Collect> {

    //是否收藏
    @Query("select count(c.id) from Collect c where c.user=?1 and c.merchant=?2 and c.collectStatus=?3")
    Integer queryCollectNum(User user, Merchant merchant,Integer collectStatus);


    @Query("select count(c.id) from Collect c where c.user.id=?1  and  c.collectStatus=1 and c.status=1")
    Integer queryMyCollectNum(Long uid);


    @Query("select c.merchant from Collect c where c.user.id=?1 and c.collectStatus=1 and c.status=1")
    Page<Merchant> queryMyCollects(Long uid, Pageable pageable);

    @Query("from Collect c where c.user.id=?1 and c.merchant.id=?2 and c.status=1")
    Collect queryCollectByUserAndMerchant(Long uid,Long mid);
}
