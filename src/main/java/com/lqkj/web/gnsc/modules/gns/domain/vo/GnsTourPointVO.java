package com.lqkj.web.gnsc.modules.gns.domain.vo;

import com.lqkj.web.gnsc.modules.gns.domain.GnsTourRoute;

import java.util.List;

/**
 * @author cs
 * @Date 2020/6/4 9:37
 * @Version 2.2.2.0
 **/
public class GnsTourPointVO {
    private Long mapCode;
    private String elementType;
    private PointVO point;

    public Long getMapCode() {
        return mapCode;
    }

    public void setMapCode(Long mapCode) {
        this.mapCode = mapCode;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public PointVO getPoint() {
        return point;
    }

    public void setPoint(PointVO point) {
        this.point = point;
    }
}
