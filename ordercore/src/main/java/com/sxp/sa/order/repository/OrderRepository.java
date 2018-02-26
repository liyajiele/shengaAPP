package com.sxp.sa.order.repository;

import com.sxp.sa.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order,Long>,JpaSpecificationExecutor<Order> {

    @Query("from Order o where o.status=?2 and o.id=?1")
    Order findById(Long orderid,Integer status);

    @Query("from Order o where o.status=1 and o.merchant.id=?1 and o.tradeStatus=?2 order by o.id desc")
    Page<Order> findOrderByStatus(Long mid,Integer tradeStatus, Pageable pageable);

    @Query("from Order o where o.status=1 and o.tradeStatus=?2 and o.user.id=?1 order by o.id desc")
    Page<Order> findOrderByTradeStatus(Long uid,Integer tradeStatus,Pageable pageable);

    @Query("from Order o where o.status=1 and o.tradeStatus=?2 and o.user.id=?1 and o.createTime>=?3 and o.createTime<=?4  order by o.id desc")
    Page<Order> findOrderByTradeStatusAndTime(Long uid,Integer tradeStatus,Long startTime,Long endTime,Pageable pageable);

    @Query("select count(o.id) from Order o where o.status=1 and o.tradeStatus=?2 and o.user.id=?1 and o.createTime>=?3 and o.createTime<=?4  ")
    Integer queryOrderNum(Long uid,Integer tradeStatus,Long startTime,Long endTime);

    @Query("select sum(o.rebate) from Order o where o.status=1 and o.tradeStatus=?2 and o.user.id=?1 and o.createTime>=?3 and o.createTime<=?4  ")
    Double queryOrderRebateNum(Long uid,Integer tradeStatus,Long startTime,Long endTime);


    @Query("from Order o where o.status=1 and o.tradeStatus>=?2 and o.user.id=?1 and o.createTime>=?3 and o.createTime<=?4  order by o.id desc")
    Page<Order> findOrderByRetaAndTime(Long uid,Integer tradeStatus,Long startTime,Long endTime,Pageable pageable);

    @Query("select count(o.id) from Order o where o.status=1 and o.tradeStatus>=?2 and o.user.id=?1 and o.createTime>=?3 and o.createTime<=?4  ")
    Integer queryRetaOrderNum(Long uid,Integer tradeStatus,Long startTime,Long endTime);

    @Query("select sum(o.rebate) from Order o where o.status=1 and o.tradeStatus>=?2 and o.user.id=?1 and o.createTime>=?3 and o.createTime<=?4  ")
    Double queryRetaOrderRebateNum(Long uid,Integer tradeStatus,Long startTime,Long endTime);




    @Query("select count(o.id) from  Order o where o.status=1 and o.tradeStatus=4 and o.user.id=?1")
    Integer queryOrderNotEvlNums(Long uid);

    @Query("select avg(o.retaStra) from Order o where o.status=1 AND o.tradeStatus=8 AND o.merchant.id=?1")
    Double selectAvgStar(Long mid);

    @Query("from Order o where o.orderNum=?1 and o.status=?2")
    Order findByOrderNum(String orderNum,Integer status);


    //商户

    @Query("from Order o where o.status=1 and o.tradeStatus=?2 and o.merchant.id=?1 and o.createTime>=?3 and o.createTime<=?4  order by o.id desc")
    Page<Order> findMerchantOrderByTradeStatusAndTime(Long mid,Integer tradeStatus,Long startTime,Long endTime,Pageable pageable);

    @Query("select count(o.id) from Order o where o.status=1 and o.tradeStatus=?2 and o.merchant.id=?1 and o.createTime>=?3 and o.createTime<=?4  ")
    Integer queryMerchantOrderNum(Long mid,Integer tradeStatus,Long startTime,Long endTime);

    @Query("select sum(o.rebate) from Order o where o.status=1 and o.tradeStatus=?2 and o.merchant.id=?1 and o.createTime>=?3 and o.createTime<=?4  ")
    Double queryMerchantOrderRebateNum(Long mid,Integer tradeStatus,Long startTime,Long endTime);
}
