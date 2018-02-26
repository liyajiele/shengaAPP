package com.sxp.sa.api.controller.merchant;


import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.order.service.OrderService;
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

@Api(value = "商户-订单相关接口", description = "商户-订单相关接口")
@RestController
@RequestMapping(value = "api/mct/order/authc", method = RequestMethod.POST)
public class ApiMctOrderController {

    @Autowired
    private OrderService orderService;




    @RequestMapping(value = "/getMineOrders", method = RequestMethod.POST)
    @ApiOperation(value = "获取订单", httpMethod = "POST", notes = "获取订单")
    public Rst<Map<String,Object>> getMineOrders(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "商户id") @RequestParam(required = true, value = "mid") Long mid,
            @ApiParam(value = "交易状态",required = false) @RequestParam(required = false, value = "tradeStatus",defaultValue = "8") Integer tradeStatus,
            @ApiParam(value = "起始时间",required = false) @RequestParam(required = false, value = "startTime",defaultValue = "0") Long  startTime,
            @ApiParam(value = "结束时间",required = false) @RequestParam(required = false, value = "endTime",defaultValue = "3723724800000") Long  endTime,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Map<String,Object>> rst = new Rst<>();

        rst.setObject(orderService.getMineOrders(mid,tradeStatus,startTime,endTime,pageable));

        return rst ;
    }
}
