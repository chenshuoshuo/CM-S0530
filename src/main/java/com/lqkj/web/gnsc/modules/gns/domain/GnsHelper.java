package com.lqkj.web.gnsc.modules.gns.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_helper", schema = "gns", catalog = "CM-S0530")
public class GnsHelper {
    private Integer helperId;
    private Integer typeCode;
    private String title;
    private String contact;
    private Timestamp updateTime;
    private Integer orderId;
    private String memo;

    @Id
    @Column(name = "helper_id", nullable = false)
    public Integer getHelperId() {
        return helperId;
    }

    public void setHelperId(Integer helperId) {
        this.helperId = helperId;
    }

    @Basic
    @Column(name = "type_code", nullable = false)
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
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
        GnsHelper gnsHelper = (GnsHelper) o;
        return helperId == gnsHelper.helperId &&
                typeCode == gnsHelper.typeCode &&
                Objects.equals(title, gnsHelper.title) &&
                Objects.equals(contact, gnsHelper.contact) &&
                Objects.equals(updateTime, gnsHelper.updateTime) &&
                Objects.equals(orderId, gnsHelper.orderId) &&
                Objects.equals(memo, gnsHelper.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(helperId, typeCode, title, contact, updateTime, orderId, memo);
    }
}
