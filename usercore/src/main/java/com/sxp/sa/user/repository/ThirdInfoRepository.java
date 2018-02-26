package com.sxp.sa.user.repository;

import com.sxp.sa.user.entity.ThirdInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/2/19.
 */
public interface ThirdInfoRepository  extends PagingAndSortingRepository<ThirdInfo,Long>,JpaSpecificationExecutor<ThirdInfo> {


    @Query("from ThirdInfo ti where ti.openId=?1 and ti.thirdType=?2 and ti.status=1")
    ThirdInfo findByOpenIdAndThirdType(String openId, String thirdType);


    @Query("from ThirdInfo ti where ti.id=?1 and ti.status=1")
    ThirdInfo findById(Long id);

    @Query("from ThirdInfo ti where ti.unionId=?1 and ti.status=1")
    List<ThirdInfo> findByUnionId(String unionId);


    @Query("from ThirdInfo ti where ti.user.id=?1 and ti.thirdType=?2 and ti.thirdName=?3 and ti.status=1")
    ThirdInfo findByUserIdAndThirdTypeAndThirdName(Long userId, String thirdType, String thirdName);
}
