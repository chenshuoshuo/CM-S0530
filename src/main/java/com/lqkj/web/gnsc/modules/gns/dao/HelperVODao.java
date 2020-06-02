package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsGuideVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsHelperVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HelperVODao extends JpaRepository<GnsHelperVO,Integer> {

    @Query(nativeQuery = true,
        value = "select g.helper_id,g.title,g.contact,c.type_name,g.update_time from gns.gns_helper g left join gns.gns_helper_type c on g.type_code = c.type_code where 1=1 " +
                "and case when :typeCode = '0' then 1=1 else c.type_code =:typeCode end " +
                "and case when :title is null then 1=1 else g.title like concat('%', :title ,'%') end order by g.order_id asc")
    Page<GnsHelperVO> page(Integer typeCode, String title, Pageable pageable);

}
