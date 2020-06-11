package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsFeedback;
import com.lqkj.web.gnsc.modules.gns.domain.GnsInteractionStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author cs
 * @Date 2020/6/1 19:52
 * @Version 2.2.2.0
 **/
@Repository
public interface InteractionStatisticDao extends JpaRepository<GnsInteractionStatistic, Integer> {

    /**
     * 根据学校ID和名称获取
     */
    GnsInteractionStatistic findBySchoolIdAndStatisticName(Integer schoolId,String name);

    /**
     * h获取使用次数/人数/分享次数/留影次数
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select count(*) use_people_count ,sum(share_times) share_count from gns.gns_user_info where school_id = :schoolId),\n" +
                "t2 as(select count(*) use_count from gns.gns_access_record ga,gns.gns_user_info gu where ga.user_id = gu.user_id and gu.school_id = :schoolId),\n" +
                "t3 as(select count(*) sign_count from gns.gns_sign gs,gns.gns_user_info gu where gs.user_id = gu.user_id and gu.school_id = :schoolId),\n" +
                "t4 as(select count(*) photo_count from gns.gns_group_photo gs,gns.gns_user_info gu where gs.user_id = gu.user_id and gu.school_id = :schoolId)\n" +
                "select jsonb_build_object('usePeopleCount',t1.use_people_count,'shareCount',t1.share_count,'useCount',t2.use_count,'signCount',t3.sign_count,'photoCount',t4.photo_count)\\:\\:varchar from t1,t2,t3,t4\n")
    String integreateStatistic(Integer schoolId);

    /**
     * 获取功能使用排行
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select row_number() over(order by statistic_data desc)order_id ,statistic_name ,statistic_data from gns.gns_interaction_statistic where school_id = :schoolId)\n" +
                "select array_to_json(array_agg(jsonb_build_object('orderId',t1.order_id,'infoName',t1.statistic_name,'infoCount',statistic_data)))\\:\\:varchar from t1")
    String intergrateUse(Integer schoolId);


}
