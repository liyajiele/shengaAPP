package com.sxp.sa.user.dao.impl;

import com.sxp.sa.basic.entity.BaseDao;
import com.sxp.sa.user.dao.AdminDao;
import com.sxp.sa.user.entity.Admin;
import com.sxp.sa.user.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/2/24.
 */
@Component
public class AdminDaoImpl extends BaseDao implements AdminDao {

    @Autowired
    private AdminRepository adminRepository;


//    @Override
//    public List<Role> findRolesByUsername(String username) {
//        return adminRepository.findRolesByUsername(username);
//    }


    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
