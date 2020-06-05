package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsSign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cs
 * @Date 2020/6/1 19:52
 * @Version 2.2.2.0
 **/
@Repository
public interface GnsSignDao extends JpaRepository<GnsSign, Integer> {

    /**
     * 获取弹幕列表
     */
    @Query(nativeQuery = true,
            value = "select u.nickname || to_char(s.create_time, 'yyyy-MM-dd hh24:mi:ss') || '在' || s.landmark_name || '打卡' bless " +
                    "from gns.gns_sign s left join gns.gns_user_info u on s.user_id = u.user_id where u.school_id = :schoolId order by s.create_time DESC limit 10")
    List<Map<String, Object>> queryList(Integer schoolId);

    /**
     * 判断是否已打卡
     */
    @Query(nativeQuery = true,
            value = "select case when (now() - t1.max_time) > interval '1 hour' then true else false end from (select max(gs.create_time) max_time from gns.gns_sign gs where gs.user_id = :userCode and gs.landmark_id = :mapCode) t1")
    Boolean existsByUserCodeAndMapCode(String userCode, Integer mapCode);


    /**
     * 打卡地标排行榜
     */
    @Query(nativeQuery = true,
            value = "select p.point_name as name, p.gns_sign_count as count\n" +
                    "from portal.map_point p\n" +
                    "where campus_code = :campusCode\n" +
                    "  and p.point_name is not null\n" +
                    "  and p.point_name != ''\n" +
                    "union\n" +
                    "select b.building_name as name, b.gns_sign_count as count\n" +
                    "from portal.map_building b\n" +
                    "where campus_code = :campusCode\n" +
                    "  and b.building_name is not null\n" +
                    "  and b.building_name != ''\n" +
                    "union\n" +
                    "select r.room_name as name, r.gns_sign_count as count\n" +
                    "from portal.map_room r\n" +
                    "where campus_code = :campusCode\n" +
                    "  and r.room_name is not null\n" +
                    "  and r.room_name != ''\n" +
                    "union\n" +
                    "select o.polygon_name as name, o.gns_sign_count as count\n" +
                    "from portal.map_others_polygon o\n" +
                    "where campus_code = :campusCode\n" +
                    "  and o.polygon_name is not null\n" +
                    "  and o.polygon_name != ''\n" +
                    "order by count desc limit 10")
    List<Object[]> getSignRanking(Integer campusCode);

    @Query(nativeQuery = true, value = "select * from gns.gns_sign s where s.user_id||'' = :userId" +
            " order by s.create_time desc")
    Page<GnsSign> getUserSigns(@Param("userId") String userId, Pageable pageable);
}
