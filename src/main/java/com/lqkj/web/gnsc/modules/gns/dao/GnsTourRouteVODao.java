package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsGuideVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsTourRouteVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GnsTourRouteVODao extends JpaRepository<GnsTourRouteVO,Integer> {

    @Query(nativeQuery = true,
        value = "select g.route_id, g.route_name,g.point_count,g.mileage,c.campus_name,g.order_id,g.update_time from gns.gns_tour_route g left join gns.gns_campus_info c on g.campus_code = c.campus_code where 1=1 " +
                "and case when :campusCode = '0' then 1=1 else g.campus_code =:campusCode end " +
                "and case when :routeName is null then 1=1 else g.route_name like concat('%', :routeName ,'%') end order by g.order_id asc")
    Page<GnsTourRouteVO> page(Integer campusCode,String routeName, Pageable pageable);

}
