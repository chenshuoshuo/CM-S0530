package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsRegisteringNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface RegisteringNoticeDao extends JpaRepository<GnsRegisteringNotice,Integer> {

    /**
     * h5获取报道须知列表
     */
    @Query(nativeQuery = true,
        value = "select notice_id noticeId,title,update_time updteTime from gns.gns_registering_notice where school_id = :schoolId order by order_id asc")
    List<Map<String,Object>> queryList(Integer schoolId);

    /**
     * 根据报道名称判断是否重复
     */
    GnsRegisteringNotice findBySchoolIdAndTitle(Integer schoolId,String title);

    List<GnsRegisteringNotice> findAllBySchoolId(Integer schoolId);

    /**
     * 获取当前最大排序值
     */
    @Query(nativeQuery = true,
            value = "SELECT MAX(sm.order_id) FROM gns.gns_registering_notice as sm where sm.school_id = :schoolId")
    Integer getMaxOrder(Integer schoolId);

    /**
     * 自动排序(新增)
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_registering_notice set order_id = order_id + 1 where order_id >= :orderId and school_id = :schoolId")
    void autoOrder(Integer orderId,Integer schoolId);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_helper set order_id = order_id + 1 where order_id >= :newOrderId and order_id <= :oldOrderId and school_id = :schoolId")
    void autoOrderForUpdateDesc(Integer oldOrderId, Integer newOrderId,Integer schoolId);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_helper set order_id = order_id - 1 where order_id > :oldOrderId and order_id <= :newOrderId and school_id = :schoolId")
    void autoOrderForUpdateAsc(Integer oldOrderId, Integer newOrderId,Integer schoolId);

    /**
     * 自动排序（删除）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_helper set order_id = order_id - 1 where order_id > :oldOrderId and school_id = :schoolId")
    void autoOrderForDelete(Integer oldOrderId,Integer schoolId);

}
