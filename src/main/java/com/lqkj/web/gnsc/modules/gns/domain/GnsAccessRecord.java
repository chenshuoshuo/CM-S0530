package com.lqkj.web.gnsc.modules.gns.domain;

import org.hibernate.annotations.Type;
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
@Table(name = "gns_access_record", schema = "gns")
public class GnsAccessRecord {
    private UUID recordId;
    private UUID userId;
    private String ip;
    private Timestamp createTime;
    private String createDate;
    private String createMonth;
    private String createHour;

    public GnsAccessRecord() {
        this.recordId = UUID.randomUUID();
    }

    public GnsAccessRecord(UUID userId, String ip,String createDate, String createMonth, String createHour) {
        this.recordId = UUID.randomUUID();;
        this.userId = userId;
        this.ip = ip;
        this.createDate = createDate;
        this.createMonth = createMonth;
        this.createHour = createHour;
    }

    @Id
    @Column(name = "record_id", nullable = false)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    public UUID getRecordId() {
        return recordId;
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "ip", nullable = true, length = 128)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    @Basic
    @Column(name = "create_date", nullable = true, length = 50)
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "create_month", nullable = true, length = 50)
    public String getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(String createMonth) {
        this.createMonth = createMonth;
    }

    @Basic
    @Column(name = "create_hour", nullable = true, length = 50)
    public String getCreateHour() {
        return createHour;
    }

    public void setCreateHour(String createHour) {
        this.createHour = createHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsAccessRecord that = (GnsAccessRecord) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(createMonth, that.createMonth) &&
                Objects.equals(createHour, that.createHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, userId, ip, createTime, createDate, createMonth, createHour);
    }
}
