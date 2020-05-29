package com.lqkj.web.gnsc.modules.gns.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:51
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_student_type", schema = "gns", catalog = "CM-S0530")
public class GnsStudentType {
    private Integer studnetTypeCode;
    private Integer schoolId;
    private String typeName;
    private Date startDate;
    private Date endDate;
    private Integer orderId;
    private String memo;

    @Id
    @Column(name = "studnet_type_code", nullable = false)
    public Integer getStudnetTypeCode() {
        return studnetTypeCode;
    }

    public void setStudnetTypeCode(int studnetTypeCode) {
        this.studnetTypeCode = studnetTypeCode;
    }

    @Basic
    @Column(name = "school_id", nullable = true)
    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Basic
    @Column(name = "type_name", nullable = true, length = 50)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "start_date", nullable = true)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = true)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        GnsStudentType that = (GnsStudentType) o;
        return studnetTypeCode == that.studnetTypeCode &&
                Objects.equals(schoolId, that.schoolId) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studnetTypeCode, schoolId, typeName, startDate, endDate, orderId, memo);
    }
}
