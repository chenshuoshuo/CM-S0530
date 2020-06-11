package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsInteractionStatistic;
import com.lqkj.web.gnsc.modules.gns.domain.GnsMapUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author cs
 * @Date 2020/6/1 19:52
 * @Version 2.2.2.0
 **/
@Repository
public interface MapUseDao extends JpaRepository<GnsMapUse, String> {

    /**
     * 获取生活服务点位点击排行
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select row_number() over() order_id,count(*) click_count,m.map_element_name from gns.gns_map_use m where m.campus_code = :campusCode and m.record_type = 5 " +
                "group by m.map_element_name order by click_count desc)\n" +
                "select array_to_json(array_agg(jsonb_build_object('orderId',t1.order_id,'infoName',t1.map_element_name,'infoCount',click_count)))\\:\\:varchar from t1")
    String pointCount(Integer campusCode);

    /**
     * 获取热门导航位置排行
     */
    @Query(nativeQuery = true,
            value = "with t1 as(select row_number() over() order_id,count(*) click_count,m.map_element_name from gns.gns_map_use m where m.campus_code = :campusCode and m.record_type =4 " +
                    "group by m.map_element_name order by click_count desc limit 10)\n" +
                    "select array_to_json(array_agg(jsonb_build_object('orderId',t1.order_id,'infoName',t1.map_element_name,'infoCount',click_count)))\\:\\:varchar from t1")
    String navigationStatistic(Integer campusCode);

    /**
     * 获取热门地标点击排行
     */
    @Query(nativeQuery = true,
            value = "with t1 as(select row_number() over() order_id,count(*) click_count,m.map_element_name from gns.gns_map_use m where m.campus_code = :campusCode and m.record_type = 1 " +
                    "group by m.map_element_name order by click_count desc limit 10)\n" +
                    "select array_to_json(array_agg(jsonb_build_object('orderId',t1.order_id,'infoName',t1.map_element_name,'infoCount',click_count)))\\:\\:varchar from t1")
    String hotPointStatistic(Integer campusCode);

}
