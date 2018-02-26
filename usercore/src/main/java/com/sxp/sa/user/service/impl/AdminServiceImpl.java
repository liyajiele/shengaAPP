package com.sxp.sa.user.service.impl;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.basic.utils.Identities;
import com.sxp.sa.user.entity.*;
import com.sxp.sa.user.repository.*;
import com.sxp.sa.user.service.AdminService;
import com.sxp.sa.user.vo.AdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sxp.sa.basic.constant.Const.Code.*;
import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.isEmpty;
import static com.sxp.sa.basic.utils.Util.isNotEmpty;

/**
 * Created by Administrator on 2017/2/24.
 */
@Service
public class AdminServiceImpl extends BaseService implements AdminService {


    @Autowired
    private AdminRepository adminDao;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Autowired
    private UrlFilterList urlFilterList;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;
    /**
     * 根据用户名查找角色
     * @param username
     * @return
     * @throws BusinessException
     */
//    @Override
//    public Set<String> findRoles(String username){
//        List<Role> roles = adminDao.findRolesByUsername(username);
//        Set<String> roleSet = new HashSet<>();
//        for(int i = 0 ;i<roles.size();i++){
//            roleSet.add(roles.get(i).getRole());
//        }
//        return roleSet;
//    }


//    @Override
//    public Set<String> findPermission(String username){
//        List<Role> roles = adminDao.findRolesByUsername(username);
//        Set<String> roleSet = new HashSet<>();
//        for(int i = 0 ;i<roles.size();i++){
//            List<Resource> resourcesList = roles.get(i).getResources();
//            for(int j = 0 ;j<resourcesList.size();j++){
//                roleSet.add(resourcesList.get(j).getResourceName());
//            }
//        }
//        return roleSet;
//    }

    /**
     * 根据用户名查找Admin
     * @param username
     * @return
     */
    @Override
    public Admin findByUsername(String username) {
        return adminDao.findByUsername(username);
    }


    /**
     * 登录
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    @Override
    public AdminVo login(String username, String password) throws BusinessException {

        Admin admin = adminDao.findByUsernameAndPassword(username,password);

        //账户或密码错误
        if(isEmpty(admin)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }

        AdminVo adminVo = BeanMapper.map(admin,AdminVo.class);
        adminVo.setToken(this.getToken(admin.getId()));

        return adminVo;
    }


    /**
     * 获取菜单
     * @param aid
     * @return
     * @throws BusinessException
     */
    @Override
    public List<Resource> menuList(Long aid) throws BusinessException {

        List<Resource> resources = new ArrayList<>();

        List<Role> roles = adminRoleRepository.findByAdminId(aid);


        for(int i = 0;i<roles.size();i++){
            List<Resource> resourcesList = roleResourceRepository.findResourceByRoleIdAndPer(roles.get(i).getId(),"html");
            resources.addAll(resourcesList);
        }

        return resources;
    }

    /**
     * 设置admin token缓存
     * @param aid
     * @return
     */
    public String getToken(Long aid){
        String userToken = Identities.uuid2();
        String tk = redisTemplate.opsForValue().get("admin"+aid);
        if(isNotEmpty(tk) && tk.length()>=32){
            return tk;
        }
        redisTemplate.opsForValue().set("admin"+aid,userToken );
        return userToken;
    }


    /**
     * 获取资源列表
     * @return
     * @throws BusinessException
     */
    @Override
    public List<Resource> resourceList() throws BusinessException {

        List<Resource> resources = resourceRepository.findAll();

        return resources;
    }


    /**
     * 增加资源
     * @param aid
     * @param resourceName
     * @param permission
     * @param url
     * @param parentResourceId
     * @return
     * @throws BusinessException
     */
    @Override
    public Resource addResource(Long aid, String resourceName, String permission, String url, Long parentResourceId,Integer priority) throws BusinessException {

        Resource parentResource = null;
        if(isNotEmpty(parentResourceId)){
            parentResource =  resourceRepository.findByIdAndStatus(aid,valid);
            if(isNotEmpty(parentResource)){
                //副资源不存在
                throw new BusinessException(RESOURCE_NOT_EXISTS,REBATE_AMOUNT_ERROR_MSG);
            }
        }


        Resource resource = new Resource();
        resource.setParentResource(parentResource);
        resource.setCreateUser(aid);
        resource.setResourceName(resourceName);
        resource.setPermission(permission);
        resource.setUrl(url);
        resource.setPriority(priority);

        resource = resourceRepository.save(resource);


        //更新资源
        urlFilterList.initFilterList();


        return resource;
    }


