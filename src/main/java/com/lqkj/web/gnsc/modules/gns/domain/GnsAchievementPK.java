package com.lqkj.web.gnsc.modules.gns.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
public class GnsAchievementPK implements Serializable {
    private Integer achievementId;
    private Integer schoolId;

    @Column(name = "achievement_id", nullable = false)
    @Id
    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    @Column(name = "school_id", nullable = false)
    @Id
    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsAchievementPK that = (GnsAchievementPK) o;
        return achievementId == that.achievementId &&
                schoolId == that.schoolId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(achievementId, schoolId);
    }
}
