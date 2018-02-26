package com.sxp.sa.order.repository;

import com.sxp.sa.order.entity.DuibaOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DuibaOrderRepository extends PagingAndSortingRepository<DuibaOrder,Long>,JpaSpecificationExecutor<DuibaOrder> {




    @Query("from DuibaOrder d where d.appKey=?1 and d.orderNum=?2 and d.result=2 and d.status=1")
    DuibaOrder findByappKeyAndOrderNum(String appkey,String orderNum);
}
