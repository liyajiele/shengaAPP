package com.sxp.sa.user.dao.impl;

import com.sxp.sa.basic.entity.BaseDao;
import com.sxp.sa.user.dao.UserDao;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by miss on 2017/2/17.
 */

@Component
public class UserDaoImpl extends BaseDao implements UserDao {

    @Autowired
    private UserRepository userRepository;

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username,Integer status) {
        return userRepository.findByUsername(username,status);
    }

    /**
     * 通过用户id查询用户信息
     * @param uid
     * @param status
     * @return
     */
    @Override
    public User findById(Long uid, Integer status) {
        return userRepository.findByIdAndStatus(uid,status);
    }


    /**
     * 模糊查找昵称
     * @param nickname
     * @param status
     * @return
     */
    @Override
    public List<User> findByNicknamelike(String nickname, Integer status) {
        return userRepository.findByNicknamelist( nickname,  status) ;
    }

}
