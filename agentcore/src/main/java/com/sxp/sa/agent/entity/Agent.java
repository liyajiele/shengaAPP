package com.sxp.sa.agent.entity;


import com.sxp.sa.basic.entity.BaseEntity;
import com.sxp.sa.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_a_agent")
@ApiModel(value = "代理商--不用")
@Setter
@Getter
public class Agent extends BaseEntity{

    @ApiModelProperty("关联用户")
    private User user;

    @ApiModelProperty("代理信息")
    private AgentInfos agentInfos;


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = AgentInfos.class)
    @JoinColumn(name = "agentInfosId")
    public AgentInfos getAgentInfos() {
        return agentInfos;
    }

    public void setAgentInfos(AgentInfos agentInfos) {
        this.agentInfos = agentInfos;
    }
}
