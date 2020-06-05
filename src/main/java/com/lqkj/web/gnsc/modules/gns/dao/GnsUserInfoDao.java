package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Map;
import java.util.UUID;

@Repository
public interface GnsUserInfoDao extends JpaRepository<GnsUserInfo, UUID> {
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

    @Query(nativeQuery = true, value = "select rank() over (order by u.sign_count desc) as rank," +
            " u.user_id||'',u.head_url, u.nickname, u.sign_count" +
            " from gns.gns_user_info u" +
            " where u.school_id = :schoolId and (:academyCode = '' or u.academy_code = :academyCode)")
    List<Object[]> getRankOfUserSign(Integer schoolId, String academyCode);

    /**
     * 获取用户位置并返回信息
     */
    @Query(nativeQuery = true,
        value = "select gns.load_user_location(?1,?2,?3)")
    String loadUserLocation(String userCode,Double lng,Double lat);

    /**
     * 根据用户ID获取
     */
    @Query(nativeQuery = true,
            value = "with t1 as(select gu.user_id ,gu.head_url ,gu.nickname,gu.real_name,gu.mobile,ga.academy_code ,ga.academy_name ,st_asgeojson(ga.location)\\:\\:json academygeom,gu.dorm_id ," +
                    "mb.building_name , st_asgeojson(mb.lng_lat)\\:\\:json dormgeom from gns.gns_user_info gu left join gns.gns_academy ga on gu.academy_code = ga.academy_code\\:\\:varchar left join portal.map_building mb on gu.dorm_id = mb.map_code\\:\\:varchar where  gu.user_id = :userCode)\n" +
                    "select jsonb_build_object('userId',user_id,'headUrl',head_url,'nickName',nickname,'realName',real_name,'mobile',mobile,'academyCode',academy_code,'academyName',academy_name,'academyGeom',academygeom,'dormId',dorm_id,'dormName',building_name,'dormGeom',dormgeom)\\:\\:varchar from t1 ")
    String findByUserId(String userCode);

    /**
     * 获取信息完善度
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select case when real_name is null then 0 else 1 end real_name,case when mobile is null then 0 else 1  end mobile,case when dorm_id is null then 0 else 1  end dorm_id,case when academy_code is null then 0 else 1  end academy_code from gns.gns_user_info where user_id = :userId),\n" +
                "t2 as(select unnest(array[real_name\\:\\:integer ,mobile\\:\\:integer,dorm_id\\:\\:integer,academy_code\\:\\:integer]) as f1 from t1)\n" +
                "select sum(t2.f1)\\:\\:integer from t2")
    Integer userInfoPerfected(String userId);

}
