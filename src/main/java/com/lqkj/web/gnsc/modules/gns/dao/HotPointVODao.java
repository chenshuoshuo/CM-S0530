package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsHelperVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.HotPointVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotPointVODao extends JpaRepository<HotPointVO,Integer> {

    @Query(nativeQuery = true,
        value = "with t1 as(select p.point_type_code ,t.type_name,t.vector_icon from gns.gns_display_point_type p " +
                "left join portal.map_point_type t on p.point_type_code = t.type_code where p.school_id =:schoolId)" +
                "select p.point_code,p.point_name,'标注' type_name,c.campus_name,p.thumbs_up_count,p.gns_hot,st_astext(lng_lat)lng_lat,st_astext(raster_lng_lat) raster_lng_lat " +
                "from t1, portal.map_point p left join gns.gns_campus_info c on p.campus_code = c.vector_zoom_code where " +
                "p.type_code in (select type_code from portal.map_point_type where parent_code = t1.point_type_code) " +
                "and case when :campusCode = '0' then 1=1 else c.campus_code = :campusCode end " +
                "and case when :pointName is null then 1=1 else p.point_name like concat('%', :pointName ,'%') end order by p.thumbs_up_count desc",
            countQuery =  "select count (a.*) from (with t1 as(select p.point_type_code ,t.type_name,t.vector_icon from gns.gns_display_point_type p " +
                    "left join portal.map_point_type t on p.point_type_code = t.type_code where p.school_id =:schoolId)" +
                    "select p.point_code,p.point_name,'标注' type_name,c.campus_name,p.thumbs_up_count,p.gns_hot,st_astext(lng_lat)lng_lat,st_astext(raster_lng_lat) raster_lng_lat " +
                    "from t1, portal.map_point p left join gns.gns_campus_info c on p.campus_code = c.vector_zoom_code where " +
                    "p.type_code in (select type_code from portal.map_point_type where parent_code = t1.point_type_code) " +
                    "and case when :campusCode = '0' then 1=1 else c.campus_code = :campusCode end " +
                    "and case when :pointName is null then 1=1 else p.point_name like concat('%', :pointName ,'%') end order by p.thumbs_up_count desc) a")
    Page<HotPointVO> page(Integer schoolId,Integer campusCode, String pointName, Pageable pageable);

}
