package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsTourPoint;
import com.lqkj.web.gnsc.modules.gns.domain.GnsTourRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cs
 * @since 2019/5/9
 */
@Repository
public interface GnsTourPointDao extends JpaRepository<GnsTourPoint, Integer> {

    /**
     * 根据路线获取点位
     */
    List<GnsTourPoint> findAllByRouteId(Integer RouteId);

}
