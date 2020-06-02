package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsClubVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsHelperVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubVODao extends JpaRepository<GnsClubVO,Integer> {

    @Query(nativeQuery = true,
        value = "select g.club_id,club_name,c.campus_code,g.update_time,g.description from gns.gns_club g left join gns.gns_campus_info c on g.campus_code = c.campus_code where 1=1 " +
                "and case when :campusCode = '0' then 1=1 else c.campus_code =:campusCode end " +
                "and case when :clubName is null then 1=1 else g.club_name like concat('%', :clubName ,'%') end order by g.order_id asc")
    Page<GnsClubVO> page(Integer campusCode, String clubName, Pageable pageable);

}
