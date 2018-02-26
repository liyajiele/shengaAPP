package com.sxp.sa.user.service;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.user.entity.DrawChannel;
import com.sxp.sa.user.vo.DrawVo;
import org.springframework.data.domain.Pageable;

/**
 * Created by Administrator on 2017/2/24.
 */

public interface DrawService {

   

    /**
     * 管理员分页查询所有用户的提成信息
     * @param uid
     * @param pageable
     * @return
     * @throws BusinessException
     */
    Pager<DrawVo> adminQueryAllDrawByPage(Long uid, String startTime, String endTime, String scope, Pageable pageable) throws BusinessException;

    /**
     * 管理员批准 提成
     * @param uid
     * @param did
     * @param directive
     * @return
     * @throws BusinessException
     */
    DrawVo adminRatifyDraw(Long uid,Long did,String directive,String rejectDescr) throws BusinessException;

    /**
     * 根据提现id ，查询提现信息
     * @param did
     * @return
     * @throws BusinessException
     */
    DrawVo queryDrawById(Long uid,Long did) throws BusinessException;
    /**
     * 申请提现
     * @param drawNum
     * @return
     */
    DrawVo appylForDraw(Long uid, Long drawCId, Double drawNum) throws BusinessException;

    DrawChannel getDrawChannel(Long uid, String alipay, String concatUser) throws BusinessException;

    /**
     * 分页查询提现列表
     * @param uid
     * @param pageable
     * @return
     * @throws BusinessException
     */
    Pager<DrawVo> findDrawByPage(Long uid, Pageable pageable) throws BusinessException;

}

