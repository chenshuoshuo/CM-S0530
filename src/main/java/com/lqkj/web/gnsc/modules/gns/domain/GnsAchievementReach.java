package com.lqkj.web.gnsc.modules.gns.domain;

import org.hibernate.annotations.Type;

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
@Table(name = "gns_achievement_reach", schema = "gns")
public class GnsAchievementReach {
    private Integer id;
    private String userId;
    private Integer achievementId;
    private Integer schoolId;
    private Timestamp reachTime;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "achievement_id", nullable = true)
    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    @Basic
    @Column(name = "school_id", nullable = true)
    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Basic
    @Column(name = "reach_time", nullable = true)
    public Timestamp getReachTime() {
        return reachTime;
    }

    public void setReachTime(Timestamp reachTime) {
        this.reachTime = reachTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsAchievementReach that = (GnsAchievementReach) o;
        return id == that.id &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(achievementId, that.achievementId) &&
                Objects.equals(schoolId, that.schoolId) &&
                Objects.equals(reachTime, that.reachTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, achievementId, schoolId, reachTime);
    }
}
