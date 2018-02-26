package com.sxp.sa.api.controller;

import com.sxp.sa.basic.entity.*;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.*;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.user.service.UserService;
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

@Api(value = "地址相关接口", description = "地址相关接口")
@RestController
@RequestMapping(value = "api/location", method = RequestMethod.POST)
public class ApiLocationController {

    @Autowired
    private CityService cityService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private HotSearchService hotSearchService;

    @Autowired
    private UserService userService;

    @Autowired
    private HotCityService hotCityService;



    @RequestMapping(value = "/cityList", method = RequestMethod.POST)
    @ApiOperation(value = "获取城市列表", httpMethod = "POST", notes = "获取城市列表")
    public Rst<List<City>> cityList(
            HttpServletRequest request
    ) throws BusinessException {

        Rst<List<City>> rst = new Rst<>();

        rst.setObject(cityService.findAll());

        return rst ;
    }


    @RequestMapping(value = "/provinceList", method = RequestMethod.POST)
    @ApiOperation(value = "省列表", httpMethod = "POST", notes = "省列表")
    public Rst<List<Province>> provinceList(
            HttpServletRequest request
    ) throws BusinessException {

        Rst<List<Province>> rst = new Rst<>();

        rst.setObject(provinceService.queryAllProvince());

        return rst ;
    }

    @RequestMapping(value = "/findCityListByProvinceId", method = RequestMethod.POST)
    @ApiOperation(value = "根据省id获取城市列表", httpMethod = "POST", notes = "根据省id获取城市列表")
    public Rst<List<City>> findCityListByProvinceId(
            @ApiParam(value = "省id",required = true) @RequestParam(required = true,value = "provinceId") Integer provinceId,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<List<City>> rst = new Rst<>();

        rst.setObject(cityService.findCityListByProvinceId(provinceId));

        return rst ;
    }

    @RequestMapping(value = "/findDistrictByCityId", method = RequestMethod.POST)
    @ApiOperation(value = "根据城市id获取区域列表", httpMethod = "POST", notes = "根据城市id获取区域列表")
    public Rst<List<District>> findDistrictByCityId(
            @ApiParam(value = "城市id",required = true) @RequestParam(required = true,value = "cityId") Integer cityId,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<List<District>> rst = new Rst<>();

        rst.setObject(districtService.findDistrictByCityId(cityId));

        return rst ;
    }


    @RequestMapping(value = "/authc/mySearchRecord", method = RequestMethod.POST)
    @ApiOperation(value = "查询我的搜索记录", httpMethod = "POST", notes = "查询我的搜索记录")
    public Rst<Object> mySearchRecord(
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid,
            @PageableDefault(page = 0, size = 30) Pageable pageable
    ) throws BusinessException {

        Rst<Object> rst = new Rst<>();

        rst.setObject(userService.findMySearchRecord(uid,pageable));

        return rst;
    }

    @RequestMapping(value = "/authc/cleanMySearchRecord", method = RequestMethod.POST)
    @ApiOperation(value = "清空我的搜索记录", httpMethod = "POST", notes = "清空我的搜索记录")
    public Rst<Object> cleanMySearchRecord(
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long uid
    ) throws BusinessException {

        Rst<Object> rst = new Rst<>();

        rst.setObject(userService.cleanMySearchRecord(uid));

        return rst;
    }

    @RequestMapping(value = "/nearByHotSearch", method = RequestMethod.POST)
    @ApiOperation(value = "根据城市id获取区域列表", httpMethod = "POST", notes = "根据城市id获取区域列表")
    public Rst<Pager<HotSearch>> findDistrictByCityId(
            @ApiParam(value = "城市id 或 区县id",required = true) @RequestParam(required = true,value = "cityId") Integer cityId,
            @ApiParam(value = "type 1.当前城市,2.当前区域",required = true) @RequestParam(required = true,value = "type") Integer type,
            @PageableDefault(page = 0, size = 30) Pageable pageable,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Pager<HotSearch>> rst = new Rst<>();

        rst.setObject(hotSearchService.nearHotSearch(cityId,type,pageable));

        return rst ;
    }


    @RequestMapping(value = "/hotCity", method = RequestMethod.POST)
    @ApiOperation(value = "热门城市", httpMethod = "POST", notes = "热门城市")
    public Rst<List<HotCity>> findDistrictByCityId(
            HttpServletRequest request
    ) throws BusinessException {

        Rst<List<HotCity>> rst = new Rst<>();

        rst.setObject(hotCityService.queryAllHotCity());

        return rst ;
    }


    @RequestMapping(value = "/getLocationInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取location信息", httpMethod = "POST", notes = "获取location信息")
    public Rst<Map<String,Object>> getLocationInfo(
            @ApiParam(value = "经度",required = true) @RequestParam(required = true,value = "longitude") Double longitude,
            @ApiParam(value = "维度",required = true) @RequestParam(required = true,value = "latitude") Double latitude,
            HttpServletRequest request
    ) throws BusinessException {

        Rst<Map<String,Object>> rst = new Rst<>();

        rst.setObject(districtService.getLocationInfo(longitude,latitude));

        return rst ;
    }


}
