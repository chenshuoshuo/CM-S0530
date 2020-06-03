package com.lqkj.web.gnsc.modules.gns.domain;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_group_photo", schema = "gns")
public class GnsGroupPhoto {
    private Integer signId;
    private UUID userId;
    private Long landmarkId;
    private String landmarkName;
    private String landmarkType;
    private String photoUrl;
    private Timestamp createTime;

    public GnsGroupPhoto() {
    }

    public GnsGroupPhoto(UUID userId, Long landmarkId, String landmarkName, String landmarkType, String photoUrl) {
        this.userId = userId;
        this.landmarkId = landmarkId;
        this.landmarkName = landmarkName;
        this.landmarkType = landmarkType;
        this.photoUrl = photoUrl;
    }

    @Id
    @Column(name = "sign_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getSignId() {
        return signId;
    }

    public void setSignId(Integer signId) {
        this.signId = signId;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "landmark_id", nullable = true)
    public Long getLandmarkId() {
        return landmarkId;
    }

    public void setLandmarkId(Long landmarkId) {
        this.landmarkId = landmarkId;
    }

    @Basic
    @Column(name = "landmark_name", nullable = true, length = 255)
    public String getLandmarkName() {
        return landmarkName;
    }

    public void setLandmarkName(String landmarkName) {
        this.landmarkName = landmarkName;
    }

    @Basic
    @Column(name = "landmark_type", nullable = true, length = 50)
    public String getLandmarkType() {
        return landmarkType;
    }

    public void setLandmarkType(String landmarkType) {
        this.landmarkType = landmarkType;
    }

    @Basic
    @Column(name = "photo_url", nullable = true, length = 1024)
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    @UpdateTimestamp
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsGroupPhoto that = (GnsGroupPhoto) o;
        return signId == that.signId &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(landmarkId, that.landmarkId) &&
                Objects.equals(landmarkName, that.landmarkName) &&
                Objects.equals(landmarkType, that.landmarkType) &&
                Objects.equals(photoUrl, that.photoUrl) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signId, userId, landmarkId, landmarkName, landmarkType, photoUrl, createTime);
    }
}
