package com.lqkj.web.gnsc.modules.gns.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_display_point_type", schema = "gns", catalog = "CM-S0530")
@IdClass(GnsDisplayPointTypePK.class)
public class GnsDisplayPointType {
    private Integer pointTypeCode;
    private Integer schoolId;

    @Id
    @Column(name = "point_type_code", nullable = false)
    public Integer getPointTypeCode() {
        return pointTypeCode;
    }

    public void setPointTypeCode(Integer pointTypeCode) {
        this.pointTypeCode = pointTypeCode;
    }

    @Id
    @Column(name = "school_id", nullable = false)
    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsDisplayPointType that = (GnsDisplayPointType) o;
        return pointTypeCode == that.pointTypeCode &&
                schoolId == that.schoolId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointTypeCode, schoolId);
    }
}
