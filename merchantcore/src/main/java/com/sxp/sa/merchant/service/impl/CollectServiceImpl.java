package com.sxp.sa.merchant.service.impl;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.merchant.entity.Collect;
import com.sxp.sa.merchant.entity.Merchant;
import com.sxp.sa.merchant.repository.CollectRepository;
import com.sxp.sa.merchant.repository.MerchantRepository;
import com.sxp.sa.merchant.service.CollectService;
import com.sxp.sa.merchant.vo.MerchantVo;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sxp.sa.basic.constant.Const.Code.*;
import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.isEmpty;
import static com.sxp.sa.basic.utils.Util.isNotEmpty;

@Service
public class CollectServiceImpl extends BaseService implements CollectService{


    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private UserRepository userDao;
    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public Integer queryMyCollectNum(Long uid) throws BusinessException {
        return collectRepository.queryMyCollectNum(uid);
    }

    /**
     * 查询我的收藏列表
     * @param uid
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<MerchantVo> queryMyCollects(Long uid, Pageable pageable) throws BusinessException {

        User user = userDao.findByIdAndStatus(uid,valid);
        //用户不存在
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }
        Page<Merchant> collects = collectRepository.queryMyCollects(uid,pageable);
        //距离
        List<Merchant> mList = collects.getContent();

        List<MerchantVo> mvos = new ArrayList<>();

        for(int i = 0 ;i <mList.size();i++){
            Merchant m = mList.get(i);
            MerchantVo mvo = BeanMapper.map(m,MerchantVo.class);
            mvo.setDistance(Util.getDistance(Double.parseDouble(user.getLatitude()),Double.parseDouble(user.getLongitude()),Double.parseDouble(m.getLatitude()),Double.parseDouble(m.getLongitude())));

            mvos.add(mvo);
        }

        Pager<MerchantVo> rst = new Pager<>();

        rst.setLast(collects.isLast());
        rst.setFirst(collects.isFirst());
        rst.setNumber(collects.getNumber());
        rst.setNumberOfElements(collects.getNumberOfElements());
        rst.setSize(collects.getSize());
        rst.setTotalElements(collects.getTotalElements());
        rst.setTotalPages(collects.getTotalPages());
        rst.setContent(mvos);

        return rst;

    }

    /**
     * 收藏或取消收藏
     * @param uid
     * @param mid
     * @param type
     * @return
     * @throws BusinessException
     */
    @Override
    public MerchantVo collectOrCancel(Long uid, Long mid, Integer type) throws BusinessException {

        User user = userDao.findByIdAndStatus(uid,valid);
        //用户不存在
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }
        Merchant merchant = merchantRepository.findById(mid,valid);
        if(isEmpty(merchant)){
            throw new BusinessException(MERCHANT_NOT_EXISTS,MERCHANT_NOT_EXISTS_MSG);
        }



        Collect collect = collectRepository.queryCollectByUserAndMerchant(uid,mid);

        if(1==type){
            if(isEmpty(collect)){
                Collect c = new Collect();
                c.setUser(user);
                c.setMerchant(merchant);
                c.setCollectStatus(1);
                c = collectRepository.save(c);
                return BeanMapper.map(merchant,MerchantVo.class);
            }else{
                collect.setCollectStatus(1);
                collectRepository.save(collect);
                return BeanMapper.map(collect.getMerchant(),MerchantVo.class);
            }
        }
        if(0==type && isNotEmpty(collect)){
            collect.setCollectStatus(0);
            collectRepository.save(collect);
            return BeanMapper.map(collect.getMerchant(),MerchantVo.class);
        }
        return null;

    }
}
