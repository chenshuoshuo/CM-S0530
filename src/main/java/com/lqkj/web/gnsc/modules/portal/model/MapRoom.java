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
@Table(name = "map_room", schema = "portal")
public class MapRoom {
    private Integer roomCode;
    private Integer typeCode;
    private String roomName;
    private Integer campusCode;
    private Long mapCode;
    private Long buildingMapCode;
    private String buildingName;
    private String enName;
    private String alias;
    private String hourseNumber;
    private Boolean synStatus;
    private Integer orderId;
    private Boolean delete;
    private String brief;
    private Integer leaf;
    private Integer versionCode;
    @JsonSerialize(using = JacksonGeometrySerializer.class)
    @JsonDeserialize(using = JacksonGeometryDeserializer.class)
    private Geometry lngLat;
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


    @Id
    @Column(name = "room_code", nullable = false)
    public Integer getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Integer roomCode) {
        this.roomCode = roomCode;
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
    @Column(name = "room_name", nullable = true, length = 255)
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
    @Column(name = "building_map_code", nullable = true)
    public Long getBuildingMapCode() {
        return buildingMapCode;
    }

    public void setBuildingMapCode(Long buildingMapCode) {
        this.buildingMapCode = buildingMapCode;
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
    @Column(name = "hourse_number", nullable = true, length = 64)
    public String getHourseNumber() {
        return hourseNumber;
    }

    public void setHourseNumber(String hourseNumber) {
        this.hourseNumber = hourseNumber;
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
    @Column(name = "order_id", nullable = true)
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
    @Column(name = "leaf", nullable = true)
    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapRoom mapRoom = (MapRoom) o;
        return roomCode == mapRoom.roomCode &&
                Objects.equals(typeCode, mapRoom.typeCode) &&
                Objects.equals(roomName, mapRoom.roomName) &&
                Objects.equals(campusCode, mapRoom.campusCode) &&
                Objects.equals(mapCode, mapRoom.mapCode) &&
                Objects.equals(buildingMapCode, mapRoom.buildingMapCode) &&
                Objects.equals(buildingName, mapRoom.buildingName) &&
                Objects.equals(enName, mapRoom.enName) &&
                Objects.equals(alias, mapRoom.alias) &&
                Objects.equals(hourseNumber, mapRoom.hourseNumber) &&
                Objects.equals(synStatus, mapRoom.synStatus) &&
                Objects.equals(orderId, mapRoom.orderId) &&
                Objects.equals(delete, mapRoom.delete) &&
                Objects.equals(brief, mapRoom.brief) &&
                Objects.equals(leaf, mapRoom.leaf) &&
                Objects.equals(versionCode, mapRoom.versionCode) &&
                Objects.equals(lngLat, mapRoom.lngLat) &&
                Objects.equals(memo, mapRoom.memo) &&
                Objects.equals(audioUrl, mapRoom.audioUrl) &&
                Objects.equals(videoUrl, mapRoom.videoUrl) &&
                Objects.equals(roamUrl, mapRoom.roamUrl) &&
                Objects.equals(photoBackground, mapRoom.photoBackground) &&
                Objects.equals(openGnsSign, mapRoom.openGnsSign) &&
                Objects.equals(gnsSignInterval, mapRoom.gnsSignInterval) &&
                Objects.equals(gnsSignCount, mapRoom.gnsSignCount) &&
                Objects.equals(pohotoTakenCount, mapRoom.pohotoTakenCount) &&
                Objects.equals(thumbsUpCount, mapRoom.thumbsUpCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomCode, typeCode, roomName, campusCode, mapCode, buildingMapCode, buildingName, enName, alias, hourseNumber, synStatus, orderId, delete, brief, leaf, versionCode, lngLat, memo, audioUrl, videoUrl, roamUrl, photoBackground, openGnsSign, gnsSignInterval, gnsSignCount, pohotoTakenCount, thumbsUpCount);
    }
}
