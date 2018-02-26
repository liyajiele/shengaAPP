package com.sxp.sa.api.controller.admin;

import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.user.entity.Resource;
import com.sxp.sa.user.entity.Role;
import com.sxp.sa.user.service.AdminService;
import com.sxp.sa.user.vo.AdminVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "管理后台-用户及权限相关接口", description = "管理员-用户相关接口")
@RestController
@RequestMapping(value = "api/admin/user", method = RequestMethod.POST)
public class ApiAdminUserController {


    @Autowired
    private AdminService adminService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户登录")
    public Rst<AdminVo> login(
            @ApiParam(value = "用户名") @RequestParam(required = true, value = "username") String username,
            @ApiParam(value = "密码") @RequestParam(required = true, value = "password") String password
    ) throws BusinessException {

        Rst<AdminVo> rst = new Rst<>();

        rst.setObject(adminService.login(username,password));

        return rst;

    }


    @RequestMapping(value = "/menuList", method = RequestMethod.POST)
    @ApiOperation(value = "菜单", httpMethod = "POST", notes = "菜单")
    public Rst<List<Resource>> menuList(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid
    ) throws BusinessException {

        Rst<List<Resource>> rst = new Rst<>();

        rst.setObject(adminService.menuList(aid));

        return rst;

    }



    @RequestMapping(value = "/resourceList", method = RequestMethod.POST)
    @ApiOperation(value = "获取资源列表", httpMethod = "POST", notes = "获取资源列表")
    public Rst<List<Resource>> resourceList(
    ) throws BusinessException {

        Rst<List<Resource>> rst = new Rst<>();

        rst.setObject(adminService.resourceList());

        return rst;

    }

