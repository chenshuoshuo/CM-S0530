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
@Table(name = "gns_guide", schema = "gns", catalog = "CM-S0530")
public class GnsGuide {
    private Integer guideId;
    private Integer studnetTypeCode;
    private Integer campusCode;
    private String title;
    private String content;
    private Geometry lngLat;
    private Geometry rasterLngLat;
    private Timestamp updateTime;
    private Integer orderId;
    private String memo;

    @Id
    @Column(name = "guide_id", nullable = false)
    public Integer getGuideId() {
        return guideId;
    }

    public void setGuideId(Integer guideId) {
        this.guideId = guideId;
    }

    @Basic
    @Column(name = "studnet_type_code", nullable = true)
    public Integer getStudnetTypeCode() {
        return studnetTypeCode;
    }

    public void setStudnetTypeCode(Integer studnetTypeCode) {
        this.studnetTypeCode = studnetTypeCode;
    }

    @Basic
    @Column(name = "campus_code", nullable = true)
    public Integer getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(Integer campusCode) {
        this.campusCode = campusCode;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "lng_lat", nullable = true)
    public Geometry getLngLat() {
        return lngLat;
    }

    public void setLngLat(Geometry lngLat) {
        this.lngLat = lngLat;
    }

    @Basic
    @Column(name = "raster_lng_lat", nullable = true)
    public Geometry getRasterLngLat() {
        return rasterLngLat;
    }

    public void setRasterLngLat(Geometry rasterLngLat) {
        this.rasterLngLat = rasterLngLat;
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
        GnsGuide gnsGuide = (GnsGuide) o;
        return guideId == gnsGuide.guideId &&
                Objects.equals(studnetTypeCode, gnsGuide.studnetTypeCode) &&
                Objects.equals(campusCode, gnsGuide.campusCode) &&
                Objects.equals(title, gnsGuide.title) &&
                Objects.equals(content, gnsGuide.content) &&
                Objects.equals(lngLat, gnsGuide.lngLat) &&
                Objects.equals(rasterLngLat, gnsGuide.rasterLngLat) &&
                Objects.equals(updateTime, gnsGuide.updateTime) &&
                Objects.equals(orderId, gnsGuide.orderId) &&
                Objects.equals(memo, gnsGuide.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guideId, studnetTypeCode, campusCode, title, content, lngLat, rasterLngLat, updateTime, orderId, memo);
    }
}
