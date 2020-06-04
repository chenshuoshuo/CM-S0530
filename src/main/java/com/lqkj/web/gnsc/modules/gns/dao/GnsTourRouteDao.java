package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsPushMessage;
import com.lqkj.web.gnsc.modules.gns.domain.GnsTourRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author cs
 * @since 2019/5/9
 */
@Repository
public interface GnsTourRouteDao extends JpaRepository<GnsTourRoute, Integer> {

    /**
     * 根据校区获取列表
     */
    @Query(nativeQuery = true,
        value = "select route_id routeId,route_name routeName from gns.gns_tour_route where campus_code = :campusCode")
    List<Map<String,Object>> findAllByCampusCode(Integer campusCode);

    /**
     * 获取指定路线详情
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select point_count,mileage,navigation_route from gns.gns_tour_route where route_id = :route)\n" +
                "select json_build_object('pointCount',point_count,'mileage',mileage,'navigationRoute',navigation_route)\\:\\:varchar from t1")
    String findRouteById(Integer route);

    /**
     * 获取当前最大排序值
     */
    @Query(nativeQuery = true,
            value = "SELECT MAX(sm.order_id) FROM gns.gns_tour_route as sm where sm.campus_code = :campusCode")
    Integer getMaxOrder(Integer campusCode);

    /**
     * 自动排序(新增)
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_tour_route set order_id = order_id + 1 where order_id >= :orderId and campus_code = :campusCode ")
    void autoOrder(Integer orderId,Integer campusCode);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_tour_route set order_id = order_id + 1 where order_id >= :newOrderId and order_id <= :oldOrderId and campus_code = :campusCode ")
    void autoOrderForUpdateDesc(Integer oldOrderId, Integer newOrderId,Integer campusCode);

    /**
     * 自动排序（编辑）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_tour_route set order_id = order_id - 1 where order_id > :oldOrderId and order_id <= :newOrderId  and campus_code = :campusCode ")
    void autoOrderForUpdateAsc(Integer oldOrderId, Integer newOrderId,Integer campusCode);

    /**
     * 自动排序（删除）
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "update gns.gns_tour_route set order_id = order_id - 1 where order_id > :oldOrderId  and campus_code = :campusCode ")
    void autoOrderForDelete(Integer oldOrderId,Integer campusCode);

}