    /**
     * 修改资源
     * @param aid
     * @param resourceid
     * @param resourceName
     * @param permission
     * @param url
     * @param parentResourceId
     * @param priority
     * @return
     * @throws BusinessException
     */
    @Override
    public Resource modifyResource(Long aid, Long resourceid, String resourceName, String permission, String url, Long parentResourceId, Integer priority) throws BusinessException {
        Resource parentResource = null;
        if(isNotEmpty(parentResourceId)){
            parentResource =  resourceRepository.findByIdAndStatus(aid,valid);
            if(isNotEmpty(parentResource)){
                //副资源不存在
                throw new BusinessException(RESOURCE_NOT_EXISTS,REBATE_AMOUNT_ERROR_MSG);
            }
        }


        Resource resource = resourceRepository.findByIdAndStatus(resourceid,valid);
        if(isEmpty(resource)){
            //副资源不存在
            throw new BusinessException(RESOURCE_NOT_EXISTS,REBATE_AMOUNT_ERROR_MSG);
        }
        resource.setParentResource(parentResource);
        resource.setModifyUser(aid);
        resource.setResourceName(resourceName);
        resource.setPermission(permission);
        resource.setUrl(url);
        resource.setPriority(priority);

        resource = resourceRepository.save(resource);


        //更新资源
        urlFilterList.initFilterList();


        return resource;
    }


    /**
     * 删除资源
     * @param aid
     * @param resourceId
     * @throws BusinessException
     */
    @Override
    public void delResource(Long aid, Long resourceId) throws BusinessException {

        roleResourceRepository.delByResourceId(resourceId,aid);


        resourceRepository.deleteById(resourceId,aid);

        //更新资源
        urlFilterList.initFilterList();

    }

    /**
     * 增加角色
     * @param aid
     * @param role
     * @param description
     * @param priority
     * @return
     * @throws BusinessException
     */
    @Override
    public Role addRole(Long aid, String role, String description, Integer priority) throws BusinessException {


        Role newRole = new Role();
        newRole.setCreateUser(aid);
        newRole.setRole(role);
        newRole.setDescription(description);
        newRole.setPriority(priority);

        roleRepository.save(newRole);

        //更新资源
        urlFilterList.initFilterList();

        return newRole;
    }

    /**
     * 查询所有角色
     * @param aid
     * @return
     * @throws BusinessException
     */
    @Override
    public List<Role> roleList(Long aid) throws BusinessException {


        List<Role> roles = roleRepository.findAll(valid);

        return roles;
    }

    /**
     * 查询某个用户的角色
     * @param aid
     * @return
     * @throws BusinessException
     */
    @Override
    public List<Role> myRoleList(Long aid) throws BusinessException {

        return adminRoleRepository.findByAdminId(aid);
    }

    /**
     * 修改角色信息
     * @param aid
     * @param role
     * @param description
     * @param priority
     * @return
     * @throws BusinessException
     */
    @Override
    public Role modifyRole(Long aid,Long roleId, String role, String description, Integer priority) throws BusinessException {


        Role r = roleRepository.findByIdAndStatus(roleId,valid);

        if(isEmpty(r)){
            throw new BusinessException(ROLE_NOT_EXISTS,ROLE_NOT_EXISTS_MSG);
        }

        r.setModifyUser(aid);
        r.setRole(role);
        r.setDescription(description);
        r.setPriority(priority);

        r = roleRepository.save(r);

        //更新资源
        urlFilterList.initFilterList();

        return r;
    }

    /**
     * 删除角色
     * @param aid
     * @param roleId
     */
    @Override
    public void delRole(Long aid, Long roleId) {

        //删除 admin-role关联
        adminRoleRepository.delByRoleId(roleId,aid);

        //删除 role-resource 关联
        roleResourceRepository.delByRoleId(roleId,aid);

        //删除 role
        Role role = roleRepository.findByIdAndStatus(roleId,valid);
        if(isNotEmpty(role)){
            role.setStatus(0);
            role.setModifyUser(aid);
            role.setModifyDesc("del");

            roleRepository.save(role);
        }



        //更新资源
        urlFilterList.initFilterList();
    }


