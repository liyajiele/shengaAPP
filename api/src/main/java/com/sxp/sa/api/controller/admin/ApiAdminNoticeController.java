package com.sxp.sa.api.controller.admin;

import com.sxp.sa.agent.entity.AgentNotice;
import com.sxp.sa.agent.service.AgentNotisService;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.user.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "管理后台-公告相关接口", description = "管理员-公告相关接口")
@RestController
@RequestMapping(value = "api/admin/notice", method = RequestMethod.POST)
public class ApiAdminNoticeController {


    @Autowired
    private AdminService adminService;

    @Autowired
    private AgentNotisService agentNotisService;



    @RequestMapping(value = "/addNotice", method = RequestMethod.POST)
    @ApiOperation(value = "增加公告", httpMethod = "POST", notes = "增加公告")
    public Rst<AgentNotice> addNotice(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "标题") @RequestParam(required = true, value = "title") String  title,
            @ApiParam(value = "内容") @RequestParam(required = true, value = "content") String  content,
            @ApiParam(value = "类型 1:内链，2:外链") @RequestParam(required = true, value = "type") Integer  type,
            @ApiParam(value = "发布状态 -1.锁定  1.直接发布") @RequestParam(required = true, value = "status") Integer  status,
            @ApiParam(value = "外链url",required = false) @RequestParam(required = false, value = "url") String  url,
            @ApiParam(value = "优先级",required = false) @RequestParam(required = false, value = "priority",defaultValue = "1") Integer  priority
    ) throws BusinessException {

        Rst<AgentNotice> rst = new Rst<>();

        rst.setObject(agentNotisService.addAgentNotice(aid,title,content,type,status,url,priority));

        return rst;

    }



    @RequestMapping(value = "/modifyNotice", method = RequestMethod.POST)
    @ApiOperation(value = "修改公告(可发布,删除,修改)", httpMethod = "POST", notes = "修改公告")
    public Rst<AgentNotice> modifyNotice(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "公告id") @RequestParam(required = true, value = "noticeId") Long   noticeId,
            @ApiParam(value = "标题") @RequestParam(required = true, value = "title") String  title,
            @ApiParam(value = "内容") @RequestParam(required = true, value = "content") String  content,
            @ApiParam(value = "类型 1:内链，2:外链") @RequestParam(required = true, value = "type") Integer  type,
            @ApiParam(value = "发布状态 -1.锁定 0.删除 1.直接发布") @RequestParam(required = true, value = "status") Integer  status,
            @ApiParam(value = "外链url",required = false) @RequestParam(required = false, value = "url") String  url,
            @ApiParam(value = "优先级",required = false) @RequestParam(required = false, value = "priority",defaultValue = "1") Integer  priority
    ) throws BusinessException {

        Rst<AgentNotice> rst = new Rst<>();

        rst.setObject(agentNotisService.modifyNotice(aid,noticeId,title,content,type,status,url,priority));

        return rst;

    }




    @RequestMapping(value = "/findNoticeListByAcent", method = RequestMethod.POST)
    @ApiOperation(value = "代理商获取公告列表)", httpMethod = "POST", notes = "代理商获取公告列表")
    public Rst<Page<AgentNotice>> findNoticeListByAcent(
            @PageableDefault(page = 0, size = 30) Pageable pageable
    ) throws BusinessException {

        Rst<Page<AgentNotice>> rst = new Rst<>();

        rst.setObject(agentNotisService.findNoticePageByStatus(Const.Status.valid,pageable));

        return rst;

    }


    @RequestMapping(value = "/findNoticeListByAdmin", method = RequestMethod.POST)
    @ApiOperation(value = "管理员获取公告列表)", httpMethod = "POST", notes = "管理员获取公告列表")
    public Rst<Page<AgentNotice>> findNoticeListByAdmin(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "发布状态 -1.锁定 0.删除 1.直接发布") @RequestParam(required = true, value = "status") Integer  status
            ) throws BusinessException {

        Rst<Page<AgentNotice>> rst = new Rst<>();

        rst.setObject(agentNotisService.findNoticePageByStatus(status,pageable));

        return rst;

    }





}
