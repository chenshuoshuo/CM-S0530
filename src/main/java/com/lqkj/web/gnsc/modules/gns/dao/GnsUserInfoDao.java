package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsStore;
import com.lqkj.web.gnsc.modules.gns.domain.GnsUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public interface GnsUserInfoDao extends JpaRepository<GnsUserInfo, UUID> {
    /**
     * 根据学校ID和openId获取用户
     */
    GnsUserInfo findBySchoolIdAndOpenid(Integer schoolId,String openId);

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
            value = "with t1 as(select gu.user_id ,gu.head_url ,gu.nickname,gu.mobile,ga.academy_code ,ga.academy_name ,st_asgeojson(ga.location)\\:\\:json academygeom,gu.dorm_id ," +
                    "mb.building_name , st_asgeojson(mb.lng_lat)\\:\\:json dormgeom from gns.gns_user_info gu,gns.gns_academy ga,portal.map_building mb " +
                    "where gu.dorm_id = mb.map_code\\:\\:varchar and gu.academy_code = ga.academy_code\\:\\:varchar and  gu.user_id = :userCode)\n" +
                    "select jsonb_build_object('userId',user_id,'headUrl',head_url,'nickName',nickname,'mobile',mobile,'academyCode',academy_code,'academyName',academy_name,'academyGeom',academygeom,'dormId',dorm_id,'dormName',building_name,'dormGeom',dormgeom)\\:\\:varchar from t1 ")
    String findByUserId(UUID userCode);

}
