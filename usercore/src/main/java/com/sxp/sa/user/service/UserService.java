package com.sxp.sa.user.service;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.vo.OtherUserVo;
import com.sxp.sa.user.vo.SearchRecordVo;
import com.sxp.sa.user.vo.UserVo;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by miss on 2017/2/17.
 */
public interface UserService {

    void updateLocation(Long uid,String longitude,String latitude);


    Pager<SearchRecordVo> findMySearchRecord(Long uid, Pageable pageable)throws BusinessException;

    Boolean cleanMySearchRecord(Long uid)throws BusinessException;


    UserVo getUserInfo(Long uid)throws BusinessException;

    Map<String,Object> getMyChildNums(Long uid)throws BusinessException;

    Pager<OtherUserVo> getMyChild(Long uid,Integer childType,Pageable pageable)throws BusinessException;


    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    UserVo login(String username,String password)throws  BusinessException;

    /**
     * 忘记密码，根据短信验证码修改密码
     * @param username
     * @param newPassword
     * @param code
     * @return
     */
    UserVo forgetUserPassword(String username,String newPassword,String code) throws BusinessException;



    /**
     *
     * @return
     * @throws BusinessException
     */
    User registerEmptyUser(String nickname, String avatar)throws BusinessException;

    /**
     * 仅用于微信扫代餐二维码绑定上级
     * @param curUser
     * @param parentId
     * @return
     */
    User bindParent(User curUser,Long parentId);


    String getToken(Long id);


    /**
     * 兑吧登录
     * @param uid
     * @return
     * @throws BusinessException
     */
    String duibaLogin(Long uid,String redirect)throws BusinessException;
}
