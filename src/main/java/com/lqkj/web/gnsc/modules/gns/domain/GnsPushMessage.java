package com.lqkj.web.gnsc.modules.gns.domain;

import com.vividsolutions.jts.geom.Geometry;

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
@Table(name = "gns_push_message", schema = "gns")
public class GnsPushMessage {
    private UUID pushId;
    private UUID userId;
    private String title;
    private Integer pushType;
    private String navigationName;
    private Geometry navigationLocation;
    private Long landmarkId;
    private Boolean landmarkIsPolygon;
    private Timestamp createTime;

    @Id
    @Column(name = "push_id", nullable = false)
    public UUID getPushId() {
        return pushId;
    }

    public void setPushId(UUID pushId) {
        this.pushId = pushId;
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
    @Column(name = "title", nullable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "push_type", nullable = true)
    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    @Basic
    @Column(name = "navigation_name", nullable = true, length = 255)
    public String getNavigationName() {
        return navigationName;
    }

    public void setNavigationName(String navigationName) {
        this.navigationName = navigationName;
    }

    @Basic
    @Column(name = "navigation_location", nullable = true)
    public Geometry getNavigationLocation() {
        return navigationLocation;
    }

    public void setNavigationLocation(Geometry navigationLocation) {
        this.navigationLocation = navigationLocation;
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
    @Column(name = "landmark_is_polygon", nullable = true)
    public Boolean getLandmarkIsPolygon() {
        return landmarkIsPolygon;
    }

    public void setLandmarkIsPolygon(Boolean landmarkIsPolygon) {
        this.landmarkIsPolygon = landmarkIsPolygon;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
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
        GnsPushMessage that = (GnsPushMessage) o;
        return Objects.equals(pushId, that.pushId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(pushType, that.pushType) &&
                Objects.equals(navigationName, that.navigationName) &&
                Objects.equals(navigationLocation, that.navigationLocation) &&
                Objects.equals(landmarkId, that.landmarkId) &&
                Objects.equals(landmarkIsPolygon, that.landmarkIsPolygon) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pushId, userId, title, pushType, navigationName, navigationLocation, landmarkId, landmarkIsPolygon, createTime);
    }
}
