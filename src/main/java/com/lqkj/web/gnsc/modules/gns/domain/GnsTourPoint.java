package com.lqkj.web.gnsc.modules.gns.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:51
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_tour_point", schema = "gns")
public class GnsTourPoint {
    private Integer pointCode;
    private Integer routeId;
    private Long mapCode;
    private String elementType;

    @Id
    @Column(name = "point_code", nullable = false)
    public Integer getPointCode() {
        return pointCode;
    }

    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
    }

    @Basic
    @Column(name = "route_id", nullable = true)
    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "map_code", nullable = true)
    public Long getMapCode() {
        return mapCode;
    }

    public void setMapCode(Long mapCode) {
        this.mapCode = mapCode;
    }

    @Basic
    @Column(name = "element_type", nullable = true, length = 50)
    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsTourPoint that = (GnsTourPoint) o;
        return pointCode == that.pointCode &&
                Objects.equals(routeId, that.routeId) &&
                Objects.equals(mapCode, that.mapCode) &&
                Objects.equals(elementType, that.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointCode, routeId, mapCode, elementType);
    }
}
