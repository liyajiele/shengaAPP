package com.sxp.sa.agent.service.impl;


import com.sxp.sa.agent.entity.AgentInfos;
import com.sxp.sa.agent.repository.AgentInfosRepository;
import com.sxp.sa.agent.service.AgentInfosService;
import com.sxp.sa.agent.vo.AgentInfosVo;
import com.sxp.sa.basic.entity.District;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.repository.DistrictRepository;
import com.sxp.sa.basic.service.BaseService;
import com.sxp.sa.basic.utils.BeanMapper;
import com.sxp.sa.merchant.repository.MerchantRepository;
import com.sxp.sa.user.entity.Account;
import com.sxp.sa.user.entity.Admin;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.repository.AccountRepository;
import com.sxp.sa.user.repository.AdminRepository;
import com.sxp.sa.user.repository.UserRepository;
import com.sxp.sa.user.vo.AccountVo;
import com.sxp.sa.user.vo.AdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.sxp.sa.basic.constant.Const.Code.*;
import static com.sxp.sa.basic.constant.Const.Status.valid;
import static com.sxp.sa.basic.utils.Util.isEmpty;
import static com.sxp.sa.basic.utils.Util.isNotEmpty;

@Service
public class AgentInfosServiceImpl extends BaseService implements AgentInfosService{

    @Autowired
    private AgentInfosRepository agentInfosRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DistrictRepository districtRepository;



