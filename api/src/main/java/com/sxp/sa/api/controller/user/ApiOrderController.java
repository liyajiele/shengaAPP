package com.sxp.sa.api.controller.user;


import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.basic.utils.Util;
import com.sxp.sa.order.service.OrderService;
import com.sxp.sa.order.vo.EvlOrderVo;
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

@Api(value = "用户-订单相关接口", description = "用户-订单相关接口")
@RestController
@RequestMapping(value = "api/user/order", method = RequestMethod.POST)
public class ApiOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/authc/getMineOrderToBeEvaluated", method = RequestMethod.POST)
    @ApiOperation(value = "获取自己的待评价订单", httpMethod = "POST", notes = "获取自己的待评价订单")
    public Rst<Pager<EvlOrderVo>> getMineOrderToBeEvaluated(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Pager<EvlOrderVo>> rst = new Rst<>();

        rst.setObject(orderService.getMineOrderToBeEvaluated(uid,pageable));

        return rst ;
    }

    @RequestMapping(value = "/authc/evalorder", method = RequestMethod.POST)
    @ApiOperation(value = "评价订单", httpMethod = "POST", notes = "评价订单")
    public Rst<EvlOrderVo> evalorder(
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "订单id") @RequestParam(required = true, value = "orderId") Long orderId,
            @ApiParam(value = "1.显示 ,2 匿名评论") @RequestParam(required = true, value = "retaType") Integer  retaType,
            @ApiParam(value = "评价内容") @RequestParam(required = true, value = "retaContretaCont") String retaCont,
            @ApiParam(value = "评价星级") @RequestParam(required = true, value = "retaStar") Double retaStar,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<EvlOrderVo> rst = new Rst<>();

        rst.setObject(orderService.evalorder(uid,orderId,retaType,retaCont,retaStar));

        return rst ;
    }


    @RequestMapping(value = "/authc/getMineOrderToBeEvaluatedNums", method = RequestMethod.POST)
    @ApiOperation(value = "获取自己的待评价订单数量", httpMethod = "POST", notes = "获取自己的待评价订单数量")
    public Rst<Integer> getMineOrderToBeEvaluatedNums(
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Integer> rst = new Rst<>();

        rst.setObject(orderService.getMineOrderToBeEvaluatedNums(uid));

        return rst ;
    }

    @RequestMapping(value = "/authc/getMineRebetaOrders", method = RequestMethod.POST)
    @ApiOperation(value = "获取返利订单", httpMethod = "POST", notes = "获取返利订单")
    public Rst<Map<String,Object>> getMineRebetaOrders(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "起始时间",required = false) @RequestParam(required = false, value = "startTime",defaultValue = "0") Long  startTime,
            @ApiParam(value = "结束时间",required = false) @RequestParam(required = false, value = "endTime",defaultValue = "3723724800000") Long  endTime,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Map<String,Object>> rst = new Rst<>();

        rst.setObject(orderService.getMineRebetaOrders(uid,startTime,endTime,pageable));

        return rst ;
    }

    @RequestMapping(value = "/authc/getMineFinishedOrders", method = RequestMethod.POST)
    @ApiOperation(value = "获取已完成订单", httpMethod = "POST", notes = "获取已完成订单")
    public Rst<Map<String,Object>> getMineFinishedOrders(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "起始时间",required = false) @RequestParam(required = false, value = "startTime",defaultValue = "0") Long  startTime,
            @ApiParam(value = "结束时间",required = false) @RequestParam(required = false, value = "endTime",defaultValue = "3723724800000") Long  endTime,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Map<String,Object>> rst = new Rst<>();

        rst.setObject(orderService.getMineFinishedOrders(uid,startTime,endTime,pageable));

        return rst ;
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ApiOperation(value = "下单支付",  notes = "下单支付")
    public String pay(
            @ApiParam(value = "用户id",required = false) @RequestParam(required = false, value = "uid",defaultValue = "0") Long uid,
            @ApiParam(value = "商户id") @RequestParam(required = true, value = "mid") Long mid,
            @ApiParam(value = "支付方式  wx_pay wx_scan ali_pay balance_pay ") @RequestParam(required = true, value = "payType") String payType,
            @ApiParam(value = "总金额") @RequestParam(required = true, value = "totalAmount") Double totalAmount,
            @ApiParam(value = "参与返利额度") @RequestParam(required = true, value = "rebateAmount") Double rebateAmount,
            @ApiParam(value = "使用余额额度") @RequestParam(required = true, value = "balanceAmount") Double balanceAmount,

            @ApiParam(value = "longitude",required = false) @RequestParam(required = false, value = "longitude") String longitude,
            @ApiParam(value = "latitude",required = false) @RequestParam(required = false, value = "latitude") String latitude,
            HttpServletRequest request
    ) throws BusinessException {

        String ip = Util.getRemoteHost(request);


       return orderService.pay(uid,mid,payType,totalAmount,rebateAmount,balanceAmount,ip,longitude,latitude);

    }
}
