package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsAccessRecord;
import com.lqkj.web.gnsc.modules.gns.domain.GnsUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GnsAccessRecordDao extends JpaRepository<GnsAccessRecord, String> {

    /**
     * 迎新使用统计（按日期统计）
     */
    @Query(nativeQuery = true,
            value = "with t1 as(select count(*)info_count,a.create_date info_name from gns.gns_access_record a,gns.gns_user_info u " +
                    "where a.user_id=u.user_id and u.school_id = :schoolId and a.create_date between :start and :end group by a.create_date order by a.create_date)\n" +
                    "select array_to_json(array_agg(jsonb_build_object('infoName',t1.info_name,'infoCount',info_count)))\\:\\:varchar from t1")
    String useStatisticByDay(Integer schoolId,String start,String end);

    /**
     * 根据IP统计使用人数
     */
    @Query(nativeQuery = true,
        value = "select a.ip, count(*) count from gns.gns_access_record a,gns.gns_user_info u where a.user_id=u.user_id and u.school_id = :schoolId group by a.ip order by count desc")
    List<Map<String,Object>> countByIp(Integer schoolId);

}
