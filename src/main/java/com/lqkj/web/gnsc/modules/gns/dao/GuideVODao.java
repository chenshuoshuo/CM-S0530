package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsGuideVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideVODao extends JpaRepository<GnsGuideVO,Integer> {

    @Query(nativeQuery = true,
        value = "select g.guide_id, g.order_id,g.title,g.content,c.campus_name,t.type_name,g.update_time,c.campus_code,t.studnet_type_code,st_astext(g.lng_lat)lng_lat,st_astext(g.raster_lng_lat) raster_lng_lat from gns.gns_guide g left join gns.gns_campus_info c on g.campus_code = c.campus_code " +
                "left join gns.gns_student_type t on g.studnet_type_code = t.studnet_type_code where t.school_id = :schoolId and 1=1 " +
                "and case when :campusCode = '0' then 1=1 else g.campus_code =:campusCode end " +
                "and case when :typeCode = '0' then 1=1 else t.studnet_type_code =:typeCode end " +
                "and case when :title is null then 1=1 else g.title like concat('%', :title ,'%') end order by g.order_id asc")
    Page<GnsGuideVO> page(Integer schoolId,Integer campusCode, Integer typeCode,String title, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select g.guide_id, g.order_id,g.title,g.content,c.campus_name,t.type_name,g.update_time,c.campus_code,t.studnet_type_code,st_asgeojson(g.lng_lat)\\:\\:varchar lng_lat,st_asgeojson(g.raster_lng_lat)\\:\\:varchar raster_lng_lat from gns.gns_guide g left join gns.gns_campus_info c on g.campus_code = c.campus_code " +
                    "left join gns.gns_student_type t on g.studnet_type_code = t.studnet_type_code where t.school_id = :schoolId and t.studnet_type_code = :typeCode")
    List<GnsGuideVO> findAllByTypeCodeAndSchoolId(Integer schoolId, Integer typeCode);

}
