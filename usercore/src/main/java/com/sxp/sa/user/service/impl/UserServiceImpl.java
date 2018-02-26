package com.sxp.sa.user.service.impl;


import cn.com.duiba.credits.sdk.CreditTool;
import com.aliyun.oss.OSSClient;
import com.github.sd4324530.fastweixin.api.QrcodeAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.enums.QrcodeType;
import com.github.sd4324530.fastweixin.api.response.QrcodeResponse;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.basic.utils.Identities;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.user.entity.Account;
import com.sxp.sa.user.entity.SMSCode;
import com.sxp.sa.user.entity.SearchRecord;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.AccountRepository;
import com.sxp.sa.user.repository.SMSCodeRepository;
import com.sxp.sa.user.repository.SearchRecodRepository;
import com.sxp.sa.user.repository.UserRepository;
import com.sxp.sa.user.service.SMSCodeService;
import com.sxp.sa.user.service.UserService;
import com.sxp.sa.user.vo.OtherUserVo;
import com.sxp.sa.user.vo.SearchRecordVo;
import com.sxp.sa.user.vo.UserVo;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.sxp.sa.basic.constant.Const.Code.*;
import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.*;

/**
 * Created by miss on 2017/2/17.
 */
@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private Cache<String,String> tokenCache;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private SearchRecodRepository searchRecodRepository;

    @Autowired
    private SMSCodeRepository smsCodeDao;
    @Autowired
    private SMSCodeService smsCodeService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ApiConfig userApiConfig;


    @Autowired
    @Qualifier("ossClient")
    private OSSClient ossClient;

    @Override
    public void updateLocation(Long uid,String longitude, String latitude) {

        User user = userDao.findByIdAndStatus(uid,valid);

        //用户不存在
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }

        user.setLongitude(longitude);
        user.setLatitude(latitude);

        userDao.save(user);

    }

    /**
     * 查询我的搜索记录
     * @param uid
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<SearchRecordVo> findMySearchRecord(Long uid, Pageable pageable) throws BusinessException {

        Page<SearchRecord> srPage = searchRecodRepository.findMySearchRecord(uid,pageable);

        Pager<SearchRecordVo> rst = p2pr(srPage,SearchRecordVo.class);

        return rst;
    }

    /**
     * 清除我的搜索记录
     * @param uid
     * @return
     * @throws BusinessException
     */
    @Override
    public Boolean cleanMySearchRecord(Long uid) throws BusinessException {
        searchRecodRepository.cleanMySearchRecord(uid);
        return true;
    }

    /**
     * 设置用户token缓存
     * @param id
     * @return
     */
    public String getToken(Long id){
        String userToken = Identities.uuid2();
        String tk = tokenCache.get("tk"+id);
        if(isNotEmpty(tk) && tk.length()>=32){
            return tk;
        }
        tokenCache.put("tk"+id,userToken );
        return userToken;
    }

    /**
     * 获取自己的用户信息
     * @param uid
     * @return
     * @throws BusinessException
     */
    @Override
    public UserVo getUserInfo(Long uid) throws BusinessException {
        User user = userDao.findByIdAndStatus(uid,valid);


        //用户不存在
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }

        return BeanMapper.map(user,UserVo.class);
    }

    /**
     * 获取下级人数
     * @param uid
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> getMyChildNums(Long uid) throws BusinessException {

        Map<String,Object> rst = new HashMap<>();
        rst.put("child1",userDao.findMyChild1Nums(uid));
        rst.put("child2",userDao.findMyChild2Nums(uid));

        return rst;
    }

    @Override
    public Pager<OtherUserVo> getMyChild(Long uid, Integer childType, Pageable pageable) throws BusinessException {

        if(childType==1){
            Page<User> userPage = userDao.findMyChild1(uid,pageable);
            return p2pr(userPage,OtherUserVo.class);
        }else{
            Page<User> userPage = userDao.findMyChild2(uid,pageable);
            return p2pr(userPage,OtherUserVo.class);
        }

    }




    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    @Override
    public UserVo login(String username, String password) throws BusinessException {
        User user = userDao.findByUsername(username);
        //如果已经注册
        if(Util.isEmpty(user)){
            throw new BusinessException(Const.Code.USER_NOT_EXISTS,"用户不存在");
        }
        if(!user.getPassword().equals(password)){
            throw new BusinessException(Const.Code.PWD_ERROR,PWD_ERROR_MSG);
        }

        UserVo userVo = BeanMapper.map(user,UserVo.class);
        //设置登录后的token
        userVo.setToken(getToken(user.getId()));
        return userVo;
    }


    /**
     * 忘记登陆密码，用短信验证修改登陆密码
     * @param username
     * @param newPassword
     * @param code
     * @return
     */
    @Override
    public UserVo forgetUserPassword(String username, String newPassword, String code) throws BusinessException{
        User user=userDao.findByUsername(username);
        if(Util.isEmpty(user)){
            throw new BusinessException(Const.Code.USER_NOT_EXISTS,"用户不存在");
        }

        //如果开启短信验证码  验证smsCode
        if(Const.OPEN_SMS){
            SMSCode smsCode = smsCodeDao.findCode(username,code,Const.SMSCodeType.FORGET_PASS);
            if(Util.isEmpty(code)){
                throw new BusinessException(Const.Code.SMS_ERROR,SMS_ERROR_MSG);
            }
            Long period = System.currentTimeMillis()-smsCode.getCreateTime();
            if(period>Const.SMS_PERIOD){
                smsCode.setStatus(Const.Status.delete);
                throw new BusinessException(Const.Code.SMS_ERROR,SMS_ERROR_MSG);
            }
            smsCodeService.deleteSms(smsCode);//删除（修改状态字段）验证过短信
        }

        user.setPassword(newPassword);
        user=userDao.save(user);
        UserVo userVo=new UserVo();
        BeanMapper.copy(user,userVo);

        return userVo;
    }



    /**
     * 注册空账号,wx扫码时使用
     * @return
     * @throws BusinessException
     */
    @Override
    public User registerEmptyUser(String nickname,String avatar) throws BusinessException {
        User user = new User();
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setLevel(1);
        user.setLongitude(Const.DEFAULT_LONGITUDE.toString());
        user.setLatitude(Const.DEFAULT_LATITUDE.toString());
        user = userDao.save(user);
        //生成用户的带参 二维码

        QrcodeAPI qrcodeApi = new QrcodeAPI(userApiConfig);

        QrcodeResponse qrcodeResp = qrcodeApi.createQrcode(QrcodeType.QR_SCENE,user.getId().toString(),2592000);
        user.setQrcode(qrcodeResp.getUrl());
        user.setQrcodeTimeout(System.currentTimeMillis()+2550000);
        user.setQrcodeTicket(qrcodeResp.getTicket());

        //上传二维码图片
        try {
            String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+qrcodeResp.getTicket();
            ossClient.putObject(Const.OSS_BUCKET_NAME, "user/qrcode/"+user.getId()+".jpg", new URL(url).openStream());
            //todo
            //存储到本地一份
//            Util.downloadFile(url,"E:/wamp64/www/nongchang/qrcode/"+user.getId()+".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }



        user = userDao.save(user);

        Account account = new Account(user);
        accountRepository.save(account);
        return user;

    }

    /**
     * 传入当前用户和上级openId  仅用于微信
     * @param curUser
     * @param parentId
     * @return
     */
    @Override
    public User bindParent(User curUser, Long parentId) {

        User parentUser = userDao.findByIdAndStatus(parentId,valid);
        //上级信息不完整 或者 已有上级
        if(Util.isEmpty(parentUser) || isNotEmpty(curUser.getParent1())){
            return  curUser;
        }
        //如果上级是商家
//        if(parentUser.getIsMerchant().equals(1) && isEmpty( curUser.getMerchantId())){
//            curUser.setMerchantId(parentUser.getMyMerchant());
//            curUser = userDao.save(curUser);
//            return curUser;
//        }
        //如果上级是代理商
        if(isNotEmpty(parentUser.getIsAgent()) && parentUser.getIsAgent()>0 && isEmpty(curUser.getAgentId())){
            curUser.setAgentId(parentUser.getId());
        }
        if(isEmpty(curUser.getParent1())){
            curUser.setParent1(parentUser.getId());
            if(isNotEmpty(parentUser.getAgentId()) && isEmpty(curUser.getAgentId())){
                curUser.setAgentId(parentUser.getAgentId());
            }

            if(isNotEmpty(parentUser.getParent1())) curUser.setParent2(parentUser.getParent1());
            if(isNotEmpty(parentUser.getParent2())) curUser.setParent3(parentUser.getParent2());
            curUser = userDao.save(curUser);
            return curUser;
        }

        return curUser;

    }

    /**
     * 兑吧登录
     * @param uid
     * @return
     * @throws BusinessException
     */
    @Override
    public String duibaLogin(Long uid,String redirect) throws BusinessException {


        Account account = accountRepository.findByUser(uid);
        if(isEmpty(account)){
            throw new BusinessException(ACCOUNT_ERROR,ACCOUNT_ERROR_MSG);
        }

        CreditTool tool=new CreditTool(Const.DuiBaParam.KEY, Const.DuiBaParam.SECRET);
        Map params=new HashMap();
        params.put("uid",uid+"");
        Integer intergral =0;
        if(isNotEmpty(account.getIntergral())){
            intergral = account.getIntergral();
        }
        params.put("credits",intergral+"");
        if(redirect!=null){
            //redirect是目标页面地址，如果要跳转到积分商城指定页面，redirect地址就是目标页面地址
            //此处请设置成一个外部传进来的参数，方便运营灵活配置
            params.put("redirect",redirect);
        }
        String url=tool.buildUrlWithSign("http://www.duiba.com.cn/autoLogin/autologin?",params);
//此url即为免登录url
        return url;
    }
}
