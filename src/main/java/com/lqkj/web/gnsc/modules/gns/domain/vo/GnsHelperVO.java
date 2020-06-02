package com.lqkj.web.gnsc.modules.gns.domain.vo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_helper", schema = "gns")
public class GnsHelperVO {
    private Integer helperId;
    private String typeName;
    private String title;
    private String contact;
    private Timestamp updateTime;

    @Id
    @Column(name = "helper_id", nullable = false)
    public Integer getHelperId() {
        return helperId;
    }

    public void setHelperId(Integer helperId) {
        this.helperId = helperId;
    }

    @Basic
    @Column(name = "type_name", nullable = false)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
    @Column(name = "contact", nullable = true, length = 50)
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
