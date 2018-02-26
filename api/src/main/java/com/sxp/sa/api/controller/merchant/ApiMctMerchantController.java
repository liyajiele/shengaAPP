package com.sxp.sa.api.controller.merchant;

import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.merchant.entity.MyMerchantVo;
import com.sxp.sa.merchant.service.MerchantService;
import com.sxp.sa.merchant.vo.MerchantAuditVo;
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
import java.util.List;

@Api(value = "商户-用户相关接口", description = "商户-用户相关接口")
@RestController
@RequestMapping(value = "api/mct/merchant", method = RequestMethod.POST)
public class ApiMctMerchantController {

    @Autowired
    private UserService userService;
    @Autowired
    private MerchantService merchantService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户登录")
    public Rst<UserVo> login(
            @ApiParam(value = "用户名") @RequestParam(required = true, value = "username") String username,
            @ApiParam(value = "密码") @RequestParam(required = true, value = "password") String password,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<UserVo> rst = new Rst<>();

        UserVo userVo = userService.login(username, password);
        rst.setObject(userVo);
        return rst;
    }


    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    @ApiOperation(value = "用户忘记登陆密码修改", httpMethod = "POST", notes = "用户忘记登陆密码修改")
    public Rst<UserVo> forgetPassword(
            @ApiParam(value = "用户名", required = true) @RequestParam(required = true, value = "username") String username,
            @ApiParam(value = "新的登陆密码", required = true) @RequestParam(required = true, value = "newPassword") String newPassword,
            @ApiParam(value = "短信验证码", required = true) @RequestParam(required = true, value = "code") String code,
            HttpServletRequest request
    ) throws BusinessException {
        Rst<UserVo> rst = new Rst<UserVo>();
        UserVo userVo = userService.forgetUserPassword(username, newPassword, code);
        rst.setObject(userVo);
        return rst;
    }

    @RequestMapping(value = "/authc/getMyAuditInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取审核信息", httpMethod = "POST", notes = "获取审核信息")
    public Rst<List<MerchantAuditVo>> getMyAuditInfo(
            @ApiParam(value = "用户名id", required = true) @RequestParam(required = true, value = "uid") Long uid,
            HttpServletRequest request
    ) throws BusinessException {
        Rst<List<MerchantAuditVo>> rst = new Rst<List<MerchantAuditVo>>();

        rst.setObject(merchantService.getMyAuditInfo(uid));
        return rst;
    }

    @RequestMapping(value = "/authc/getMyFans", method = RequestMethod.POST)
    @ApiOperation(value = "分页获取粉丝", httpMethod = "POST", notes = "分页获取粉丝")
    public Rst<Pager<OtherUserVo>> getMyFans(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "用户名id", required = true) @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "门店id", required = true) @RequestParam(required = true, value = "mid") Long mid,
            HttpServletRequest request
    ) throws BusinessException {
        Rst<Pager<OtherUserVo>> rst = new Rst<Pager<OtherUserVo>>();

        rst.setObject(merchantService.getMyFans(mid,pageable));
        return rst;
    }

    @RequestMapping(value = "/authc/getMyMerchant", method = RequestMethod.POST)
    @ApiOperation(value = "分页获取门店 ", httpMethod = "POST", notes = "分页获取门店 ")
    public Rst<Pager<MyMerchantVo>> getMyMerchant(
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            @ApiParam(value = "用户名id", required = true) @RequestParam(required = true, value = "uid") Long uid,
            HttpServletRequest request
    ) throws BusinessException {
        Rst<Pager<MyMerchantVo>> rst = new Rst<Pager<MyMerchantVo>>();
        rst.setObject(merchantService.getMyMerchant(uid,pageable));
        return rst;
    }

    @RequestMapping(value = "/authc/updateMerchantInfos", method = RequestMethod.POST)
    @ApiOperation(value = "修改门店信息 ", httpMethod = "POST", notes = "修改门店信息 ")
    public Rst<MyMerchantVo> updateMerchantInfos(
            @ApiParam(value = "用户名id", required = true) @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "商户id", required = true) @RequestParam(required = true, value = "mid") Long mid,

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
        Rst<MyMerchantVo> rst = new Rst<MyMerchantVo>();
        rst.setObject(merchantService.updateMerchantInfos(uid,mid,areaId,provinceId,cityId,districtId,mctName,addr,mobile,shopHours,types,ownerRealName,ownerIdcard,idcardImage1,idcardImage2,licenceType,licenceStatus,licenceImage,mainImage,images,longitude,latitude, consumerption,profits,rebateType));
        return rst;
    }




    @RequestMapping(value = "/authc/createMerchant", method = RequestMethod.POST)
    @ApiOperation(value = "创建门店", httpMethod = "POST", notes = "创建门店")
    public Rst<MerchantAuditVo> createMerchant(
            @ApiParam(value = "用户名id", required = true) @RequestParam(required = true, value = "uid") Long uid,

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
            HttpServletRequest request
    ) throws BusinessException {
        Rst<MerchantAuditVo> rst = new Rst<MerchantAuditVo>();

        rst.setObject(merchantService.createMerchant(null,uid,areaId,provinceId,cityId,districtId,mctName,addr,mobile,shopHours,types,ownerRealName,ownerIdcard,idcardImage1,idcardImage2,licenceType,licenceStatus,licenceImage,mainImage,images,longitude,latitude,null,null,null));

        return rst;
    }
}
