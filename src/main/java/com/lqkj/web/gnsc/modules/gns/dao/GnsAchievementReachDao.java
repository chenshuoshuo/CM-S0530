package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsAccessRecord;
import com.lqkj.web.gnsc.modules.gns.domain.GnsAchievementReach;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
