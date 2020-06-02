package com.lqkj.web.gnsc.modules.portal.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lqkj.web.gnsc.utils.JacksonGeometryDeserializer;
import com.lqkj.web.gnsc.utils.JacksonGeometrySerializer;
import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:48
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_point", schema = "portal")
public class MapPoint {
    private Integer pointCode;
    private Integer typeCode;
    private String pointName;
    private Integer campusCode;
    private Integer leaf;
    private String location;
    @JsonSerialize(using = JacksonGeometrySerializer.class)
    @JsonDeserialize(using = JacksonGeometryDeserializer.class)
    private Geometry lngLat;
    @JsonSerialize(using = JacksonGeometrySerializer.class)
    @JsonDeserialize(using = JacksonGeometryDeserializer.class)
    private Geometry rasterLngLat;
    private Integer orderId;
    private String brief;
    private Long mapCode;
    private Integer versionCode;
    private Boolean synStatus;
    private Boolean delete;
    private String memo;
    private String audioUrl;
    private String videoUrl;
    private String roamUrl;
    private String photoBackground;
    private Boolean openGnsSign;
    private Integer gnsSignInterval;
    private Integer gnsSignCount;
    private Integer pohotoTakenCount;
    private Boolean gnsHot;
    private Integer thumbsUpCount;
    private MapPointType mapPointType;

    @Id
    @Column(name = "point_code", nullable = false)
    public Integer getPointCode() {
        return pointCode;
    }

    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
    }

    @Basic
    @Column(name = "type_code", nullable = true)
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "point_name", nullable = true, length = 255)
    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
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
    @Column(name = "leaf", nullable = true)
    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    @Basic
    @Column(name = "location", nullable = true, length = 64)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
    @Column(name = "order_id", nullable = true)
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "brief", nullable = true, length = -1)
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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
    @Column(name = "version_code", nullable = true)
    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    @Basic
    @Column(name = "syn_status", nullable = true)
    public Boolean getSynStatus() {
        return synStatus;
    }

    public void setSynStatus(Boolean synStatus) {
        this.synStatus = synStatus;
    }

    @Basic
    @Column(name = "delete", nullable = true)
    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    @Basic
    @Column(name = "memo", nullable = true, length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Basic
    @Column(name = "audio_url", nullable = true, length = 1024)
    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Basic
    @Column(name = "video_url", nullable = true, length = 1024)
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
    @Column(name = "photo_background", nullable = true, length = 1024)
    public String getPhotoBackground() {
        return photoBackground;
    }

    public void setPhotoBackground(String photoBackground) {
        this.photoBackground = photoBackground;
    }

    @Basic
    @Column(name = "open_gns_sign", nullable = true)
    public Boolean getOpenGnsSign() {
        return openGnsSign;
    }

    public void setOpenGnsSign(Boolean openGnsSign) {
        this.openGnsSign = openGnsSign;
    }

    @Basic
    @Column(name = "gns_sign_interval", nullable = true)
    public Integer getGnsSignInterval() {
        return gnsSignInterval;
    }

    public void setGnsSignInterval(Integer gnsSignInterval) {
        this.gnsSignInterval = gnsSignInterval;
    }

    @Basic
    @Column(name = "gns_sign_count", nullable = true)
    public Integer getGnsSignCount() {
        return gnsSignCount;
    }

    public void setGnsSignCount(Integer gnsSignCount) {
        this.gnsSignCount = gnsSignCount;
    }

    @Basic
    @Column(name = "pohoto_taken_count", nullable = true)
    public Integer getPohotoTakenCount() {
        return pohotoTakenCount;
    }

    public void setPohotoTakenCount(Integer pohotoTakenCount) {
        this.pohotoTakenCount = pohotoTakenCount;
    }

    @Basic
    @Column(name = "gns_hot", nullable = true)
    public Boolean getGnsHot() {
        return gnsHot;
    }

    public void setGnsHot(Boolean gnsHot) {
        this.gnsHot = gnsHot;
    }

    @Basic
    @Column(name = "thumbs_up_count", nullable = true)
    public Integer getThumbsUpCount() {
        return thumbsUpCount;
    }

    public void setThumbsUpCount(Integer thumbsUpCount) {
        this.thumbsUpCount = thumbsUpCount;
    }

    @ManyToOne
    @JoinColumn(name = "type_code", insertable = false, updatable = false)
    public MapPointType getMapPointType() {
        return mapPointType;
    }

    public void setMapPointType(MapPointType mapPointType) {
        this.mapPointType = mapPointType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapPoint mapPoint = (MapPoint) o;
        return pointCode == mapPoint.pointCode &&
                Objects.equals(typeCode, mapPoint.typeCode) &&
                Objects.equals(pointName, mapPoint.pointName) &&
                Objects.equals(campusCode, mapPoint.campusCode) &&
                Objects.equals(leaf, mapPoint.leaf) &&
                Objects.equals(location, mapPoint.location) &&
                Objects.equals(lngLat, mapPoint.lngLat) &&
                Objects.equals(rasterLngLat, mapPoint.rasterLngLat) &&
                Objects.equals(orderId, mapPoint.orderId) &&
                Objects.equals(brief, mapPoint.brief) &&
                Objects.equals(mapCode, mapPoint.mapCode) &&
                Objects.equals(versionCode, mapPoint.versionCode) &&
                Objects.equals(synStatus, mapPoint.synStatus) &&
                Objects.equals(delete, mapPoint.delete) &&
                Objects.equals(memo, mapPoint.memo) &&
                Objects.equals(audioUrl, mapPoint.audioUrl) &&
                Objects.equals(videoUrl, mapPoint.videoUrl) &&
                Objects.equals(roamUrl, mapPoint.roamUrl) &&
                Objects.equals(photoBackground, mapPoint.photoBackground) &&
                Objects.equals(openGnsSign, mapPoint.openGnsSign) &&
                Objects.equals(gnsSignInterval, mapPoint.gnsSignInterval) &&
                Objects.equals(gnsSignCount, mapPoint.gnsSignCount) &&
                Objects.equals(pohotoTakenCount, mapPoint.pohotoTakenCount) &&
                Objects.equals(gnsHot, mapPoint.gnsHot) &&
                Objects.equals(thumbsUpCount, mapPoint.thumbsUpCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointCode, typeCode, pointName, campusCode, leaf, location, lngLat, rasterLngLat, orderId, brief, mapCode, versionCode, synStatus, delete, memo, audioUrl, videoUrl, roamUrl, photoBackground, openGnsSign, gnsSignInterval, gnsSignCount, pohotoTakenCount, gnsHot, thumbsUpCount);
    }
}
