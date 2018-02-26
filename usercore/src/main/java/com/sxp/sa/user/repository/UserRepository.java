package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by sxp
 * on 2016/12/23.
 */
public interface UserRepository  extends PagingAndSortingRepository<User,Long>,JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Query("from User u where u.username=?1 and u.status=?2")
    User findByUsername(String username,Integer status);

    /**
     * 根据id和状态查询用户信息
     * @param id
     * @param status
     * @return
     */
    User findByIdAndStatus(Long id,Integer status);


    /**
     * 昵称模糊查询
     * @param nickname
     * @param status
     * @return
     */
    @Query("from User u where u.nickname like ?1 and u.status=?2")
    List<User>  findByNicknamelist(String nickname, Integer status);


    @Query("select count(u.id) from User u where u.status=1 and u.parent1=?1")
    Integer findMyChild1Nums(Long uid);

    @Query("from User u where u.status=1 and u.parent1=?1")
    Page<User> findMyChild1(Long uid,Pageable pageable);

    @Query("select count(u.id) from User u where u.status=1 and u.parent2=?1")
    Integer findMyChild2Nums(Long uid);

    @Query("from User u where u.status=1 and u.parent2=?1")
    Page<User> findMyChild2(Long uid,Pageable pageable);


    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Query("from User u where u.username=?1 and u.status=1")
    User findByUsername(String username);


    @Query("from User u where u.status=1 and u.merchantId=?1")
    Page<User> findByMerchantId(Long mid,Pageable pageable);

    @Query("from User u where u.agentId=?1 and u.status=1")
    Integer findCountAgentFans(Long agentId);
}
