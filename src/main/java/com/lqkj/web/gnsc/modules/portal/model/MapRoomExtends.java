package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:48
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_room_extends", schema = "portal")
@IdClass(MapRoomExtendsPK.class)
public class MapRoomExtends {
    private Integer roomCode;
    private Integer columnId;
    private Integer typeCode;
    private String extendsValue;
    private MapRtExtendsDefine mapRtExtendsDefine;

    @Id
    @Column(name = "room_code", nullable = false)
    public Integer getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Integer roomCode) {
        this.roomCode = roomCode;
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
    public MapRtExtendsDefine getMapRtExtendsDefine() {
        return mapRtExtendsDefine;
    }

    public void setMapRtExtendsDefine(MapRtExtendsDefine mapRtExtendsDefine) {
        this.mapRtExtendsDefine = mapRtExtendsDefine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapRoomExtends that = (MapRoomExtends) o;
        return roomCode == that.roomCode &&
                columnId == that.columnId &&
                typeCode == that.typeCode &&
                Objects.equals(extendsValue, that.extendsValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomCode, columnId, typeCode, extendsValue);
    }
}
