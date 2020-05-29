package com.lqkj.web.gnsc.modules.gns.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:51
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_tour_route", schema = "gns", catalog = "CM-S0530")
public class GnsTourRoute {
    private Integer campusCode;
    private Integer routeId;
    private String routeName;
    private Integer pointCount;
    private String mileage;
    private String updateTime;
    private Integer orderId;
    private String memo;

    @Basic
    @Column(name = "campus_code", nullable = true)
    public Integer getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(Integer campusCode) {
        this.campusCode = campusCode;
    }

    @Id
    @Column(name = "route_id", nullable = false)
    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "route_name", nullable = true, length = 50)
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    @Basic
    @Column(name = "point_count", nullable = true)
    public Integer getPointCount() {
        return pointCount;
    }

    public void setPointCount(Integer pointCount) {
        this.pointCount = pointCount;
    }

    @Basic
    @Column(name = "mileage", nullable = true, length = 20)
    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    @Basic
    @Column(name = "update_time", nullable = true, length = -1)
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "order_id", nullable = true)
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "memo", nullable = true, length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsTourRoute that = (GnsTourRoute) o;
        return routeId == that.routeId &&
                Objects.equals(campusCode, that.campusCode) &&
                Objects.equals(routeName, that.routeName) &&
                Objects.equals(pointCount, that.pointCount) &&
                Objects.equals(mileage, that.mileage) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campusCode, routeId, routeName, pointCount, mileage, updateTime, orderId, memo);
    }
}
