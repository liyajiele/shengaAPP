package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.AdminRole;
import com.sxp.sa.user.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface AdminRoleRepository extends PagingAndSortingRepository<AdminRole,Long>,JpaSpecificationExecutor<AdminRole> {


    @Query("select ar.role from AdminRole ar where ar.status=1 and ar.admin.id=?1")
    List<Role> findByAdminId(Long aid);


    @Transactional
    @Modifying
    @Query("update AdminRole ar set ar.status=2,ar.modifyUser=?2,ar.modifyDesc='del' where ar.role.id=?1")
    void delByRoleId(Long roleId,Long delUserId);


    @Query("from AdminRole ar where ar.status=1 and ar.admin.id=?1 and ar.role.id=?2")
    AdminRole findByAdminIdAndRoleId(Long adminId,Long roleId);

}
