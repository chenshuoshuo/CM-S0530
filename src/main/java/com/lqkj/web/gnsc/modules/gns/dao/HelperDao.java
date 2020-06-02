package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelperDao extends JpaRepository<GnsHelper,Integer> {

    List<GnsHelper> findAllByTypeCode(Integer typeCode);

    /**
     * 根据联系方式判断是否重复
     */
    GnsHelper findByContact(String contact);

    /**
     * 获取当前最大排序值
     */
    @Query(nativeQuery = true,
            value = "SELECT MAX(sm.order_id) FROM gns.gns_helper as sm")
    Integer getMaxOrder();

    /**
     * 自动排序(新增)
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_helper set order_id = order_id + 1 where order_id >= :orderId ")
    void autoOrder(Integer orderId);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_helper set order_id = order_id + 1 where order_id >= :newOrderId and order_id <= :oldOrderId")
    void autoOrderForUpdateDesc(Integer oldOrderId, Integer newOrderId);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_helper set order_id = order_id - 1 where order_id > :oldOrderId and order_id <= :newOrderId ")
    void autoOrderForUpdateAsc(Integer oldOrderId, Integer newOrderId);

    /**
     * 自动排序（删除）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_helper set order_id = order_id - 1 where order_id > :oldOrderId ")
    void autoOrderForDelete(Integer oldOrderId);


}
