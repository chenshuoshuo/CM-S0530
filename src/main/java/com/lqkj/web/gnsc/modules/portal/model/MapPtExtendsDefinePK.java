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
public class MapPtExtendsDefinePK implements Serializable {
    private int columnId;
    private int typeCode;

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
        MapPtExtendsDefinePK that = (MapPtExtendsDefinePK) o;
        return columnId == that.columnId &&
                typeCode == that.typeCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnId, typeCode);
    }
}
