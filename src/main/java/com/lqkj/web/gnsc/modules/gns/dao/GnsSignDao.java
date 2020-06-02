package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cs
 * @Date 2020/6/1 19:52
 * @Version 2.2.2.0
 **/
@Repository
public interface GnsSignDao extends JpaRepository<GnsSign,Integer> {

    /**
     * 获取弹幕列表
     */
    @Query(nativeQuery = true,
        value = "select u.nickname || to_char(s.create_time, 'yyyy-MM-dd hh24:mi:ss') || '在' || s.landmark_name || '打卡' bless " +
                "from gns.gns_sign s left join gns.gns_user_info u on s.user_id = u.user_id where u.school_id = :schoolId order by s.create_time DESC limit 10")
    List<Map<String,Object>> queryList(Integer schoolId);

}
