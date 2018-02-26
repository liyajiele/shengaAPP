package com.sxp.sa.api.controller.admin;


import com.sxp.sa.agent.service.AgentInfosService;
import com.sxp.sa.agent.vo.AgentInfosVo;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "管理后台-代理商相关接口", description = "管理员-代理商相关接口 ")
@RestController
@RequestMapping(value = "api/admin/agent", method = RequestMethod.POST)
public class ApiAdminAgentController {

    @Autowired
    private AgentInfosService agentInfosService;

    @RequestMapping(value = "/bindAgent", method = RequestMethod.POST)
    @ApiOperation(value = "代理区域", httpMethod = "POST", notes = "代理区域    ")
    public Rst<AgentInfosVo> bindAgent(
            @ApiParam(value = "操作管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long  uid,
            @ApiParam(value = "代理区域id") @RequestParam(required = true, value = "districtId") Integer  districtId,

            @ApiParam(value = "描述信息") @RequestParam(required = true, value = "description") String  description,
            @ApiParam(value = "登录账号",required = false) @RequestParam(required = false, value = "username") String  username,
            @ApiParam(value = "登录密码",required = false) @RequestParam(required = false, value = "password") String   password,

            @ApiParam(value = "代理时间",required = false) @RequestParam(required = false, value = "startTime") Long   startTime,
            @ApiParam(value = "代理过期时间",required = false) @RequestParam(required = false, value = "endTime") Long   endTime,

            @ApiParam(value = "招商经理id",required = false) @RequestParam(required = false, value = "zhaoshangManagerId") Long   zhaoshangManagerId,
            @ApiParam(value = "客户经理id",required = false) @RequestParam(required = false, value = "kehuManagerId") Long   kehuManagerId

    ) throws BusinessException {

        Rst<AgentInfosVo> rst = new Rst<>();

        rst.setObject(agentInfosService.bindAgent(aid,uid,districtId,description,username,password,startTime,endTime,zhaoshangManagerId,kehuManagerId));

        return rst;

    }


    @RequestMapping(value = "/findAgentListByAdmin", method = RequestMethod.POST)
    @ApiOperation(value = "管理员获取代理商列表", httpMethod = "POST", notes = "管理员获取代理商列表")
    public Rst<Pager<AgentInfosVo>> bindAgent(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "操作管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "搜索字符串",required = false) @RequestParam(required = false, value = "searchStr") String   searchStr

            ) throws BusinessException {

        Rst<Pager<AgentInfosVo>> rst = new Rst<>();

        rst.setObject(agentInfosService.findAgentListByAdmin(aid,searchStr,pageable));

        return rst;

    }


    @RequestMapping(value = "/findAgentListByAgentUid", method = RequestMethod.POST)
    @ApiOperation(value = "根据代理商uid获取代理列表", httpMethod = "POST", notes = "根据代理商uid获取代理列表")
    public Rst<Pager<AgentInfosVo>> findAgentListByAgentUid(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "操作管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "搜索字符串",required = true) @RequestParam(required = true, value = "uid") Long   uid

    ) throws BusinessException {

        Rst<Pager<AgentInfosVo>> rst = new Rst<>();

        rst.setObject(agentInfosService.findAgentListByAgentUid(aid,uid,pageable));

        return rst;

    }

}
