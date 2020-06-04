package com.lqkj.web.gnsc.modules.gns.domain;

import com.lqkj.web.gnsc.utils.CommonUtils;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * @author cs
 * @Date 2020/5/28 12:51
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_sign", schema = "gns")
public class GnsSign {
    private Integer signId;
    private UUID userId;
    private Long landmarkId;
    private String landmarkName;
    private String landmarkType;
    private Timestamp createTime;
    private String formatTime;

    public GnsSign() {
    }

    public GnsSign(UUID userId, Long landmarkId, String landmarkName, String landmarkType) {
        this.userId = userId;
        this.landmarkId = landmarkId;
        this.landmarkName = landmarkName;
        this.landmarkType = landmarkType;
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
        GnsSign gnsSign = (GnsSign) o;
        return signId == gnsSign.signId &&
                Objects.equals(userId, gnsSign.userId) &&
                Objects.equals(landmarkId, gnsSign.landmarkId) &&
                Objects.equals(landmarkName, gnsSign.landmarkName) &&
                Objects.equals(landmarkType, gnsSign.landmarkType) &&
                Objects.equals(createTime, gnsSign.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signId, userId, landmarkId, landmarkName, landmarkType, createTime);
    }

    @Transient
    public String getFormatTime() {
        if (createTime == null)
            return "";
        return CommonUtils.formatTimeStamp(createTime);
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }
}
