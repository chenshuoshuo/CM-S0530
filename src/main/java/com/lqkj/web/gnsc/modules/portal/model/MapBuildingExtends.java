package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:47
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_building_extends", schema = "portal")
@IdClass(MapBuildingExtendsPK.class)
public class MapBuildingExtends {
    private Integer columnId;
    private Integer typeCode;
    private Integer buildingCode;
    private String extendsValue;
    private MapBtExtendsDefine mapBtExtendsDefine;

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
    @Column(name = "building_code", nullable = false)
    public int getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(int buildingCode) {
        this.buildingCode = buildingCode;
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
    public MapBtExtendsDefine getMapBtExtendsDefine() {
        return mapBtExtendsDefine;
    }

    public void setMapBtExtendsDefine(MapBtExtendsDefine mapBtExtendsDefine) {
        this.mapBtExtendsDefine = mapBtExtendsDefine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapBuildingExtends that = (MapBuildingExtends) o;
        return columnId == that.columnId &&
                typeCode == that.typeCode &&
                buildingCode == that.buildingCode &&
                Objects.equals(extendsValue, that.extendsValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnId, typeCode, buildingCode, extendsValue);
    }
}
