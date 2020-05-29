package com.lqkj.web.gnsc.modules.gns.domain;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:51
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_school", schema = "gns")
public class GnsSchool implements Serializable {
    private Integer schoolId;
    private String schoolName;
    private Timestamp accessTime;
    private String audioUrl;
    private String videoUrl;
    private String profile;
    private Integer orderId;
    private String memo;
    private Integer campusCount;
    private List<GnsCampusInfo> campusInfoList;

    @Id
    @Column(name = "school_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Basic
    @Column(name = "school_name", nullable = true, length = 255)
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Basic
    @Column(name = "access_time", nullable = true)
    @UpdateTimestamp
    public Timestamp getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Timestamp accessTime) {
        this.accessTime = accessTime;
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
    @Column(name = "profile", nullable = true, length = -1)
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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
    public Integer getCampusCount() {
        return campusCount;
    }

    public void setCampusCount(Integer campusCount) {
        this.campusCount = campusCount;
    }

    @Transient
    public List<GnsCampusInfo> getCampusInfoList() {
        return campusInfoList;
    }

    public void setCampusInfoList(List<GnsCampusInfo> campusInfoList) {
        this.campusInfoList = campusInfoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsSchool school = (GnsSchool) o;
        return Objects.equals(schoolId, school.schoolId) &&
                Objects.equals(schoolName, school.schoolName) &&
                Objects.equals(accessTime, school.accessTime) &&
                Objects.equals(audioUrl, school.audioUrl) &&
                Objects.equals(videoUrl, school.videoUrl) &&
                Objects.equals(profile, school.profile) &&
                Objects.equals(orderId, school.orderId) &&
                Objects.equals(memo, school.memo) &&
                Objects.equals(campusCount, school.campusCount) &&
                Objects.equals(campusInfoList, school.campusInfoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolId, schoolName, accessTime, audioUrl, videoUrl, profile, orderId, memo, campusCount, campusInfoList);
    }
}