    @Override
    public AdminRole addAdminRole(Long aid, Long taid, Long roleId) throws BusinessException {


        //查询目标用户
        Admin admin = adminDao.findByAdminId(taid);
        if(isEmpty(admin)){
            throw new BusinessException(ADMIN_NOT_EXISTS,ADMIN_NOT_EXISTS_MSG);
        }


        //查询目标角色
        Role role = roleRepository.findByIdAndStatus(roleId,valid);
        if(isEmpty(role)){
            throw new BusinessException(ROLE_NOT_EXISTS,ROLE_NOT_EXISTS_MSG);
        }

        //查询 用户-角色  如果不存在则增加
        AdminRole ar = adminRoleRepository.findByAdminIdAndRoleId(taid,roleId);
        if(isEmpty(ar)){
            ar = new AdminRole();
            ar.setModifyUser(aid);
            ar.setModifyDesc("add");
            ar.setAdmin(admin);
            ar.setRole(role);

            ar = adminRoleRepository.save(ar);

        }



        //更新资源
        urlFilterList.initFilterList();
        return ar;
    }

    @Override
    public void delAdminRole(Long aid, Long taid, Long roleId) throws BusinessException {

        //查询 用户-角色  如果不存在则增加
        AdminRole ar = adminRoleRepository.findByAdminIdAndRoleId(taid,roleId);
        if(isNotEmpty(ar)){

            ar.setStatus(Const.Status.delete);
            ar.setModifyUser(aid);
            ar.setModifyDesc("del");

            ar = adminRoleRepository.save(ar);

        }


        //更新资源
        urlFilterList.initFilterList();
    }

    /**
     * 根据角色查找资源
     * @param roleId
     * @return
     * @throws BusinessException
     */
    @Override
    public List<Resource> findResourceByRole(Long roleId) throws BusinessException {

        List<Resource> resources = roleResourceRepository.findResourceByRoleIdAndPer(roleId);

        return resources;
    }

    /**
     * 给角色增加资源
     * @param aid
     * @param resourceId
     * @param roleId
     * @return
     * @throws BusinessException
     */
    @Override
    public RoleResource addRoleResource(Long aid, Long resourceId, Long roleId) throws BusinessException {

        //查询目标资源
        Resource resource = resourceRepository.findByIdAndStatus(resourceId,valid);
        if(isEmpty(resource)){
            throw new BusinessException(RESOURCE_NOT_EXISTS,RESOURCE_NOT_EXISTS_MSG);
        }

        //查询目标角色
        Role role = roleRepository.findByIdAndStatus(roleId,valid);
        if(isEmpty(role)){
            throw new BusinessException(ROLE_NOT_EXISTS,ROLE_NOT_EXISTS_MSG);
        }

        //查询 role-resource 记录
        RoleResource roleResource  = roleResourceRepository.findByRoleIdAndResourceId(roleId,resourceId);
        if(isEmpty(roleResource)){
            roleResource = new RoleResource();
            roleResource.setCreateUser(aid);
            roleResource.setModifyUser(aid);
            roleResource.setModifyDesc("add");

            roleResource.setRole(role);
            roleResource.setResource(resource);

            roleResource = roleResourceRepository.save(roleResource);
        }




        //更新资源
        urlFilterList.initFilterList();
        return roleResource;
    }


    @Override
    public void delRoleResource(Long aid, Long resourceId, Long roleId) throws BusinessException {

        //查询 role-resource 记录
        RoleResource roleResource  = roleResourceRepository.findByRoleIdAndResourceId(roleId,resourceId);
        if(isNotEmpty(roleResource)){
            roleResource.setStatus(Const.Status.delete);
            roleResource.setModifyUser(aid);
            roleResource.setModifyDesc("del");

            roleResource = roleResourceRepository.save(roleResource);
        }


        //更新资源
        urlFilterList.initFilterList();
    }


    /**
     * 将普通用户绑定为管理员
     * @param aid
     * @param uid
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    @Override
    public AdminVo bindAdmin(Long aid, Long uid,String desc, String username, String password) throws BusinessException {


        User user = userRepository.findByIdAndStatus(uid,valid);
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }

        //设置账号密码
        if(isNotEmpty(username) && isNotEmpty(password)){
            user.setUsername(username);
            user.setPassword(password);
            userRepository.save(user);
        }

        Admin admin = adminRepository.findByUserId(uid);
        if(isNotEmpty(admin)){
            throw new BusinessException(IS_ADMIN_NOW,IS_ADMIN_NOW_MSG);
        }
        admin = new Admin();
        admin.setCreateUser(aid);
        admin.setModifyDesc("add");
        admin.setDescription(desc);
        admin.setUser(user);

        admin = adminRepository.save(admin);


        return BeanMapper.map(admin,AdminVo.class);
    }
}
