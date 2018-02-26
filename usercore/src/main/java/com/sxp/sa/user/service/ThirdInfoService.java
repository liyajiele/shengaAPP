package com.sxp.sa.user.service;

import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.vo.ThirdInfoVo;

/**
 * Created by Administrator on 2017/2/19.
 */
public interface ThirdInfoService {

    /**
     * 查询三方信息是否存在
     * @param openId
     * @param thirdType
     * @return
     * @throws BusinessException
     */
    Long isExists(String openId, String thirdType);

    /**
     * 保存三方信息
     * @param user
     * @param nickname
     * @param avatar
     * @param groupId
     * @param thirdType
     * @param openId
     * @param unionId
     * @param thirdName
     * @param subscribe
     * @return
     * @throws BusinessException
     */
    ThirdInfoVo addThirdInfo(User user, String nickname, String avatar, Integer groupId, String thirdType, String openId, String unionId, String thirdName, Integer subscribe)throws BusinessException;

    /**
     * 更新三方信息
     * @param user
     * @param nickname
     * @param avatar
     * @param groupId
     * @param thirdType
     * @param openId
     * @param unionId
     * @param thirdName
     * @param subscribe
     * @return
     * @throws BusinessException
     */
    ThirdInfoVo updateThirdInfo(Long thirdInfoId, User user, String nickname, String avatar, Integer groupId, String thirdType, String openId, String unionId, String thirdName, Integer subscribe)throws BusinessException;


    /**
     * 微信登录时更新用户信息
     * @param openid
     * @param thirdType
     * @param thirdName
     * @param userInfoResponse
     * @return
     */
    ThirdInfoVo updateThirdInfo(String openid, String thirdType, String thirdName, GetUserInfoResponse userInfoResponse);

    /**
     * 获取信用户或者unionid关联用户
     * @param unionId
     * @param avatar
     * @param nickname
     * @return
     * @throws BusinessException
     */
    User getEmptyOrLinkUser(String unionId, String avatar, String nickname)throws  BusinessException;
}
