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
@Table(name = "gns_map_use", schema = "gns", catalog = "CM-S0530")
public class GnsMapUse {
    private UUID recordId;
    private UUID gnsUserId;
    private Integer recordType;
    private String mapElementName;
    private Timestamp createTime;

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
    @Column(name = "gns_user_id", nullable = true)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    public UUID getGnsUserId() {
        return gnsUserId;
    }

    public void setGnsUserId(UUID gnsUserId) {
        this.gnsUserId = gnsUserId;
    }

    @Basic
    @Column(name = "record_type", nullable = true)
    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    @Basic
    @Column(name = "map_element_name", nullable = true, length = 255)
    public String getMapElementName() {
        return mapElementName;
    }

    public void setMapElementName(String mapElementName) {
        this.mapElementName = mapElementName;
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
        GnsMapUse gnsMapUse = (GnsMapUse) o;
        return Objects.equals(recordId, gnsMapUse.recordId) &&
                Objects.equals(gnsUserId, gnsMapUse.gnsUserId) &&
                Objects.equals(recordType, gnsMapUse.recordType) &&
                Objects.equals(mapElementName, gnsMapUse.mapElementName) &&
                Objects.equals(createTime, gnsMapUse.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, gnsUserId, recordType, mapElementName, createTime);
    }
}
