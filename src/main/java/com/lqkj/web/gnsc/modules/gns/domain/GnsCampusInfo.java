package com.lqkj.web.gnsc.modules.gns.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_campus_info", schema = "gns")
public class GnsCampusInfo {
    private Integer campusCode;
    private Integer schoolId;
    private String campusName;
    private Integer rasterZoomCode;
    private Integer vectorZoomCode;
    private String gateLngLat;
    private String campusDescription;
    private String roamUrl;
    private Integer orderId;
    private String memo;
    private String schoolName;

    @Id
    @Column(name = "campus_code", nullable = false)
    public Integer getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(Integer campusCode) {
        this.campusCode = campusCode;
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
    @Column(name = "campus_name", nullable = true, length = 255)
    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    @Basic
    @Column(name = "raster_zoom_code", nullable = true)
    public Integer getRasterZoomCode() {
        return rasterZoomCode;
    }

    public void setRasterZoomCode(Integer rasterZoomCode) {
        this.rasterZoomCode = rasterZoomCode;
    }

    @Basic
    @Column(name = "vector_zoom_code", nullable = true)
    public Integer getVectorZoomCode() {
        return vectorZoomCode;
    }

    public void setVectorZoomCode(Integer vectorZoomCode) {
        this.vectorZoomCode = vectorZoomCode;
    }

    @Basic
    @Column(name = "gate_lng_lat", nullable = true, length = 100)
    public String getGateLngLat() {
        return gateLngLat;
    }

    public void setGateLngLat(String gateLngLat) {
        this.gateLngLat = gateLngLat;
    }

    @Basic
    @Column(name = "campus_description", nullable = true, length = 255)
    public String getCampusDescription() {
        return campusDescription;
    }

    public void setCampusDescription(String campusDescription) {
        this.campusDescription = campusDescription;
    }

    @Basic
    @Column(name = "roam_url", nullable = true, length = 1024)
    public String getRoamUrl() {
        return roamUrl;
    }

    public void setRoamUrl(String roamUrl) {
        this.roamUrl = roamUrl;
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

    @Transient
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsCampusInfo that = (GnsCampusInfo) o;
        return campusCode == that.campusCode &&
                Objects.equals(schoolId, that.schoolId) &&
                Objects.equals(campusName, that.campusName) &&
                Objects.equals(rasterZoomCode, that.rasterZoomCode) &&
                Objects.equals(vectorZoomCode, that.vectorZoomCode) &&
                Objects.equals(gateLngLat, that.gateLngLat) &&
                Objects.equals(campusDescription, that.campusDescription) &&
                Objects.equals(roamUrl, that.roamUrl) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campusCode, schoolId, campusName, rasterZoomCode, vectorZoomCode, gateLngLat, campusDescription, roamUrl, orderId, memo);
    }
}
