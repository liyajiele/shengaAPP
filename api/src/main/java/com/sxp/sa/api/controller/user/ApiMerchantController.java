package com.sxp.sa.api.controller.user;


import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.merchant.entity.MerchantType;
import com.sxp.sa.merchant.service.MerchantService;
import com.sxp.sa.merchant.vo.MerchantDetailVo;
import com.sxp.sa.merchant.vo.MerchantVo;
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
import java.util.List;
import java.util.Map;

@Api(value = "用户-商户相关接口", description = "用户-商户相关接口")
@RestController
@RequestMapping(value = "api/user/merchant", method = RequestMethod.POST)
public class ApiMerchantController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/nearByMerchant", method = RequestMethod.POST)
    @ApiOperation(value = "附近商户", httpMethod = "POST", notes = "附近商户,按距离排序,分页")
    public Rst<Pager<MerchantVo>> cityList(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "区域id",required = false) @RequestParam(required = false, value = "districtId") Integer districtId,

            @ApiParam(value = "typeId",required = false) @RequestParam(required = false, value = "typeId") Long typeId,
            @ApiParam(value = "searchStr",required = false) @RequestParam(required = false, value = "searchStr") String searchStr,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Pager<MerchantVo>> rst = new Rst<>();

        rst.setObject(merchantService.nearByMerchant(uid,typeId,districtId,searchStr,pageable));

        return rst ;
    }


    @RequestMapping(value = "/allMerchantTypes", method = RequestMethod.POST)
    @ApiOperation(value = "查询所有的商家类型", httpMethod = "POST", notes = "查询所有的商家类型")
    public Rst<List<MerchantType>> allMerchantTypes(
            HttpServletRequest request
    ) throws BusinessException {

        Rst<List<MerchantType>> rst = new Rst<>();

        rst.setObject(merchantService.allMerchantTypes());

        return rst ;
    }


    @RequestMapping(value = "/getMerchantDetail", method = RequestMethod.POST)
    @ApiOperation(value = "查看商家详情", httpMethod = "POST", notes = "查看商家详情")
    public Rst<MerchantDetailVo> merchantDetail(
            @ApiParam(value = "商家id",required = true) @RequestParam(required = true, value = "mid") Long mid,
            @ApiParam(value = "用户id",required = false) @RequestParam(required = false, value = "uid") Long uid,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<MerchantDetailVo> rst = new Rst<>();

        MerchantDetailVo mvo = merchantService.getMerchantDetail(mid,uid);
        //获取两条评论
        mvo = orderService.addCommons(mvo);

        rst.setObject(mvo);

        return rst ;
    }

    @RequestMapping(value = "/getMerchantCommons", method = RequestMethod.POST)
    @ApiOperation(value = "查看商家评论列表", httpMethod = "POST", notes = "查看商家评论列表,分页")
    public Rst<Map<String,Object>> merchantDetail(
            @ApiParam(value = "商家id",required = true) @RequestParam(required = true, value = "mid") Long mid,
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Map<String,Object>> rst = new Rst<>();

        Map<String,Object> mvo = orderService.getMerchantCommons(mid,pageable);

        rst.setObject(mvo);

        return rst ;
    }
}
