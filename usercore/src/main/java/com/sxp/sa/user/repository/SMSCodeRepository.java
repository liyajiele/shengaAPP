package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.SMSCode;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SMSCodeRepository extends PagingAndSortingRepository<SMSCode,Long>,JpaSpecificationExecutor<SMSCode> {

    @Query("from SMSCode c where c.mobile=?1 and code=?2 and type=?3 and status=1")
    SMSCode findCode(String mobile,String code,String type);
}
