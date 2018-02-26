package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.Account;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by dell on 2017/7/18.
 */
public interface AccountRepository extends PagingAndSortingRepository<Account,Long>,JpaSpecificationExecutor<Account>{


    @Query("from Account ac where ac.user.id=?1 and ac.status=1 and ac.user.status=1")
    Account findByUser(Long uid);
}
