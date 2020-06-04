package com.lqkj.web.gnsc.modules.gns.domain.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author cs
 * @Date 2020/6/4 14:47
 * @Version 2.2.2.0
 **/
@Entity
public class HotPointVO {

    private Integer pointCode;
    private String pointName;
    private String campusName;
    private String typeName;
    private Integer thumpUpCount;
    private Boolean gnsHot;
    private String lngLat;
    private String rasterLngLat;

    @Id
    @Column(name = "point_code")
    public Integer getPointCode() {
        return pointCode;
    }

    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
    }

    @Column(name = "point_name")
    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    @Column(name = "campus_name")
    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Column(name = "thumbs_up_count")
    public Integer getThumpUpCount() {
        return thumpUpCount;
    }

    public void setThumpUpCount(Integer thumpUpCount) {
        this.thumpUpCount = thumpUpCount;
    }

    @Column(name = "gns_hot")
    public Boolean getGnsHot() {
        return gnsHot;
    }

    public void setGnsHot(Boolean gnsHot) {
        this.gnsHot = gnsHot;
    }

    @Column(name = "lng_lat")
    public String getLngLat() {
        return lngLat;
    }

    public void setLngLat(String lngLat) {
        this.lngLat = lngLat;
    }

    @Column(name = "raster_lng_lat")
    public String getRasterLngLat() {
        return rasterLngLat;
    }

    public void setRasterLngLat(String rasterLngLat) {
        this.rasterLngLat = rasterLngLat;
    }
}
