package com.lqkj.web.gnsc.modules.gns.domain;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_application_use", schema = "gns")
public class GnsApplicationUse {
    private String recordId;
    private Integer applicationId;
    private String userId;
    private Timestamp createTime;

    public GnsApplicationUse() {
    }

    public GnsApplicationUse(String recordId, Integer applicationId, String userId) {
        this.recordId = recordId;
        this.applicationId = applicationId;
        this.userId = userId;
    }

    @Id
    @Column(name = "record_id", nullable = false)
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "application_id", nullable = true)
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
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
        GnsApplicationUse that = (GnsApplicationUse) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, applicationId, userId, createTime);
    }
}
