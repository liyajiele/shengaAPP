package com.sxp.sa.user.repository;


import com.sxp.sa.user.entity.Resource;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by sxp
 * on 2016/12/26.
 */
public interface ResourceRepository extends PagingAndSortingRepository<Resource,Long>,JpaSpecificationExecutor<Resource> {


    @Query("from Resource r where r.status=1")
    List<Resource> findAll();


    @Query("from Resource r where r.id=?1 and r.status=?2")
    Resource findByIdAndStatus(Long rid,Integer status);



    @Modifying
    @Transactional
    @Query("update Resource r set r.status=0,r.modifyUser=?2,r.modifyDesc='del' where r.id=?1")
    void deleteById(Long resourceId,Long aid);
}



