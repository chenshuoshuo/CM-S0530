package com.lqkj.web.gnsc.modules.gns.domain;

import com.vladmihalcea.hibernate.type.basic.Inet;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_achievement", schema = "gns", catalog = "CM-S0530")
@IdClass(GnsAchievementPK.class)
public class GnsAchievement {
    private Integer achievementId;
    private Integer schoolId;
    private String achievementName;
    private String achievedIcon;
    private String notAchievedIcon;
    private String brief;
    private String condition;
    private Timestamp updateTime;
    private Integer orderId;
    private String memo;

    @Id
    @Column(name = "achievement_id", nullable = false)
    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    @Id
    @Column(name = "school_id", nullable = false)
    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Basic
    @Column(name = "achievement_name", nullable = true, length = 50)
    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    @Basic
    @Column(name = "achieved_icon", nullable = true, length = 1024)
    public String getAchievedIcon() {
        return achievedIcon;
    }

    public void setAchievedIcon(String achievedIcon) {
        this.achievedIcon = achievedIcon;
    }

    @Basic
    @Column(name = "not_achieved_icon", nullable = true, length = 1024)
    public String getNotAchievedIcon() {
        return notAchievedIcon;
    }

    public void setNotAchievedIcon(String notAchievedIcon) {
        this.notAchievedIcon = notAchievedIcon;
    }

    @Basic
    @Column(name = "brief", nullable = true, length = 100)
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    @Basic
    @Column(name = "condition", nullable = true, length = 100)
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Basic
    @Column(name = "update_time", nullable = true, length = -1)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsAchievement that = (GnsAchievement) o;
        return achievementId == that.achievementId &&
                schoolId == that.schoolId &&
                Objects.equals(achievementName, that.achievementName) &&
                Objects.equals(achievedIcon, that.achievedIcon) &&
                Objects.equals(notAchievedIcon, that.notAchievedIcon) &&
                Objects.equals(brief, that.brief) &&
                Objects.equals(condition, that.condition) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(achievementId, schoolId, achievementName, achievedIcon, notAchievedIcon, brief, condition, updateTime, orderId, memo);
    }
}
