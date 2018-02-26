package com.sxp.sa.agent.service.impl;


import com.sxp.sa.agent.entity.AgentNotice;
import com.sxp.sa.agent.repository.AgentNoticeRepository;
import com.sxp.sa.agent.service.AgentNotisService;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sxp.sa.basic.constant.Const.Code.NOTICE_NOT_EXISTS;
import static com.sxp.sa.basic.constant.Const.Code.NOTICE_NOT_EXISTS_MSG;
import static com.sxp.sa.basic.utils.Util.isEmpty;

@Service
public class AgentNoticeServiceImpl extends BaseService implements AgentNotisService{

    @Autowired
    private AgentNoticeRepository agentNoticeRepository;


    /**
     * 增加后台公告
     * @param aid
     * @param title
     * @param content
     * @param type
     * @param url
     * @param priority
     * @return
     * @throws BusinessException
     */
    @Override
    public AgentNotice addAgentNotice(Long aid, String title, String content, Integer type,Integer status, String url, Integer priority) throws BusinessException {


        AgentNotice agentNotice = new AgentNotice();
        agentNotice.setCreateUser(aid);
        agentNotice.setModifyDesc("add");
        agentNotice.setTitle(title);
        agentNotice.setContent(content);
        agentNotice.setType(type);
        agentNotice.setUrl(url);
        agentNotice.setPriority(priority);
        //设置为锁定状态
        agentNotice.setStatus(status);

        agentNotice = agentNoticeRepository.save(agentNotice);

        return agentNotice;
    }

    /**
     * 修改公告
     * @param aid
     * @param noticeId
     * @param title
     * @param content
     * @param type
     * @param status
     * @param url
     * @param priority
     * @return
     * @throws BusinessException
     */
    @Override
    public AgentNotice modifyNotice(Long aid, Long noticeId, String title, String content, Integer type, Integer status, String url, Integer priority) throws BusinessException {



        AgentNotice agentNotice = agentNoticeRepository.findById(noticeId);
        if(isEmpty(agentNotice)){
            throw new BusinessException(NOTICE_NOT_EXISTS,NOTICE_NOT_EXISTS_MSG);
        }

        agentNotice.setTitle(title);
        agentNotice.setContent(content);
        agentNotice.setType(type);
        agentNotice.setStatus(status);
        agentNotice.setUrl(url);
        agentNotice.setPriority(priority);
        agentNotice.setModifyUser(aid);
        agentNotice.setModifyDesc("modify");

        agentNotice = agentNoticeRepository.save(agentNotice);

        return agentNotice;
    }


    /**
     * 分页查询公告
     * @param status
     * @return
     */
    @Override
    public Page<AgentNotice> findNoticePageByStatus(Integer status,Pageable pageable) {

        Page<AgentNotice> notices = agentNoticeRepository.findByStatus(status,pageable);

        return notices;
    }
}
