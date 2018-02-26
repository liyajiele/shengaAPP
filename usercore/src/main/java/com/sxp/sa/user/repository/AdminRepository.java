package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.Admin;
import com.sxp.sa.user.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


/**
 * Created by sxp
 * on 2016/12/26.
 */
public interface AdminRepository extends PagingAndSortingRepository<Admin,Long>,JpaSpecificationExecutor<Admin> {



    @Query("from Admin a where a.user.username=?1 and a.status=1")
    Admin findByUsername(String username);

    @Query("from Admin a where a.id=?1 and a.status=1")
    Admin findByAdminId(Long aid);

    @Query("from Admin a where a.user.username=?1 and a.user.password=?2 and a.status=1")
    Admin findByUsernameAndPassword(String username,String password);

    @Query("from Admin a where a.user.id=?1 and a.status=1")
    Admin findByUserId(Long userId);
}
