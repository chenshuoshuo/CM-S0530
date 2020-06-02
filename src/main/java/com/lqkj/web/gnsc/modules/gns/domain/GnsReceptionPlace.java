package com.lqkj.web.gnsc.modules.gns.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lqkj.web.gnsc.utils.JacksonGeometryDeserializer;
import com.lqkj.web.gnsc.utils.JacksonGeometrySerializer;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:51
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_reception_place", schema = "gns")
public class GnsReceptionPlace {
    private Integer placeId;
    private Integer typeCode;
    private Integer campusCode;
    private String title;
    private String content;
    @JsonSerialize(using = JacksonGeometrySerializer.class)
    @JsonDeserialize(using = JacksonGeometryDeserializer.class)
    private Geometry lngLat;
    private Timestamp updateTime;
    private Integer orderId;
    private String memo;

    @Id
    @Column(name = "place_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    @Basic
    @Column(name = "type_code", nullable = true)
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "campus_code", nullable = true)
    public Integer getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(Integer campusCode) {
        this.campusCode = campusCode;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "lng_lat", nullable = true)
    public Geometry getLngLat() {
        return lngLat;
    }

    public void setLngLat(Geometry lngLat) {
        this.lngLat = lngLat;
    }

    @Basic
    @Column(name = "update_time", nullable = true, length = -1)
    @UpdateTimestamp
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
        GnsReceptionPlace that = (GnsReceptionPlace) o;
        return placeId == that.placeId &&
                Objects.equals(typeCode, that.typeCode) &&
                Objects.equals(campusCode, that.campusCode) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(lngLat, that.lngLat) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, typeCode, campusCode, title, content, lngLat, updateTime, orderId, memo);
    }
}
