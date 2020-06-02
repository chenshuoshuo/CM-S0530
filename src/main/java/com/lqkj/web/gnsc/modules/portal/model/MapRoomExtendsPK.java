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
public class MapRoomExtendsPK implements Serializable {
    private Integer roomCode;
    private Integer columnId;
    private Integer typeCode;

    @Column(name = "room_code", nullable = false)
    @Id
    public Integer getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Integer roomCode) {
        this.roomCode = roomCode;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapRoomExtendsPK that = (MapRoomExtendsPK) o;
        return roomCode == that.roomCode &&
                columnId == that.columnId &&
                typeCode == that.typeCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomCode, columnId, typeCode);
    }
}
