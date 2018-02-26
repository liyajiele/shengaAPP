package com.sxp.sa.user.repository;


import com.sxp.sa.user.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by sxp
 * on 2016/12/26.
 */
public interface RoleRepository extends PagingAndSortingRepository<Role,Long>,JpaSpecificationExecutor<Role> {


    @Query("from Role r where r.status=?1")
    List<Role> findAll(Integer status);

    @Query("from Role r where r.status=?2 and r.id=?1")
    Role findByIdAndStatus(Long roleId,Integer status);
}
