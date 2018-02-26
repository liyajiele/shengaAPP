package com.sxp.sa.user.service;


import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.user.entity.*;
import com.sxp.sa.user.vo.AdminVo;

import java.util.List;

/**
 * Created by Administrator on 2017/2/24.
 */
public interface AdminService {

    /**
     * 根据用户名查询角色集合
     * @param username
     * @return
     * @throws BusinessException
     */
//    Set<String> findRoles(String username) ;

    /**
     * 根据用户名查询权限集合
     * @param username
     * @return
     * @throws BusinessException
     */
//    Set<String> findPermission(String username) ;

    Admin findByUsername(String username);


    /**
     * 登录
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    AdminVo login(String username,String password)throws BusinessException;


    /**
     * 菜单
     * @param aid
     * @return
     * @throws BusinessException
     */
    List<Resource> menuList(Long aid)throws BusinessException;


    /**
     * 获取资源列表
     * @return
     * @throws BusinessException
     */
    List<Resource> resourceList()throws BusinessException;

    /**
     * 增加资源
     * @return
     * @throws BusinessException
     */
    Resource addResource(Long aid,String resourceName,String permission,String url,Long parentResourceId,Integer priority)throws BusinessException;


    /**
     * 修改资源
     * @return
     * @throws BusinessException
     */
    Resource modifyResource(Long aid,Long resourceid,String resourceName,String permission,String url,Long parentResourceId,Integer priority)throws BusinessException;

    /**
     * 删除资源
     * @param aid
     * @param resourceId
     * @throws BusinessException
     */
    void delResource(Long aid,Long resourceId)throws BusinessException;


    /**
     * 增加角色
     * @return
     * @throws BusinessException
     */
    Role addRole(Long aid,String role,String description,Integer priority)throws BusinessException;

    /**
     * 查询所有角色
     * @param aid
     * @return
     * @throws BusinessException
     */
    List<Role> roleList(Long aid)throws BusinessException;


    /**
     * 查询某人的角色
     * @param aid
     * @return
     * @throws BusinessException
     */
    List<Role> myRoleList(Long aid)throws BusinessException;

    /**
     * 修改角色信息
     * @param aid
     * @param role
     * @param description
     * @param priority
     * @return
     * @throws BusinessException
     */
    Role modifyRole(Long aid,Long roleId,String role,String description,Integer priority)throws BusinessException;

    /**
     * 删除角色
     * @param aid
     * @param roleId
     */
    void delRole(Long aid,Long roleId);


    /**
     * 给用户增加角色
     * @param aid
     * @param taid
     * @param roleId
     * @return
     * @throws BusinessException
     */
    AdminRole addAdminRole(Long aid,Long taid,Long roleId)throws BusinessException;

    /**
     * 删除用户的角色
     * @param aid
     * @param taid
     * @param roleId
     * @return
     * @throws BusinessException
     */
    void delAdminRole(Long aid,Long taid,Long roleId)throws BusinessException;


    /**
     * 根据角色查找资源
     * @param roleId
     * @return
     * @throws BusinessException
     */
    List<Resource> findResourceByRole(Long roleId)throws BusinessException;


    /**
     * 给角色增加资源
     * @param aid
     * @param resourceId
     * @param roleId
     * @return
     * @throws BusinessException
     */
    RoleResource addRoleResource(Long aid,Long resourceId,Long roleId)throws BusinessException;


    /**
     * 删除角色的资源
     * @param aid
     * @param resourceId
     * @param roleId
     * @throws BusinessException
     */
    void delRoleResource(Long aid,Long resourceId,Long roleId)throws  BusinessException;


    /**
     * 将普通用户绑定为管理员
     * @return
     * @throws BusinessException
     */
    AdminVo bindAdmin(Long aid,Long uid,String desc,String username,String password)throws BusinessException;
}
