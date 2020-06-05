package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsGuideVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsReceptionPlaceVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceptionPlaceVODao extends JpaRepository<GnsReceptionPlaceVO,Integer> {

    @Query(nativeQuery = true,
        value = "select g.place_id,g.title,g.content,c.campus_name,c.campus_code, t.type_name,t.type_code, g.update_time from gns.gns_reception_place g left join gns.gns_campus_info c on g.campus_code = c.campus_code " +
                "left join gns.gns_reception_type t on g.type_code = t.type_code where t.school_id = :schoolId and 1=1 " +
                "and case when :campusCode = '0' then 1=1 else g.campus_code =:campusCode end " +
                "and case when :typeCode = '0' then 1=1 else t.type_code =:typeCode end " +
                "and case when :title is null then 1=1 else g.title like concat('%', :title ,'%') end order by g.order_id asc")
    Page<GnsReceptionPlaceVO> page(Integer schoolId,Integer campusCode, Integer typeCode, String title, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select g.place_id,g.title,g.content,c.campus_name,c.campus_code, t.type_name,t.type_code, g.update_time from gns.gns_reception_place g left join gns.gns_campus_info c on g.campus_code = c.campus_code " +
                    "left join gns.gns_reception_type t on g.type_code = t.type_code where t.school_id = :schoolId order by g.order_id asc")
    List<GnsReceptionPlaceVO> findAllBySchoolId(Integer schoolId);

}
