package com.sxp.sa.agent.service;

import com.sxp.sa.agent.entity.AgentNotice;
import com.sxp.sa.basic.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentNotisService {


    /**
     * 增加 后台公告
     * @param aid
     * @param title
     * @param content
     * @param type
     * @param url
     * @param priority
     * @return
     * @throws BusinessException
     */
    AgentNotice addAgentNotice(Long aid,String title,String content,Integer type,Integer status,String url,Integer priority)throws BusinessException;


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
    AgentNotice modifyNotice(Long aid,Long noticeId,String title,String content,Integer type,Integer status,String url,Integer priority)throws BusinessException;


    /**
     * 分页查询公告
     * @param status
     * @return
     */
    Page<AgentNotice> findNoticePageByStatus(Integer status, Pageable pageable);
}
