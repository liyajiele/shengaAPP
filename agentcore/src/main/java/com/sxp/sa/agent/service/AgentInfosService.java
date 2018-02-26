package com.sxp.sa.agent.service;

import com.sxp.sa.agent.vo.AgentInfosVo;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import org.springframework.data.domain.Pageable;

public interface AgentInfosService {


    /**
     * 绑定为代理商
     * @param aid
     * @param uid
     * @param districtId
     * @param description
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    AgentInfosVo bindAgent(Long aid,Long uid,Integer districtId,String description,String username,String password,Long startTime,Long endTime,Long zhaoshangManagerId,Long kehuManagerId)throws BusinessException;


    Pager<AgentInfosVo> findAgentListByAdmin(Long aid, String searchStr, Pageable pageable)throws BusinessException;

    Pager<AgentInfosVo> findAgentListByAgentUid(Long aid, Long uid, Pageable pageable)throws BusinessException;
}
