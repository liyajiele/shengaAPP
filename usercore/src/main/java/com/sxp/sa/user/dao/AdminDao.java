package com.sxp.sa.user.dao;


import com.sxp.sa.user.entity.Admin;

/**
 * Created by Administrator on 2017/2/24.
 */
public interface AdminDao {

//    List<Role> findRolesByUsername(String username);

    Admin findByUsername(String username);
}
