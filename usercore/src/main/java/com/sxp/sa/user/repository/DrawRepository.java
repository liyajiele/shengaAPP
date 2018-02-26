package com.sxp.sa.user.repository;


import com.sxp.sa.user.entity.Draw;
import com.sxp.sa.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Administrator on 2017/2/24.
 */
public interface DrawRepository extends PagingAndSortingRepository<Draw,Long>,JpaSpecificationExecutor<Draw> {

    /**
     * 用户自己分页查询自己所有提成 信息
     * @param user
     * @param pageable
     * @return
     */
    @Query("from Draw d where d.user =?1 and  d.status=1 order by d.id DESC")
    Page<Draw> findByUserAndPage(User user, Pageable pageable);

    /**
     * 管理员分页查询所有的用户的 一类的(支付宝，银行) 提现信息
     * @param pageable
     * @return
     */
    @Query("from Draw d where d.status=1 and (d.drawStatus='1'or d.drawStatus='4') and  d.createTime  between ?1  and ?2  and d. drawChannel.channelType=?3 order by d.drawStatus DESC,d.id DESC")
    Page<Draw> adminFindAllDrawByPage(Long startL,Long  endL,String scope,Pageable pageable);

    /**
     * 管理员分页查询所有的用户的 提现信息
     * @param pageable
     * @return
     */
    @Query("from Draw d where d.status=1 and (d.drawStatus='1'or d.drawStatus='4') and  d.createTime  between ?1  and ?2   order by d.drawStatus DESC,d.id DESC")
    Page<Draw> adminFindAll(Long startL,Long  endL,Pageable pageable);


    /**
     * 查询该id的提现信息
     * @param id
     * @return
     */
    @Query("from Draw d where d.status =1 and d.id=?1")
    Draw findById(Long id);

}
