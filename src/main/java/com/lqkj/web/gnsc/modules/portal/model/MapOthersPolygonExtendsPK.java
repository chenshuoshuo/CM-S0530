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
public class MapOthersPolygonExtendsPK implements Serializable {
    private int polygonCode;
    private int columnId;
    private int typeCode;

    @Column(name = "polygon_code", nullable = false)
    @Id
    public int getPolygonCode() {
        return polygonCode;
    }

    public void setPolygonCode(int polygonCode) {
        this.polygonCode = polygonCode;
    }

    @Column(name = "column_id", nullable = false)
    @Id
    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    @Column(name = "type_code", nullable = false)
    @Id
    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapOthersPolygonExtendsPK that = (MapOthersPolygonExtendsPK) o;
        return polygonCode == that.polygonCode &&
                columnId == that.columnId &&
                typeCode == that.typeCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(polygonCode, columnId, typeCode);
    }
}
