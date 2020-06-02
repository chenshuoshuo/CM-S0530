package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:47
 * @Version 2.2.2.0
 **/
public class MapBuildingExtendsPK implements Serializable {
    private Integer columnId;
    private Integer typeCode;
    private Integer buildingCode;

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

    @Column(name = "building_code", nullable = false)
    @Id
    public Integer getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapBuildingExtendsPK that = (MapBuildingExtendsPK) o;
        return columnId == that.columnId &&
                typeCode == that.typeCode &&
                buildingCode == that.buildingCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnId, typeCode, buildingCode);
    }
}
