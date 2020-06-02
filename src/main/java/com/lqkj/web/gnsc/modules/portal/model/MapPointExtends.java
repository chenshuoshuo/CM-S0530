package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:48
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_point_extends", schema = "portal")
@IdClass(MapPointExtendsPK.class)
public class MapPointExtends {
    private Integer columnId;
    private Integer typeCode;
    private Integer pointCode;
    private String extendsValue;
    private MapPtExtendsDefine mapPtExtendsDefine;

    @Id
    @Column(name = "column_id", nullable = false)
    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    @Id
    @Column(name = "type_code", nullable = false)
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    @Id
    @Column(name = "point_code", nullable = false)
    public Integer getPointCode() {
        return pointCode;
    }

    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
    }

    @Basic
    @Column(name = "extends_value", nullable = true, length = 1024)
    public String getExtendsValue() {
        return extendsValue;
    }

    public void setExtendsValue(String extendsValue) {
        this.extendsValue = extendsValue;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "column_id", referencedColumnName = "column_id",
            nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "type_code", referencedColumnName = "type_code",
                    nullable = false, insertable = false, updatable = false)})
    public MapPtExtendsDefine getMapPtExtendsDefine() {
        return mapPtExtendsDefine;
    }

    public void setMapPtExtendsDefine(MapPtExtendsDefine mapPtExtendsDefine) {
        this.mapPtExtendsDefine = mapPtExtendsDefine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapPointExtends that = (MapPointExtends) o;
        return columnId == that.columnId &&
                typeCode == that.typeCode &&
                pointCode == that.pointCode &&
                Objects.equals(extendsValue, that.extendsValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnId, typeCode, pointCode, extendsValue);
    }
}
