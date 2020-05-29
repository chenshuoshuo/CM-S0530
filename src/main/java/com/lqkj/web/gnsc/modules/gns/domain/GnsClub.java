package com.lqkj.web.gnsc.modules.gns.domain;

import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_club", schema = "gns", catalog = "CM-S0530")
public class GnsClub {
    private Integer clubId;
    private Integer campusCode;
    private String clubName;
    private String clubLogo;
    private Geometry location;
    private String description;
    private Timestamp updateTime;
    private Integer orderId;
    private String memo;

    @Id
    @Column(name = "club_id", nullable = false)
    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
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
    @Column(name = "club_name", nullable = true, length = 50)
    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @Basic
    @Column(name = "club_logo", nullable = true, length = 1024)
    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    @Basic
    @Column(name = "location", nullable = true)
    public Geometry getLocation() {
        return location;
    }

    public void setLocation(Geometry location) {
        this.location = location;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        GnsClub gnsClub = (GnsClub) o;
        return clubId == gnsClub.clubId &&
                Objects.equals(campusCode, gnsClub.campusCode) &&
                Objects.equals(clubName, gnsClub.clubName) &&
                Objects.equals(clubLogo, gnsClub.clubLogo) &&
                Objects.equals(location, gnsClub.location) &&
                Objects.equals(description, gnsClub.description) &&
                Objects.equals(updateTime, gnsClub.updateTime) &&
                Objects.equals(orderId, gnsClub.orderId) &&
                Objects.equals(memo, gnsClub.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clubId, campusCode, clubName, clubLogo, location, description, updateTime, orderId, memo);
    }
}