    @RequestMapping(value = "/addResource", method = RequestMethod.POST)
    @ApiOperation(value = "增加资源", httpMethod = "POST", notes = "增加资源")
    public Rst<Resource> addResource(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "资源名") @RequestParam(required = true, value = "resourceName") String  resourceName,
            @ApiParam(value = "资源字符串 html ,api") @RequestParam(required = true, value = "permission") String  permission,
            @ApiParam(value = "url") @RequestParam(required = true, value = "url") String  url,
            @ApiParam(value = "父资源 id",required = false) @RequestParam(required = false, value = "parentResourceId") Long  parentResourceId,
            @ApiParam(value = "优先级",required = false) @RequestParam(required = false, value = "priority",defaultValue = "1") Integer  priority
    ) throws BusinessException {

        Rst<Resource> rst = new Rst<>();

        rst.setObject(adminService.addResource(aid,resourceName,permission,url,parentResourceId,priority));

        return rst;

    }




    @RequestMapping(value = "/modifyResource", method = RequestMethod.POST)
    @ApiOperation(value = "修改资源", httpMethod = "POST", notes = "修改资源")
    public Rst<Resource> modifyResource(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "资源id") @RequestParam(required = true, value = "resourceId") Long  resourceId,
            @ApiParam(value = "资源名") @RequestParam(required = true, value = "resourceName") String  resourceName,
            @ApiParam(value = "资源字符串 html ,api") @RequestParam(required = true, value = "permission") String  permission,
            @ApiParam(value = "url") @RequestParam(required = true, value = "url") String  url,
            @ApiParam(value = "父资源 id",required = false) @RequestParam(required = false, value = "parentResourceId") Long  parentResourceId,
            @ApiParam(value = "优先级",required = false) @RequestParam(required = false, value = "priority",defaultValue = "1") Integer  priority
    ) throws BusinessException {

        Rst<Resource> rst = new Rst<>();

        rst.setObject(adminService.modifyResource(aid,resourceId,resourceName,permission,url,parentResourceId,priority));

        return rst;

    }



    @RequestMapping(value = "/delResource", method = RequestMethod.POST)
    @ApiOperation(value = "删除资源", httpMethod = "POST", notes = "删除资源")
    public Rst<Object> delResource(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "资源id") @RequestParam(required = true, value = "resourceId") Long  resourceId
    ) throws BusinessException {

        Rst<Object> rst = new Rst<>();

        adminService.delResource(aid,resourceId);

        return rst;

    }




    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ApiOperation(value = "增加角色", httpMethod = "POST", notes = "增加角色")
    public Rst<Role> addRole(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "角色名称 英文") @RequestParam(required = true, value = "role") String  role,
            @ApiParam(value = "角色描述") @RequestParam(required = true, value = "description") String  description,
            @ApiParam(value = "优先级",required = false) @RequestParam(required = false, value = "priority",defaultValue = "1") Integer  priority
    ) throws BusinessException {

        Rst<Role> rst = new Rst<>();

        rst.setObject(adminService.addRole(aid,role,description,priority));

        return rst;

    }

    @RequestMapping(value = "/roleList", method = RequestMethod.POST)
    @ApiOperation(value = "系统角色列表", httpMethod = "POST", notes = "系统角色列表")
    public Rst<List<Role>> roleList(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid
    ) throws BusinessException {

        Rst<List<Role>> rst = new Rst<>();

        rst.setObject(adminService.roleList(aid));

        return rst;

    }

    @RequestMapping(value = "/modifyRole", method = RequestMethod.POST)
    @ApiOperation(value = "修改角色信息", httpMethod = "POST", notes = "修改角色信息")
    public Rst<Role> modifyRole(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "角色id") @RequestParam(required = true, value = "roleId") Long  roleId,
            @ApiParam(value = "角色名称 英文") @RequestParam(required = true, value = "role") String  role,
            @ApiParam(value = "角色描述") @RequestParam(required = true, value = "description") String  description,
            @ApiParam(value = "优先级",required = false) @RequestParam(required = false, value = "priority",defaultValue = "1") Integer  priority
    ) throws BusinessException {

        Rst<Role> rst = new Rst<>();

        rst.setObject(adminService.modifyRole(aid,roleId,role,description,priority));

        return rst;

    }


    @RequestMapping(value = "/delRole", method = RequestMethod.POST)
    @ApiOperation(value = "删除角色信息", httpMethod = "POST", notes = "删除角色信息")
    public Rst<Object> delRole(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "角色id") @RequestParam(required = true, value = "roleId") Long  roleId
    ) throws BusinessException {

        Rst<Object> rst = new Rst<>();

        adminService.delRole(aid,roleId);

        return rst;

    }

    @RequestMapping(value = "/myRoleList", method = RequestMethod.POST)
    @ApiOperation(value = "某人角色列表", httpMethod = "POST", notes = "某人角色列表")
    public Rst<List<Role>> myRoleList(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid
            ) throws BusinessException {

        Rst<List<Role>> rst = new Rst<>();

        rst.setObject(adminService.myRoleList(aid));

        return rst;

    }


    @RequestMapping(value = "/addAdminRole", method = RequestMethod.POST)
    @ApiOperation(value = "给用户增加角色", httpMethod = "POST", notes = "给用户增加角色")
    public Rst<Object> addAdminRole(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "目标管理员") @RequestParam(required = true, value = "taid") Long  taid,
            @ApiParam(value = "角色id") @RequestParam(required = true, value = "roleId") Long  roleId
    ) throws BusinessException {

        Rst<Object> rst = new Rst<>();

        adminService.addAdminRole(aid,taid,roleId);

        return rst;

    }


    @RequestMapping(value = "/delAdminRole", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户的角色", httpMethod = "POST", notes = "删除用户的角色")
    public Rst<Object> delAdminRole(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "目标管理员") @RequestParam(required = true, value = "taid") Long  taid,
            @ApiParam(value = "角色id") @RequestParam(required = true, value = "roleId") Long  roleId
    ) throws BusinessException {

        Rst<Object> rst = new Rst<>();

        adminService.delAdminRole(aid,taid,roleId);

        return rst;

    }




    @RequestMapping(value = "/findResourceByRole", method = RequestMethod.POST)
    @ApiOperation(value = "根据角色查找资源", httpMethod = "POST", notes = "根据角色查找资源")
    public Rst<List<Resource>> findResourceByRole(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "角色id") @RequestParam(required = true, value = "roleId") Long  roleId
    ) throws BusinessException {

        Rst<List<Resource>> rst = new Rst<>();

        rst.setObject(adminService.findResourceByRole(roleId));

        return rst;

    }


    @RequestMapping(value = "/addRoleResource", method = RequestMethod.POST)
    @ApiOperation(value = "给角色增加资源", httpMethod = "POST", notes = "给角色增加资源")
    public Rst<Object> addRoleResource(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "资源id") @RequestParam(required = true, value = "resourceId") Long  resourceId,
            @ApiParam(value = "角色id") @RequestParam(required = true, value = "roleId") Long  roleId
    ) throws BusinessException {

        Rst<Object> rst = new Rst<>();

        adminService.addRoleResource(aid,resourceId,roleId);

        return rst;

    }




    @RequestMapping(value = "/delRoleResource", method = RequestMethod.POST)
    @ApiOperation(value = "删除角色的资源", httpMethod = "POST", notes = "删除角色的资源")
    public Rst<Object> delRoleResource(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "资源id ") @RequestParam(required = true, value = "resourceId") Long  resourceId,
            @ApiParam(value = "角色id") @RequestParam(required = true, value = "roleId") Long  roleId
    ) throws BusinessException {

        Rst<Object> rst = new Rst<>();

        adminService.delRoleResource(aid,resourceId,roleId);

        return rst;

    }



    @RequestMapping(value = "/bindAdmin", method = RequestMethod.POST)
    @ApiOperation(value = "绑定为管理员", httpMethod = "POST", notes = "绑定为管理员")
    public Rst<AdminVo> bindAdmin(
            @ApiParam(value = "管理员id") @RequestParam(required = true, value = "aid") Long  aid,
            @ApiParam(value = "用户id") @RequestParam(required = true, value = "uid") Long  uid,
            @ApiParam(value = "描述信息") @RequestParam(required = true, value = "description") String  description,
            @ApiParam(value = "登录账号",required = false) @RequestParam(required = false, value = "username") String  username,
            @ApiParam(value = "登录密码",required = false) @RequestParam(required = false, value = "password") String   password

    ) throws BusinessException {

        Rst<AdminVo> rst = new Rst<>();

        rst.setObject(adminService.bindAdmin(aid,uid,description,username,password));

        return rst;

    }




}
