package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cs
 * @since 2019/5/9
 */
@Repository
public interface DormNavigationDao extends JpaRepository<GnsClub,Integer> {

    /**
     * 根据校区获取宿舍楼列表
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select mb.map_code ,mb.building_name ,st_asgeojson(mb.lng_lat)\\:\\:json lng_lat from portal.map_building mb where mb.campus_code = :vectorZoomCode)\n" +
                "select array_to_json(array_agg(json_build_object('mapCode',t1.map_code,'buildingName',t1.building_name,'vectorGeom',t1.lng_lat)))\\:\\:varchar from t1")
    String queryList(Integer vectorZoomCode);

    /**
     * 根据学校获取宿舍列表
     */
    @Query(nativeQuery = true,
            value = "with t1 as(select mb.map_code,mb.building_name from gns.gns_school gs,gns.gns_campus_info gc,portal.map_building mb where gs.school_id = gc.school_id and mb.campus_code = gc.vector_zoom_code and mb.type_code = 12 and gs.school_id = :schoolId)\n" +
                    "SELECT array_to_json(array_agg(jsonb_build_object('dormCode',map_code,'dormName',building_name)))\\:\\:varchar from t1")
    String queryListWithSchoolId(Integer schoolId);

}
