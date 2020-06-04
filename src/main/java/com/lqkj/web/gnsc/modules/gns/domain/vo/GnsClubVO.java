package com.lqkj.web.gnsc.modules.gns.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lqkj.web.gnsc.utils.JacksonGeometryDeserializer;
import com.lqkj.web.gnsc.utils.JacksonGeometrySerializer;
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
public class GnsClubVO {
    private Integer clubId;
    private Integer campusName;
    private String clubName;
    private String description;
    private Integer click;
    private Timestamp updateTime;

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
    public Integer getCampusName() {
        return campusName;
    }

    public void setCampusName(Integer campusName) {
        this.campusName = campusName;
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
    @Column(name = "click")
    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }
}
