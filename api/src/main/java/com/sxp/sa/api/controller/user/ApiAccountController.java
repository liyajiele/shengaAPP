package com.sxp.sa.api.controller.user;


import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.order.service.AccountChangeService;
import com.sxp.sa.order.vo.RBAccountRecordVo;
import com.sxp.sa.user.service.AccountService;
import com.sxp.sa.user.vo.AccountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value = "用户-账户相关接口", description = "用户-账户相关接口")
@RestController
@RequestMapping(value = "api/user/account", method = RequestMethod.POST)
public class ApiAccountController {

    @Autowired
    private AccountChangeService accountChangeService;


    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/authc/getRedBag", method = RequestMethod.POST)
    @ApiOperation(value = "获取红包", httpMethod = "POST", notes = "获取红包")
    public Rst<RBAccountRecordVo> getRedBag(
            @ApiParam(value = "用户id",required = true) @RequestParam(required = true,value = "uid") Long uid,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<RBAccountRecordVo> rst = new Rst<>();

        rst.setObject(accountChangeService.getRedBag(uid));
        return rst ;
    }

    @RequestMapping(value = "/authc/accountInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取账户信息", httpMethod = "POST", notes = "获取账户信息")
    public Rst<AccountVo> accountInfo(
            @ApiParam(value = "用户id",required = true) @RequestParam(required = true,value = "uid") Long uid,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<AccountVo> rst = new Rst<>();

        rst.setObject(accountService.getAccountInfo(uid));
        return rst ;
    }
}
