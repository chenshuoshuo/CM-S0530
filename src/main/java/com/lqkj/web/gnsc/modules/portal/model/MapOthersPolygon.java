package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:48
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_others_polygon", schema = "portal")
public class MapOthersPolygon {
    private Integer polygonCode;
    private Integer typeCode;
    private String polygonName;
    private Integer campusCode;
    private Long mapCode;
    private String enName;
    private String alias;
    private Boolean synStatus;
    private Integer orderId;
    private Boolean delete;
    private String brief;
    private Integer leaf;
    private Integer versionCode;
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
    @Column(name = "polygon_code", nullable = false)
    public Integer getPolygonCode() {
        return polygonCode;
    }

    public void setPolygonCode(Integer polygonCode) {
        this.polygonCode = polygonCode;
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
    @Column(name = "polygon_name", nullable = true, length = 255)
    public String getPolygonName() {
        return polygonName;
    }

    public void setPolygonName(String polygonName) {
        this.polygonName = polygonName;
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
        MapOthersPolygon that = (MapOthersPolygon) o;
        return polygonCode == that.polygonCode &&
                Objects.equals(typeCode, that.typeCode) &&
                Objects.equals(polygonName, that.polygonName) &&
                Objects.equals(campusCode, that.campusCode) &&
                Objects.equals(mapCode, that.mapCode) &&
                Objects.equals(enName, that.enName) &&
                Objects.equals(alias, that.alias) &&
                Objects.equals(synStatus, that.synStatus) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(delete, that.delete) &&
                Objects.equals(brief, that.brief) &&
                Objects.equals(leaf, that.leaf) &&
                Objects.equals(versionCode, that.versionCode) &&
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
        return Objects.hash(polygonCode, typeCode, polygonName, campusCode, mapCode, enName, alias, synStatus, orderId, delete, brief, leaf, versionCode, memo, audioUrl, videoUrl, roamUrl, photoBackground, openGnsSign, gnsSignInterval, gnsSignCount, pohotoTakenCount, thumbsUpCount);
    }
}
