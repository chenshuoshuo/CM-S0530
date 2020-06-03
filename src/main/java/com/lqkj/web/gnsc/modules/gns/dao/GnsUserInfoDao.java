package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GnsUserInfoDao extends JpaRepository<GnsUserInfo, String> {
    /**
     * 根据学校ID和openId获取用户
     */
    GnsUserInfo findBySchoolIdAndOpenid(Integer schoolId, String openId);

    @Query(nativeQuery = true, value = "select * from gns.gns_user_info where user_id||'' = :userId")
    GnsUserInfo findByUUID(String userId);

    @Query(nativeQuery = true, value = "select a.achievement_name,a.achieved_icon,a.not_achieved_icon," +
            " a.brief,a.condition,ar.reach_time from gns.gns_achievement a " +
            " inner join gns.gns_achievement_reach ar on a.achievement_id = ar.achievement_id " +
            " where ar.user_id||'' = :userId order by case when ar.reach_time is null then 1 else 0 end asc, " +
            " ar.reach_time desc")
    List<Object[]> findAchieveByUserId(String userId);

    /**
     * 获取用户位置并返回信息
     */
//    @Query(nativeQuery = true,
//        value = "")
//    String loadUserLocation();

}
