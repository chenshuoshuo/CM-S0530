package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsApplicationUse;
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
public interface ApplicationUseDao extends JpaRepository<GnsApplicationUse, String> {
    /**
     * 应用使用统计
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select application_name,application_id from gns.gns_application where school_id = :schoolId),\n" +
                "t2 as(select row_number() over() order_id, t1.application_name ,count(*) use_count,count(distinct au.user_id) use_people_count from gns.gns_application_use au,gns.gns_user_info gu,t1 where au.user_id = gu.user_id and au.application_id = t1.application_id group by t1.application_name order by use_count desc)\n" +
                "select array_to_json(array_agg(jsonb_build_object('orderId',t2.order_id,'applicationName',t2.application_name,'useCount',t2.use_count,'usePeopleCount',t2.use_people_count)))\\:\\:varchar from t2\n")
    String applicationUse(Integer schoolId);

}
