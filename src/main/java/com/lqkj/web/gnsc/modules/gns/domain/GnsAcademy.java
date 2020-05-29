package com.lqkj.web.gnsc.modules.gns.domain;

import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_academy", schema = "gns", catalog = "CM-S0530")
public class GnsAcademy {
    private Integer academyCode;
    private Integer schoolId;
    private String academyName;
    private Geometry location;
    private Timestamp updateTime;
    private Integer orderId;
    private String memo;

    @Id
    @Column(name = "academy_code", nullable = false)
    public Integer getAcademyCode() {
        return academyCode;
    }

    public void setAcademyCode(Integer academyCode) {
        this.academyCode = academyCode;
    }

    @Basic
    @Column(name = "school_id", nullable = true)
    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Basic
    @Column(name = "academy_name", nullable = true, length = 255)
    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    @Basic
    @Column(name = "location", nullable = true)
    public Geometry getLocation() {
        return location;
    }

    public void setLocation(Geometry location) {
        this.location = location;
    }

    @Basic
    @Column(name = "update_time", nullable = true, length = -1)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
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
        GnsAcademy that = (GnsAcademy) o;
        return academyCode == that.academyCode &&
                Objects.equals(schoolId, that.schoolId) &&
                Objects.equals(academyName, that.academyName) &&
                Objects.equals(location, that.location) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(academyCode, schoolId, academyName, location, updateTime, orderId, memo);
    }
}
