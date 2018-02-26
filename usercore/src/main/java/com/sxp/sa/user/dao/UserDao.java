package com.sxp.sa.user.dao;

import com.sxp.sa.user.entity.User;

import java.util.List;

/**
 * Created by miss on 2017/2/17.
 */
public interface UserDao {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username, Integer status);

    /**
     * 根据id查询用户信息
     * @param uid
     * @param status
     * @return
     */
    User findById(Long uid ,Integer status);


    List<User> findByNicknamelike(String nickname,Integer status);



}
