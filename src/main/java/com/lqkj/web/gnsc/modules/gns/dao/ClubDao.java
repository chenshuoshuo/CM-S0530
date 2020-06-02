package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsClub;
import org.omg.CORBA.INTERNAL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cs
 * @since 2019/5/9
 */
@Repository
public interface ClubDao extends JpaRepository<GnsClub,Integer> {

    @Query(nativeQuery = true,
            value = "select club_id clubId,club_name clubName,club_logo clubLogo from gns.gns_club where campus_code = :campusCode order by order_id asc ")
    List<Map<String,Object>> queryList(Integer campusCode);

    GnsClub findAllByClubNameAndCampusCode(String clubName,Integer campusCode);

    /**
     * 获取当前最大排序值
     */
    @Query(nativeQuery = true,
            value = "SELECT MAX(sm.order_id) FROM gns.gns_club as sm")
    Integer getMaxOrder();

    /**
     * 自动排序(新增)
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_club set order_id = order_id + 1 where order_id >= :orderId ")
    void autoOrder(Integer orderId);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_club set order_id = order_id + 1 where order_id >= :newOrderId and order_id <= :oldOrderId")
    void autoOrderForUpdateDesc(Integer oldOrderId, Integer newOrderId);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_club set order_id = order_id - 1 where order_id > :oldOrderId and order_id <= :newOrderId ")
    void autoOrderForUpdateAsc(Integer oldOrderId, Integer newOrderId);

    /**
     * 自动排序（删除）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_club set order_id = order_id - 1 where order_id > :oldOrderId ")
    void autoOrderForDelete(Integer oldOrderId);

}