    /**
     * 绑定为代理商
     * @param aid
     * @param uid
     * @param districtId
     * @param description
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    @Override
    @Transactional
    public AgentInfosVo bindAgent(Long aid, Long uid, Integer districtId, String description, String username, String password,Long startTime,Long endTime, Long zhaoshangManagerId,Long kehuManagerId) throws BusinessException {

        //对应用户信息
        User user = userRepository.findByIdAndStatus(uid,valid);
        if(isEmpty(user)){
            throw new BusinessException(USER_NOT_EXISTS,USER_NOT_EXISTS_MSG);
        }
        // 查询目标区域  及 是否被代理
        District district = districtRepository.findById(districtId);
        if(isEmpty(district)){
            throw new BusinessException(DISTRICT_NOT_EXISTS,DISTRICT_NOT_EXISTS_MSG);
        }
        //是否已经被代理
        AgentInfos oAgentInfos = agentInfosRepository.findByDistrictId(districtId);
        if(isNotEmpty(oAgentInfos)){
            throw new BusinessException(DISTRICT_HAD_AGENT,DISTRICT_HAD_AGENT_MSG);
        }


        //招商经理是否存在
        AdminVo zhaoShangManagerVo = null;
        if(isNotEmpty(zhaoshangManagerId)){
             Admin zhaoShangManager = adminRepository.findByAdminId(zhaoshangManagerId);
             if(isNotEmpty(zhaoShangManager)){
                 zhaoShangManagerVo = BeanMapper.map(zhaoShangManager,AdminVo.class);
             }

        }
        //客户经理
        AdminVo kehuManagerVo = null;
        if(isNotEmpty(kehuManagerId)){
            Admin kehuManager = adminRepository.findByAdminId(kehuManagerId);
            if(isNotEmpty(kehuManager)){
                kehuManagerVo = BeanMapper.map(kehuManager,AdminVo.class);
            }
        }





        //设置账号密码
        if(isNotEmpty(username) && isNotEmpty(password)){
            user.setUsername(username);
            user.setPassword(password);

            user = userRepository.save(user);
        }


        Admin admin = adminRepository.findByUserId(uid);
        //如果该用户以前不是管理员
        if(isEmpty(admin)){
            admin = new Admin();
            admin.setUser(user);
            admin.setDescription(description);
            admin.setCreateUser(aid);
            admin.setModifyDesc("增加代理商");

            admin = adminRepository.save(admin);
        }

        AgentInfos agentInfos = new AgentInfos();
        agentInfos.setUser(user);
        agentInfos.setAgentLevel(5);
        agentInfos.setDistrict(district);
        agentInfos.setZhaoShangAid(zhaoshangManagerId);
        agentInfos.setKehuAid(kehuManagerId);
        agentInfos.setCreateTime(aid);
        agentInfos.setModifyDesc("增加代理区域");
        agentInfos.setStartTime(System.currentTimeMillis());
        agentInfos.setEndTime(System.currentTimeMillis()+365*24*3600*1000);
        if(isNotEmpty(startTime)) agentInfos.setStartTime(startTime);
        if(isNotEmpty(endTime)) agentInfos.setEndTime(endTime);

        agentInfos.setAgentInfo(description);

        agentInfos = agentInfosRepository.save(agentInfos);


        AgentInfosVo agentInfosVo = BeanMapper.map(agentInfos,AgentInfosVo.class);
        agentInfosVo.setZhaoShangManager(zhaoShangManagerVo);
        agentInfosVo.setKuhuManager(kehuManagerVo);


        //todo 代理商角色


        return agentInfosVo;
    }


    /**
     * 管理员搜索 代理商列表
     * @param aid
     * @param searchStr
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<AgentInfosVo> findAgentListByAdmin(Long aid, String searchStr, Pageable pageable) throws BusinessException {

        Page<AgentInfos> agentInfosPage = null;
        //如果搜索字符串为空
        if(isEmpty(searchStr)){
            agentInfosPage = agentInfosRepository.findByPage(pageable);

        }else{
            agentInfosPage = agentInfosRepository.findByPageAndSearchStr("%"+searchStr+"%",pageable);
        }


        return this.getInfosVoHelper(agentInfosPage);

    }

    private Pager<AgentInfosVo> getInfosVoHelper(  Page<AgentInfos> agentInfosPage){
        List<AgentInfosVo> infosVos = new ArrayList<>();


        for(int i = 0 ;i<agentInfosPage.getContent().size();i++){
            AgentInfos agentInfos = agentInfosPage.getContent().get(i);

            AgentInfosVo avo = BeanMapper.map(agentInfos,AgentInfosVo.class);
            if(isNotEmpty(agentInfos.getKehuAid())){
                avo.setKuhuManager(BeanMapper.map(adminRepository.findByAdminId(agentInfos.getKehuAid()),AdminVo.class));
            }
            if(isNotEmpty(agentInfos.getZhaoShangAid())){
                avo.setZhaoShangManager(BeanMapper.map(adminRepository.findByAdminId(agentInfos.getZhaoShangAid()),AdminVo.class));
            }

            //账户余额
            User agentUser = agentInfos.getUser();
            Account account = accountRepository.findByUser(agentUser.getId());
            if(isNotEmpty(account)){
                avo.setAccount(BeanMapper.map(account, AccountVo.class));
            }

            //区域内商家数
            Integer mctNums = merchantRepository.findCountMctNumsByDistrict(avo.getDistrict().gettId());
            if(isEmpty(mctNums)) mctNums = 0;
            avo.setMctNums(mctNums);

            //粉丝数
            Integer fansNums = userRepository.findCountAgentFans(avo.getUser().getId());
            if(isEmpty(fansNums)) fansNums=0;
            avo.setFansNums(fansNums);

            infosVos.add(avo);

        }

        Pager<AgentInfosVo> rst = new Pager<>();
        rst.setLast(agentInfosPage.isLast());
        rst.setFirst(agentInfosPage.isFirst());
        rst.setNumber(agentInfosPage.getNumber());
        rst.setNumberOfElements(agentInfosPage.getNumberOfElements());
        rst.setSize(agentInfosPage.getSize());
        rst.setTotalElements(agentInfosPage.getTotalElements());
        rst.setTotalPages(agentInfosPage.getTotalPages());
        rst.setContent(infosVos);

        return rst;

    }

    /**
     * 根据代理商 uid 获取代理信息
     * @param aid
     * @param uid
     * @param pageable
     * @return
     * @throws BusinessException
     */
    @Override
    public Pager<AgentInfosVo> findAgentListByAgentUid(Long aid, Long uid, Pageable pageable) throws BusinessException {


        Page<AgentInfos> agentInfosPage = agentInfosRepository.findAgentListByAgentUid(uid,pageable);

        return this.getInfosVoHelper(agentInfosPage);
    }
}
