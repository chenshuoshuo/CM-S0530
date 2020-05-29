package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsGuideVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptionPlaceVODao extends JpaRepository<GnsGuideVO,Integer> {

    @Query(nativeQuery = true,
        value = "select g.place_id,g.title,g.content,c.campus_name,t.type_name,g.update_time from gns.gns_reception_place g left join gns.gns_campus_info c on g.campus_code = c.campus_code " +
                "left join gns.gns_reception_type t on g.type_code = t.type_code where 1=1 " +
                "and case when :campusCode = '0' then 1=1 else g.campus_code =:campusCode end " +
                "and case when :typeCode = '0' then 1=1 else t.type_code =:typeCode end " +
                "and case when :title is null then 1=1 else g.title like concat('%', :title ,'%') end order by g.order_id asc")
    Page<GnsGuideVO> page(Integer campusCode, Integer typeCode, String title, Pageable pageable);

}
