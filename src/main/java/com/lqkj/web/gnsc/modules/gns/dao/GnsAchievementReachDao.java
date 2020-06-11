package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsAccessRecord;
import com.lqkj.web.gnsc.modules.gns.domain.GnsAchievementReach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GnsAchievementReachDao extends JpaRepository<GnsAchievementReach, Integer> {

    /**
     * 根据学校和成就id统计获得人数
     */
    Integer countAllByAchievementIdAndSchoolId(Integer id,Integer schoolId);

    /**
     * 获取当前用户成就数量
     */
    Integer countAllByUserId(String userId);

    /**
     * 成就量统计
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select count(r.achievement_id) reach_count,a.achievement_name from gns.gns_achievement_reach r " +
                "left join gns.gns_achievement a on r.achievement_id = a.achievement_id where r.school_id = :schoolId group by a.achievement_name order by reach_count desc ),\n" +
                "t2 as(select row_number() over() order_id,t1.* from t1)\n" +
                "select array_to_json(array_agg(jsonb_build_object('orderId',t2.order_id,'infoName',t2.achievement_name,'infoCount',t2.reach_count)))\\:\\:varchar from t2")
    String achievementReachStatistic(Integer schoolId);

}
