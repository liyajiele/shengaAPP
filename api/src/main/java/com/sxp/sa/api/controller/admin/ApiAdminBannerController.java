package com.sxp.sa.api.controller.admin;


import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.agent.service.BannerService;
import com.sxp.sa.agent.vo.BannerVo;
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

@Api(value = "管理后台-banner 相关接口", description = "管理员-banner 相关接口")
@RestController
@RequestMapping(value = "api/admin/banner", method = RequestMethod.POST)
public class ApiAdminBannerController {


    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/adminAddBanner", method = RequestMethod.POST)
    @ApiOperation(value = "管理人员增加banner", httpMethod = "POST", notes = "管理人员增加banner")
    public Rst<BannerVo> adminAddBanner(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "图片地址") @RequestParam(required = true, value = "image") String  image,
            @ApiParam(value = "地址") @RequestParam(required = true, value = "url") String  url,
            @ApiParam(value = "详情") @RequestParam(required = true, value = "content") String   content,
            @ApiParam(value = "发布状态 -1.锁定  1.直接发布") @RequestParam(required = true, value = "status") Integer  status,
            @ApiParam(value = "轮播时间",required = false) @RequestParam(required = false, value = "carouselTime") Long  carouselTime,
            @ApiParam(value = "优先级",required = false) @RequestParam(required = false, value = "priority",defaultValue = "1") Integer  priority
    ) throws BusinessException {

        Rst<BannerVo> rst = new Rst<>();

        rst.setObject(bannerService.adminAddBanner(aid,image,url,content,status,carouselTime,priority));

        return rst;

    }


    @RequestMapping(value = "/agentAddBanner", method = RequestMethod.POST)
    @ApiOperation(value = "代理商增加banner", httpMethod = "POST", notes = "代理商增加banner")
    public Rst<BannerVo> agentAddBanner(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "区域id") @RequestParam(required = true, value = "districtId") Integer  districtId,
            @ApiParam(value = "图片地址") @RequestParam(required = true, value = "image") String  image,
            @ApiParam(value = "地址") @RequestParam(required = true, value = "url") String  url,
            @ApiParam(value = "详情") @RequestParam(required = true, value = "content") String   content,
            @ApiParam(value = "轮播时间",required = false) @RequestParam(required = false, value = "carouselTime") Long  carouselTime,
            @ApiParam(value = "优先级",required = false) @RequestParam(required = false, value = "priority",defaultValue = "1") Integer  priority
    ) throws BusinessException {

        Rst<BannerVo> rst = new Rst<>();

        rst.setObject(bannerService.agentAddBanner(aid,districtId,image,url,content,carouselTime,priority));

        return rst;

    }






    @RequestMapping(value = "/adminModifyBanner", method = RequestMethod.POST)
    @ApiOperation(value = "管理人员修改banner(修改,发布,下架,审核)", httpMethod = "POST", notes = "管理人员修改banner")
    public Rst<BannerVo> adminModifyBanner(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "bannerId") @RequestParam(required = true, value = "bannerId") Long  bannerId,
            @ApiParam(value = "图片地址") @RequestParam(required = true, value = "image") String  image,
            @ApiParam(value = "地址") @RequestParam(required = true, value = "url") String  url,
            @ApiParam(value = "详情") @RequestParam(required = true, value = "content") String   content,
            @ApiParam(value = "发布状态 -1.锁定 0删除 1.直接发布") @RequestParam(required = true, value = "status") Integer  status,
            @ApiParam(value = "轮播时间",required = false) @RequestParam(required = false, value = "carouselTime") Long  carouselTime,
            @ApiParam(value = "优先级",required = false) @RequestParam(required = false, value = "priority",defaultValue = "1") Integer  priority
    ) throws BusinessException {

        Rst<BannerVo> rst = new Rst<>();

        rst.setObject(bannerService.adminModifyBanner(aid,bannerId,image,url,content,status,carouselTime,priority));

        return rst;

    }


    @RequestMapping(value = "/agentModifyBanner", method = RequestMethod.POST)
    @ApiOperation(value = "代理商修改banner(修改,下架)", httpMethod = "POST", notes = "代理商修改banner(修改,下架)")
    public Rst<BannerVo> agentModifyBanner(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "bannerId") @RequestParam(required = true, value = "bannerId") Long  bannerId,
            @ApiParam(value = "图片地址") @RequestParam(required = true, value = "image") String  image,
            @ApiParam(value = "地址") @RequestParam(required = true, value = "url") String  url,
            @ApiParam(value = "详情") @RequestParam(required = true, value = "content") String   content,
            @ApiParam(value = "发布状态 -1.锁定  0删除") @RequestParam(required = true, value = "status") Integer  status,
            @ApiParam(value = "轮播时间",required = false) @RequestParam(required = false, value = "carouselTime") Long  carouselTime,
            @ApiParam(value = "优先级",required = false) @RequestParam(required = false, value = "priority",defaultValue = "1") Integer  priority
    ) throws BusinessException {

        Rst<BannerVo> rst = new Rst<>();

        rst.setObject(bannerService.agentModifyBanner(aid,bannerId,image,url,content,status,carouselTime,priority));

        return rst;

    }


    @RequestMapping(value = "/adminFindBannerList", method = RequestMethod.POST)
    @ApiOperation(value = "管理人员查看banner列表", httpMethod = "POST", notes = "管理人员查看banner列表")
    public Rst<Pager<BannerVo>> adminFindBannerList(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "发布状态 -1.锁定  1.直接发布") @RequestParam(required = true, value = "status") Integer  status,
            @ApiParam(value = "区域id",required = false) @RequestParam(required = false, value = "districtId") Integer  districtId
    ) throws BusinessException {

        Rst<Pager<BannerVo>> rst = new Rst<>();

        rst.setObject(bannerService.adminFindBannerList(aid,status,districtId,pageable));

        return rst;

    }



    @RequestMapping(value = "/agentFindBannerList", method = RequestMethod.POST)
    @ApiOperation(value = "代理商查看banner列表", httpMethod = "POST", notes = "代理商查看banner列表")
    public Rst<Pager<BannerVo>> agentFindBannerList(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "发布状态 -1.锁定  1.直接发布") @RequestParam(required = true, value = "status") Integer  status,
            @ApiParam(value = "区域id",required = false) @RequestParam(required = false, value = "districtId") Integer  districtId
    ) throws BusinessException {

        Rst<Pager<BannerVo>> rst = new Rst<>();

        rst.setObject(bannerService.agentFindBannerList(aid,status,districtId,pageable));

        return rst;

    }



}
