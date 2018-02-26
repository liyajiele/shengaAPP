package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.DrawChannel;
import com.sxp.sa.user.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
public interface DrawChannelRepository extends PagingAndSortingRepository<DrawChannel,Long>,JpaSpecificationExecutor<DrawChannel> {



    @Query("from DrawChannel d where d.id=?1 and d.status=1")
    DrawChannel findById(Long id);

    @Query("from DrawChannel dc where dc.user.id=?1 and dc.aliPay=?2 and dc.concatUser=?3 and dc.status=1")
    DrawChannel findByUserAndAlipayUsernameAndNick(Long uid,String aliUsername,String aliNickname);


    /**
     *查询某个用户的所有提现方式
     * @param user
     * @return
     */
    @Query("from DrawChannel d where d.user=?1 and d.status=1  order by d.channelType ASC ,d.createTime DESC ")
    List<DrawChannel> findByUserAndAll(User user);
}
