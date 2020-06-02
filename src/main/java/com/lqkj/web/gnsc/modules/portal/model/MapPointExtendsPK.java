package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:48
 * @Version 2.2.2.0
 **/
public class MapPointExtendsPK implements Serializable {
    private Integer columnId;
    private Integer typeCode;
    private Integer pointCode;

    @Column(name = "column_id", nullable = false)
    @Id
    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    @Column(name = "type_code", nullable = false)
    @Id
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    @Column(name = "point_code", nullable = false)
    @Id
    public Integer getPointCode() {
        return pointCode;
    }

    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapPointExtendsPK that = (MapPointExtendsPK) o;
        return columnId == that.columnId &&
                typeCode == that.typeCode &&
                pointCode == that.pointCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnId, typeCode, pointCode);
    }
}
