package com.sxp.sa.api.controller.user;


import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.merchant.service.NewsInfoService;
import com.sxp.sa.merchant.vo.NewsInfoVo;
import com.sxp.sa.order.service.AccountRecordService;
import com.sxp.sa.order.vo.AccountRecordVo;
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

import javax.servlet.http.HttpServletRequest;

@Api(value = "用户-账户记录相关接口", description = "用户-账户记录相关接口")
@RestController
@RequestMapping(value = "api/user/accountRecord", method = RequestMethod.POST)
public class ApiAccountRecordController {

    @Autowired
    private NewsInfoService newsInfoService;

    @Autowired
    private AccountRecordService accountRecordService;

    @RequestMapping(value = "/getNewsInfoByPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取最新动态", httpMethod = "POST", notes = "获取最新动态,分页")
    public Rst<Pager<NewsInfoVo>> getNewsInfoByPage(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Pager<NewsInfoVo>> rst = new Rst<>();

        rst.setObject(newsInfoService.findByPage(pageable));

        return rst ;
    }

    @RequestMapping(value = "/getRedBagInfos", method = RequestMethod.POST)
    @ApiOperation(value = "获取红包动态", httpMethod = "POST", notes = "获取红包动态,分页")
    public Rst<Pager<NewsInfoVo>> getRedBagInfos(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Pager<NewsInfoVo>> rst = new Rst<>();

        rst.setObject(newsInfoService.getRedBagInfos(pageable));

        return rst ;
    }

    @RequestMapping(value = "/authc/getMyAccountRecord", method = RequestMethod.POST)
    @ApiOperation(value = "获取我的账户记录(参数可选)", httpMethod = "POST", notes = "获取我的账户记录(参数可选),分页")
    public Rst<Pager<AccountRecordVo>> getMyAccountRecord(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "记录类型",required = false) @RequestParam(required = false, value = "recordType",defaultValue = "0") Integer recordType,
            @ApiParam(value = "起始时间",required = false) @RequestParam(required = false, value = "startTime",defaultValue = "0") Long  startTime,
            @ApiParam(value = "结束时间",required = false) @RequestParam(required = false, value = "endTime",defaultValue = "3723724800000") Long  endTime,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Pager<AccountRecordVo>> rst = new Rst<>();

        rst.setObject(accountRecordService.getMyAccountRecord(uid,recordType,startTime,endTime,pageable));

        return rst ;
    }
}
