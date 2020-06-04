package com.lqkj.web.gnsc.modules.gns.domain.vo;

import com.lqkj.web.gnsc.modules.gns.domain.GnsTourRoute;

import java.util.List;

/**
 * @author cs
 * @Date 2020/6/4 12:32
 * @Version 2.2.2.0
 **/
public class GnsTourRouteForm {
    private GnsTourRoute tourRoute;
    private List<GnsTourPointVO> tourPointList;

    public GnsTourRoute getTourRoute() {
        return tourRoute;
    }

    public void setTourRoute(GnsTourRoute tourRoute) {
        this.tourRoute = tourRoute;
    }

    public List<GnsTourPointVO> getTourPointList() {
        return tourPointList;
    }

    public void setTourPointList(List<GnsTourPointVO> tourPointList) {
        this.tourPointList = tourPointList;
    }
}
