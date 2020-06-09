package com.lqkj.web.gnsc.modules.portal.model;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lqkj.web.gnsc.utils.JacksonGeometryDeserializer;
import com.lqkj.web.gnsc.utils.JacksonGeometrySerializer;
import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:47
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_building", schema = "portal")
public class MapBuilding {
    private Integer buildingCode;
    private String buildingName;
    private Integer typeCode;
    private Integer campusCode;
    private Long mapCode;
    private String enName;
    private String alias;
    private Integer orderId;
    private Boolean synStatus;
    private Boolean delete;
    private String brief;
    private Integer versionCode;
    @JsonSerialize(using = JacksonGeometrySerializer.class)
    @JsonDeserialize(using = JacksonGeometryDeserializer.class)
    private Geometry lngLat;
    @JsonSerialize(using = JacksonGeometrySerializer.class)
    @JsonDeserialize(using = JacksonGeometryDeserializer.class)
    private Geometry rasterLngLat;
    private String memo;
    private String audioUrl;
    private String videoUrl;
    private String roamUrl;
    private String photoBackground;
    private Boolean openGnsSign;
    private Integer gnsSignInterval;
    private Integer gnsSignCount;
    private Integer pohotoTakenCount;
    private Integer thumbsUpCount;
    private String campusName;
    private String center;
    private JSONObject geoJson;
    private MapBuildingType mapBuildingType;
    private List<MapBuildingImg> mapBuildingImgList;
    private List<MapBuildingExtends> mapBuildingExtendsList;

    @Id
    @Column(name = "building_code", nullable = false)
    public Integer getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
    }

    @Column(name = "type_code")
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "building_name", nullable = true, length = 255)
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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
    @Column(name = "map_code", nullable = true)
    public Long getMapCode() {
        return mapCode;
    }

    public void setMapCode(Long mapCode) {
        this.mapCode = mapCode;
    }

    @Basic
    @Column(name = "en_name", nullable = true, length = 255)
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Basic
    @Column(name = "alias", nullable = true, length = 255)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
    @Column(name = "brief", nullable = true, length = -1)
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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
    @Column(name = "thumbs_up_count", nullable = true)
    public Integer getThumbsUpCount() {
        return thumbsUpCount;
    }

    public void setThumbsUpCount(Integer thumbsUpCount) {
        this.thumbsUpCount = thumbsUpCount;
    }

    @Transient
    public MapBuildingType getMapBuildingType() {
        return mapBuildingType;
    }

    public void setMapBuildingType(MapBuildingType mapBuildingType) {
        this.mapBuildingType = mapBuildingType;
    }

    @Transient
    public List<MapBuildingImg> getMapBuildingImgList() {
        return mapBuildingImgList;
    }

    public void setMapBuildingImgList(List<MapBuildingImg> mapBuildingImgList) {
        this.mapBuildingImgList = mapBuildingImgList;
    }

    @Transient
    public List<MapBuildingExtends> getMapBuildingExtendsList() {
        return mapBuildingExtendsList;
    }

    public void setMapBuildingExtendsList(List<MapBuildingExtends> mapBuildingExtendsList) {
        this.mapBuildingExtendsList = mapBuildingExtendsList;
    }

    @Transient
    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    @Transient
    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    @Transient
    public JSONObject getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(JSONObject geoJson) {
        this.geoJson = geoJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapBuilding that = (MapBuilding) o;
        return buildingCode == that.buildingCode &&
                Objects.equals(buildingName, that.buildingName) &&
                Objects.equals(campusCode, that.campusCode) &&
                Objects.equals(mapCode, that.mapCode) &&
                Objects.equals(enName, that.enName) &&
                Objects.equals(alias, that.alias) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(synStatus, that.synStatus) &&
                Objects.equals(delete, that.delete) &&
                Objects.equals(brief, that.brief) &&
                Objects.equals(versionCode, that.versionCode) &&
                Objects.equals(lngLat, that.lngLat) &&
                Objects.equals(rasterLngLat, that.rasterLngLat) &&
                Objects.equals(memo, that.memo) &&
                Objects.equals(audioUrl, that.audioUrl) &&
                Objects.equals(videoUrl, that.videoUrl) &&
                Objects.equals(roamUrl, that.roamUrl) &&
                Objects.equals(photoBackground, that.photoBackground) &&
                Objects.equals(openGnsSign, that.openGnsSign) &&
                Objects.equals(gnsSignInterval, that.gnsSignInterval) &&
                Objects.equals(gnsSignCount, that.gnsSignCount) &&
                Objects.equals(pohotoTakenCount, that.pohotoTakenCount) &&
                Objects.equals(thumbsUpCount, that.thumbsUpCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingCode, buildingName, campusCode, mapCode, enName, alias, orderId, synStatus, delete, brief, versionCode, lngLat, rasterLngLat, memo, audioUrl, videoUrl, roamUrl, photoBackground, openGnsSign, gnsSignInterval, gnsSignCount, pohotoTakenCount, thumbsUpCount);
    }
}
