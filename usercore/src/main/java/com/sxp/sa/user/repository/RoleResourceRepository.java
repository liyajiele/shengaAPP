package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.Resource;
import com.sxp.sa.user.entity.Role;
import com.sxp.sa.user.entity.RoleResource;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface RoleResourceRepository extends PagingAndSortingRepository<RoleResource,Long>,JpaSpecificationExecutor<RoleResource> {

    @Query("select distinct rr.role from RoleResource rr where rr.status=1 and rr.resource.id=?1")
    List<Role> findByResourceId(Long resourceId);


    @Query("select distinct  rr.resource from RoleResource rr where rr.status=1 and rr.role.id=?1 and rr.resource.permission=?2")
    List<Resource> findResourceByRoleIdAndPer(Long roleId,String permission);



    @Query("select distinct  rr.resource from RoleResource rr where rr.status=1 and rr.role.id=?1 ")
    List<Resource> findResourceByRoleIdAndPer(Long roleId);

    @Query("from RoleResource rr where rr.status=1 and rr.role.id=?1 and rr.resource.id=?2")
    RoleResource findByRoleIdAndResourceId(Long roleId,Long resoucesId);

    @Modifying
    @Transactional
    @Query("update RoleResource rr set rr.status=2,rr.modifyUser=?2,rr.modifyDesc='del' where rr.resource.id=?1")
    void delByResourceId(Long resourceId,Long modifyUserId);

    @Modifying
    @Transactional
    @Query("update RoleResource rr set rr.status=2,rr.modifyUser=?2,rr.modifyDesc='del' where rr.role.id=?1")
    void delByRoleId(Long roleId,Long modifyUserId);
}
