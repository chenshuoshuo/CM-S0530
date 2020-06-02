package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsDisplayPointType;
import com.lqkj.web.gnsc.modules.gns.domain.GnsDisplayPointTypePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PointTypeDao extends JpaRepository<GnsDisplayPointType, GnsDisplayPointTypePK> {

    /**
     * 获取指定学校地标分类
     * @param schoolId
     * @return
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select p.point_type_code ,t.type_name from gns.gns_display_point_type p left join portal.map_point_type t on p.point_type_code = t.type_code where p.school_id =:schoolId)\n" +
                " select p.type_code typeCode,p.type_name typeName from portal.map_point_type p,t1 where p.parent_code = t1.point_type_code")
    List<Map<String,Object>> queryList(Integer schoolId);

    /**
     * 根据分类和学校获取地标列表
     */
    @Query(nativeQuery = true,
        value = "with t1 as(select p.point_code,p.point_name,st_asgeojson(lng_lat)\\:\\:json lng_lat,st_asgeojson(raster_lng_lat)\\:\\:json raster_lng_lat,p.brief,p.audio_url " +
                "from portal.map_point p left join gns.gns_campus_info c on p.campus_code = c.vector_zoom_code where p.campus_code = :campusCode and p.type_code = :typeCode order by p.order_id asc)\n" +
                "select array_to_json(array_agg(json_build_object('pointCode',t1.point_code,'pointName',t1.point_name,'vectorGeom',t1.lng_lat,'rasterGeom',t1.raster_lng_lat,'brief',t1.brief,'audioUrl',t1.audio_url)))\\:\\:varchar from t1")
    String queryListWithType(Integer campusCode,Integer typeCode);

    /**
     * 获取热门地标
     */
    @Query(nativeQuery = true,
            value = "with t1 as(select p.point_code,p.point_name,st_asgeojson(lng_lat)\\:\\:json lng_lat,st_asgeojson(raster_lng_lat)\\:\\:json raster_lng_lat,p.brief,p.audio_url " +
                    "from portal.map_point p left join gns.gns_campus_info c on p.campus_code = c.vector_zoom_code where p.campus_code = :campusCode and p.gns_hot = true order by p.thumbs_up_count desc limit 10)\n" +
                    "select array_to_json(array_agg(json_build_object('pointCode',t1.point_code,'pointName',t1.point_name,'vectorGeom',t1.lng_lat,'rasterGeom',t1.raster_lng_lat,'brief',t1.brief,'audioUrl',t1.audio_url)))\\:\\:varchar from t1")
    String queryHotListWithType(Integer campusCode);


}
