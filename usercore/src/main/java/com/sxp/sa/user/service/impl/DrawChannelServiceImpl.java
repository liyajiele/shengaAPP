package com.sxp.sa.user.service.impl;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.user.dao.UserDao;
import com.sxp.sa.user.entity.DrawChannel;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.DrawChannelRepository;
import com.sxp.sa.user.service.DrawChannelService;
import com.sxp.sa.user.vo.DrawChannelVo;
import com.sxp.sa.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sxp.sa.basic.constant.Const.Code.DRAW_CHANNEL_NOT_EXISTS_MSG;
import static com.sxp.sa.basic.constant.Const.Status.valid;

/**
 */

@Service
public class DrawChannelServiceImpl implements DrawChannelService {

    @Autowired
    private DrawChannelRepository drawChannelDao;

    @Autowired
    private UserDao userDao;
    /**
     * 保存提现渠道
     * @param drawChannel
     * @return
     * @throws BusinessException
     */
    @Override
    public DrawChannelVo save(Long uid, DrawChannel drawChannel) throws BusinessException {
        User user= userDao.findById(uid,valid);
        if(Util.isEmpty(user)){
            throw new BusinessException(Const.Code.USER_NOT_EXISTS,"用户不存在");
        }
        UserVo userVo=new UserVo();
        BeanMapper.copy(user,userVo);
        drawChannel.setUser(user);
        drawChannelDao.save(drawChannel);
        DrawChannelVo drawChannelVo=new DrawChannelVo();
        BeanMapper.copy(drawChannel,drawChannelVo);
        drawChannelVo.setUserVo(userVo);//set进去userVo
        return drawChannelVo;
    }

    /**
     * 删除提现渠道
     * @param id
     * @throws BusinessException
     */
    @Override
    public DrawChannelVo delete(Long uid,long  id) throws BusinessException{
        User user =userDao.findById(uid,valid);
        if(Util.isEmpty(user)){
            throw new BusinessException(Const.Code.USER_NOT_EXISTS,"用户不存在");
        }
        DrawChannel drawChannel=drawChannelDao.findById(id);

        if(Util.isEmpty(drawChannel)){
            throw new BusinessException(Const.Code.DRAW_CHANNEL_NOT_EXISTS,DRAW_CHANNEL_NOT_EXISTS_MSG);
        }

        drawChannel.setStatus(2);

        drawChannel=drawChannelDao.save(drawChannel);

        DrawChannelVo drawChannelVo=BeanMapper.map(drawChannel,DrawChannelVo.class);

        return drawChannelVo;
    }

    /**
     * 查询用户所有提现渠道
     * @param uid
     * @return
     * @throws BusinessException
     */
    @Override
    public List<DrawChannelVo> queryUserAllDrawChannel(Long uid) throws BusinessException {
        User user=userDao.findById(uid,valid);
        if(Util.isEmpty(user)){
            throw new BusinessException(Const.Code.USER_NOT_EXISTS,"用户不存在");
        }
        List<DrawChannel> drawChannelList=drawChannelDao.findByUserAndAll(user);
        List<DrawChannelVo> drawChannelVoList=new ArrayList<DrawChannelVo>();
        for(DrawChannel d:drawChannelList){
            DrawChannelVo drawChannelVo=new DrawChannelVo();
            BeanMapper.copy(d,drawChannelVo);
            drawChannelVoList.add(drawChannelVo);
        }
        return drawChannelVoList;
    }
}
