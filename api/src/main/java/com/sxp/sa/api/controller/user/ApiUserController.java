package com.sxp.sa.api.controller.user;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.merchant.service.CollectService;
import com.sxp.sa.merchant.vo.MerchantVo;
import com.sxp.sa.user.service.AccountService;
import com.sxp.sa.user.service.UserService;
import com.sxp.sa.user.vo.OtherUserVo;
import com.sxp.sa.user.vo.UserVo;
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
import java.util.Map;

/**
 * Created by miss on 2017/7/3.
 */
@Api(value = "用户-用户接口", description = "用户-用户接口")
@RestController
@RequestMapping(value = "api/user/user", method = RequestMethod.POST)
public class ApiUserController {


    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CollectService collectService;



    @RequestMapping(value = "/authc/updateLocation", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户定位位置", httpMethod = "POST", notes = "更新用户定位位置")
    public Rst<Object> updateLocation(
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "longitude") @RequestParam(required = true, value = "longitude") String longitude,
            @ApiParam(value = "latitude") @RequestParam(required = true, value = "latitude") String latitude
    ) throws BusinessException {

        Rst<Object> rst = new Rst<>();

        userService.updateLocation(uid,longitude, latitude);

        return rst;
    }

    @RequestMapping(value = "/authc/getUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户信息(自己)", httpMethod = "POST", notes = "获取用户信息(自己)")
    public Rst<UserVo> updateLocation(
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid
    ) throws BusinessException {

        Rst<UserVo> rst = new Rst<>();
        rst.setObject(userService.getUserInfo(uid));
        return rst;
    }

    @RequestMapping(value = "/authc/getCollectNums", method = RequestMethod.POST)
    @ApiOperation(value = "收藏的店铺数量", httpMethod = "POST", notes = "收藏的店铺数量")
    public Rst<Integer> getCollectNums(
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid
    ) throws BusinessException {

        Rst<Integer> rst = new Rst<>();

        rst.setObject(collectService.queryMyCollectNum(uid));
        return rst;
    }

    @RequestMapping(value = "/authc/getCollects", method = RequestMethod.POST)
    @ApiOperation(value = "分页获取收藏列表", httpMethod = "POST", notes = "分页获取收藏列表")
    public Rst<Pager<MerchantVo>> getCollects(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid
    ) throws BusinessException {

        Rst<Pager<MerchantVo>> rst = new Rst<>();

        rst.setObject(collectService.queryMyCollects(uid,pageable));


        return rst;
    }

    @RequestMapping(value = "/authc/collectOrCancel", method = RequestMethod.POST)
    @ApiOperation(value = "收藏与取消收藏", httpMethod = "POST", notes = "收藏与取消收藏")
    public Rst<MerchantVo> collectOrCancel(
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "商户id") @RequestParam(required = true, value = "mid") Long mid,
             @ApiParam(value = "状态 1.收藏,0取消") @RequestParam(required = true, value = "type") Integer type
    ) throws BusinessException {

        Rst<MerchantVo> rst = new Rst<>();

        rst.setObject(collectService.collectOrCancel(uid,mid,type));

        return rst;
    }


    @RequestMapping(value = "/authc/getMyChildNums", method = RequestMethod.POST)
    @ApiOperation(value = "获取下级人数", httpMethod = "POST", notes = "获取下级人数")
    public Rst<Map<String,Object>> getMyChildNums(
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid
    ) throws BusinessException {

        Rst<Map<String,Object>> rst = new Rst<>();
        rst.setObject(userService.getMyChildNums(uid));
        return rst;
    }

    @RequestMapping(value = "/authc/getMyChild", method = RequestMethod.POST)
    @ApiOperation(value = "分页获取下级列表", httpMethod = "POST", notes = "分页获取下级列表")
    public Rst<Pager<OtherUserVo>> getMyChild(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "下级类型 1.直接 2.非直接") @RequestParam(required = true, value = "childType") Integer childType
    ) throws BusinessException {

        Rst<Pager<OtherUserVo>> rst = new Rst<>();
        rst.setObject(userService.getMyChild(uid,childType,pageable));
        return rst;
    }



    @RequestMapping(value = "/authc/duibaLogin",method = RequestMethod.POST)
    @ApiOperation(value = "兑吧登录接口" , httpMethod = "POST", notes = "兑吧登录接口")
    public String duibaLogin(
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "redirectUrl",required = false) @RequestParam(required = false, value = "redirectUrl") String redirectUrl,
            HttpServletRequest request
    )throws BusinessException{

        return userService.duibaLogin(uid,redirectUrl);
    }


}
