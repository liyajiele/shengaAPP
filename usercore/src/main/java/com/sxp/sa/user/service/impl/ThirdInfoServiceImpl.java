package com.sxp.sa.user.service.impl;

import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.basic.utils.Identities;
import com.sxp.sa.user.entity.ThirdInfo;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.ThirdInfoRepository;
import com.sxp.sa.user.service.ThirdInfoService;
import com.sxp.sa.user.service.UserService;
import com.sxp.sa.user.vo.ThirdInfoVo;
import com.sxp.sa.user.vo.UserVo;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sxp.sa.basic.utils.Util.isEmpty;
import static com.sxp.sa.basic.utils.Util.isNotEmpty;


/**
 * Created by Administrator on 2017/2/19.
 */

@Service
public class ThirdInfoServiceImpl extends BaseService implements ThirdInfoService {


    @Autowired
    private ThirdInfoRepository thirdInfoDao;

    @Autowired
    private UserService userService;

    @Autowired
    private Cache<String,String> tokenCache;


    /**
     * 查询三方信息是否存在
     * @param openId
     * @param thirdType
     * @return
     */
    @Override
    public Long isExists(String openId, String thirdType){

        ThirdInfo thirdInfo = thirdInfoDao.findByOpenIdAndThirdType(openId,thirdType);
        if(isNotEmpty(thirdInfo)){
            return thirdInfo.getId();
        }else{
            return null;
        }
    }

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
    @Override
    public ThirdInfoVo addThirdInfo(User user, String nickname, String avatar, Integer groupId, String thirdType, String openId, String unionId, String thirdName, Integer subscribe) throws BusinessException {

        if(isEmpty(nickname)){
            nickname = "";
        }
        ThirdInfo thirdInfo = new ThirdInfo(user,nickname,avatar,groupId,thirdType,openId,unionId,thirdName,subscribe);

        ThirdInfoVo thirdInfoVo = BeanMapper.map(thirdInfoDao.save(thirdInfo),ThirdInfoVo.class);

        return thirdInfoVo;
    }

    /**
     * 更新三方信息
     * @param thirdInfoId
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
    @Override
    public ThirdInfoVo updateThirdInfo(Long thirdInfoId, User user, String nickname, String avatar, Integer groupId, String thirdType, String openId, String unionId, String thirdName, Integer subscribe) throws BusinessException {

        ThirdInfo thirdInfo = thirdInfoDao.findById(thirdInfoId);
        if(isNotEmpty(user)) thirdInfo.setUser(user);
        if(isNotEmpty(nickname)) thirdInfo.setNickname(nickname);
        if(isNotEmpty(avatar)) thirdInfo.setAvatar(avatar);
        thirdInfo.setGroupId(groupId);
        if(isNotEmpty(thirdType)) thirdInfo.setThirdType(thirdType);
        if(isNotEmpty(openId)) thirdInfo.setOpenId(openId);
        if(isNotEmpty(unionId)) thirdInfo.setUnionId(unionId);
        if(isNotEmpty(thirdName)) thirdInfo.setThirdName(thirdName);
        thirdInfo.setSubscribe(subscribe);

        thirdInfo=thirdInfoDao.save(thirdInfo);

        return BeanMapper.map(thirdInfo,ThirdInfoVo.class);
    }

    /**
     * 微信登录时更新用户信息
     * @param openid
     * @param thirdType
     * @param thirdName
     * @param userInfoResponse
     * @return
     */
    @Override
    public ThirdInfoVo updateThirdInfo(String openid, String thirdType, String thirdName, GetUserInfoResponse userInfoResponse) {

        ThirdInfo thirdInfo = thirdInfoDao.findByOpenIdAndThirdType(openid,thirdType);

        if(isNotEmpty(thirdInfo)){

            if(isNotEmpty(userInfoResponse.getNickname())) thirdInfo.setNickname(userInfoResponse.getNickname());
            if(isNotEmpty(userInfoResponse.getHeadimgurl())) thirdInfo.setAvatar(userInfoResponse.getHeadimgurl());
            thirdInfo.setGroupId(userInfoResponse.getGroupid());
            if(isNotEmpty(thirdType)) thirdInfo.setThirdType(thirdType);
            if(isNotEmpty(userInfoResponse.getOpenid())) thirdInfo.setOpenId(userInfoResponse.getOpenid());
            if(isNotEmpty(userInfoResponse.getUnionid())) thirdInfo.setUnionId(userInfoResponse.getUnionid());
            if(isNotEmpty(thirdName)) thirdInfo.setThirdName(thirdName);
            thirdInfo.setSubscribe(userInfoResponse.getSubscribe());

            thirdInfo=thirdInfoDao.save(thirdInfo);

            ThirdInfoVo  thirdInfoVo=  BeanMapper.map(thirdInfo,ThirdInfoVo.class);

            UserVo user = thirdInfoVo.getUser();
            //正常用户 登录
            if(isNotEmpty(user) && isNotEmpty(user.getUsername()) ){
                String token = tokenCache.get("tk"+user.getId());
//                if(isEmpty(token)){
                    token = Identities.uuid2();
                    tokenCache.put("tk"+user.getId(),token);
//                }
                user.setToken(token);
                thirdInfoVo.setUser(user);
            }


            return thirdInfoVo;

        }else{
            return null;
        }
    }

    /**
     * 获取信用户或者unionid关联用户
     * @param unionId
     * @param avatar
     * @param nickname
     * @return
     * @throws BusinessException
     */
    @Override
    public User getEmptyOrLinkUser(String unionId, String avatar, String nickname) throws BusinessException {

        if(isEmpty(unionId)){
            return userService.registerEmptyUser(nickname,avatar);
        }else{
            List<ThirdInfo> thirdInfos =  thirdInfoDao.findByUnionId(unionId);
            if(isEmpty(thirdInfos) || thirdInfos.size()==0){
                return userService.registerEmptyUser(nickname,avatar);
            }
            for(int i = 0 ;i<thirdInfos.size();i++){
                if(isNotEmpty(thirdInfos.get(i).getUser())){
                    return thirdInfos.get(i).getUser();
                }
            }
            return userService.registerEmptyUser(nickname,avatar);
        }
    }
}
