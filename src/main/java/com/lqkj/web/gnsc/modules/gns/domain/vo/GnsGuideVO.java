package com.lqkj.web.gnsc.modules.gns.domain.vo;

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
public class GnsGuideVO {
    private Integer guideId;
    private String tyeName;
    private String campusName;
    private String title;
    private String content;
    private Timestamp updateTime;
    private Integer orderId;

    @Id
    @Column(name = "guide_id", nullable = false)
    public Integer getGuideId() {
        return guideId;
    }

    public void setGuideId(Integer guideId) {
        this.guideId = guideId;
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

    @Basic
    @Column(name = "order_id", nullable = true)
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }



}
