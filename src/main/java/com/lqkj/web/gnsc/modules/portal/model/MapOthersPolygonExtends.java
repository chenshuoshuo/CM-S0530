package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:48
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_others_polygon_extends", schema = "portal")
@IdClass(MapOthersPolygonExtendsPK.class)
public class MapOthersPolygonExtends {
    private Integer polygonCode;
    private Integer columnId;
    private Integer typeCode;
    private String extendsValue;
    private MapOptExtendsDefine mapOptExtendsDefine;

    @Id
    @Column(name = "polygon_code", nullable = false)
    public Integer getPolygonCode() {
        return polygonCode;
    }

    public void setPolygonCode(Integer polygonCode) {
        this.polygonCode = polygonCode;
    }

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
    public MapOptExtendsDefine getMapOptExtendsDefine() {
        return mapOptExtendsDefine;
    }

    public void setMapOptExtendsDefine(MapOptExtendsDefine mapOptExtendsDefine) {
        this.mapOptExtendsDefine = mapOptExtendsDefine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapOthersPolygonExtends that = (MapOthersPolygonExtends) o;
        return polygonCode == that.polygonCode &&
                columnId == that.columnId &&
                typeCode == that.typeCode &&
                Objects.equals(extendsValue, that.extendsValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(polygonCode, columnId, typeCode, extendsValue);
    }
}
