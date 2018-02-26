package com.sxp.sa.api.controller.admin;


import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.merchant.service.MerchantService;
import com.sxp.sa.merchant.vo.MerchantAuditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value = "管理后台-商户相关接口", description = "管理员-商户相关接口 相关接口")
@RestController
@RequestMapping(value = "api/admin/mct", method = RequestMethod.POST)
public class ApiAdminMctController {

    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "/createMerchant", method = RequestMethod.POST)
    @ApiOperation(value = "管理员 创建门店", httpMethod = "POST", notes = "管理人员 创建门店")
    public Rst<MerchantAuditVo> createMerchant(
            @ApiParam(value = "管理员id", required = true) @RequestParam(required = true, value = "aid") Long aid,

            @ApiParam(value = "目标用户id", required = true) @RequestParam(required = true, value = "uid") Long uid,

            @ApiParam(value = "大区id", required = true) @RequestParam(required = true, value = "areaId") Integer areaId,
            @ApiParam(value = "省份id", required = true) @RequestParam(required = true, value = "provinceId") Integer provinceId,
            @ApiParam(value = "城市id", required = true) @RequestParam(required = true, value = "cityId") Integer cityId,
            @ApiParam(value = "区县id", required = true) @RequestParam(required = true, value = "districtId") Integer districtId,

            @ApiParam(value = "店铺名称", required = true) @RequestParam(required = true, value = "mctName") String mctName,
            @ApiParam(value = "店铺地址", required = true) @RequestParam(required = true, value = "addr") String addr,
            @ApiParam(value = "店铺联系电话", required = true) @RequestParam(required = true, value = "mobile") String mobile,
            @ApiParam(value = "营业时间 如:9:00~21:00", required = true) @RequestParam(required = true, value = "shopHours") String shopHours,

            @ApiParam(value = "主图地址", required = true) @RequestParam(required = true, value = "mainImage") String mainImage,
            @ApiParam(value = "images", required = true) @RequestParam(required = true, value = "images") String images,

            @ApiParam(value = "店铺分类 | 分割id 如 1|3", required = true) @RequestParam(required = true, value = "types") String types,

            @ApiParam(value = "经营者姓名", required = true) @RequestParam(required = true, value = "ownerRealName") String ownerRealName,
            @ApiParam(value = "经营者身份证号", required = true) @RequestParam(required = true, value = "ownerIdcard") String ownerIdcard,
            @ApiParam(value = "经营者身份证正面地址", required = true) @RequestParam(required = true, value = "ownerIdcard") String idcardImage1,
            @ApiParam(value = "经营者身份证反面地址", required = true) @RequestParam(required = true, value = "ownerIdcard") String idcardImage2,

            @ApiParam(value = "营业执照类型 1.企业法人,2个体工商", required = true) @RequestParam(required = true, value = "licenceType") Integer licenceType,
            @ApiParam(value = "营业执照 1.已经办理, 2暂无法提供", required = true) @RequestParam(required = true, value = "licenceStatus") Integer licenceStatus,
            @ApiParam(value = "执照照片地址", required = true) @RequestParam(required = true, value = "licenceImage") String licenceImage,

            @ApiParam(value = "longitude", required = true) @RequestParam(required = true, value = "longitude") String longitude,
            @ApiParam(value = "latitude", required = true) @RequestParam(required = true, value = "latitude") String latitude,

            @ApiParam(value = "人均消费", required = true) @RequestParam(required = true, value = "consumerption") Integer consumerption,
            @ApiParam(value = "让利百分比 0.2", required = true) @RequestParam(required = true, value = "profits") Double profits,
            @ApiParam(value = "1全反 2.部分返利", required = true) @RequestParam(required = true, value = "rebateType") Integer rebateType,
            HttpServletRequest request
    ) throws BusinessException {
        Rst<MerchantAuditVo> rst = new Rst<MerchantAuditVo>();

        rst.setObject(merchantService.createMerchant(aid,uid,areaId,provinceId,cityId,districtId,mctName,addr,mobile,shopHours,types,ownerRealName,ownerIdcard,idcardImage1,idcardImage2,licenceType,licenceStatus,licenceImage,mainImage,images,longitude,latitude,consumerption,profits,rebateType));

        return rst;
    }
}
