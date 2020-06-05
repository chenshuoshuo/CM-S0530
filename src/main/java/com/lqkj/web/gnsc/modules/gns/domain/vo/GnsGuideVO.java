package com.lqkj.web.gnsc.modules.gns.domain.vo;

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
public class GnsGuideVO {
    private Integer guideId;
    private String tyeName;
    private String campusName;
    private String title;
    private String content;
    private Timestamp updateTime;
    private Integer orderId;
    private Integer typeCode;
    private Integer campusCode;
    private String lngLatString;
    private String rasterLngLat;

    @Id
    @Column(name = "guide_id", nullable = false)
    public Integer getGuideId() {
        return guideId;
    }

    public void setGuideId(Integer guideId) {
        this.guideId = guideId;
    }

    @Column(name = "type_name")
    public String getTyeName() {
        return tyeName;
    }

    public void setTyeName(String tyeName) {
        this.tyeName = tyeName;
    }

    @Column(name = "campus_name")
    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    @Column(name = "studnet_type_code")
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "campus_code")
    public Integer getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(Integer campusCode) {
        this.campusCode = campusCode;
    }

    @Basic
    @Column(name = "lng_lat")
    public String getLngLatString() {
        return lngLatString;
    }

    public void setLngLatString(String lngLatString) {
        this.lngLatString = lngLatString;
    }

    @Basic
    @Column(name = "raster_lng_lat")
    public String getRasterLngLat() {
        return rasterLngLat;
    }

    public void setRasterLngLat(String rasterLngLat) {
        this.rasterLngLat = rasterLngLat;
    }
}
