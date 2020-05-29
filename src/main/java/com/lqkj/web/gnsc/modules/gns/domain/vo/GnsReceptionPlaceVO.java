package com.lqkj.web.gnsc.modules.gns.domain.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
public class GnsReceptionPlaceVO {
    private Integer placeId;
    private String tyeName;
    private String campusName;
    private String title;
    private String content;
    private Timestamp updateTime;

    @Id
    @Column(name = "place_id", nullable = false)
    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    @Column(name = "type_name")
    public String getTyeName() {
        return tyeName;
    }

    public void setTyeName(String tyeName) {
        this.tyeName = tyeName;
    }

    @Column(name = "campus_name")
    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    @Basic
    @Column(name = "update_time", nullable = true, length = -1)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }


}
