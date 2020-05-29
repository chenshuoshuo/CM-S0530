package com.lqkj.web.gnsc.modules.gns.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
public class GnsDisplayPointTypePK implements Serializable {
    private Integer pointTypeCode;
    private Integer schoolId;

    @Column(name = "point_type_code", nullable = false)
    @Id
    public Integer getPointTypeCode() {
        return pointTypeCode;
    }

    public void setPointTypeCode(Integer pointTypeCode) {
        this.pointTypeCode = pointTypeCode;
    }

    @Column(name = "school_id", nullable = false)
    @Id
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
        GnsDisplayPointTypePK that = (GnsDisplayPointTypePK) o;
        return pointTypeCode == that.pointTypeCode &&
                schoolId == that.schoolId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointTypeCode, schoolId);
    }
}
