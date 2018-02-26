package com.sxp.sa.user.service;


import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.user.entity.DrawChannel;
import com.sxp.sa.user.vo.DrawChannelVo;

import java.util.List;

public interface DrawChannelService {

    /**
     * 保存提现渠道
     * @param drawChannel
     * @return
     */
    DrawChannelVo save(Long uid, DrawChannel drawChannel) throws BusinessException;

    /**
     * 删除提现渠道
     * @param id
     */
    DrawChannelVo delete(Long uid, long id) throws BusinessException;

    /**
     * 查询用户所有提现渠道
     * @param uid
     * @return
     * @throws BusinessException
     */
    List<DrawChannelVo> queryUserAllDrawChannel(Long uid) throws BusinessException;
}
