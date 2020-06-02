package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:47
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_opt_extends_define", schema = "portal")
@IdClass(MapOptExtendsDefinePK.class)
public class MapOptExtendsDefine {
    private Integer columnId;
    private Integer typeCode;
    private String columnName;
    private String columnCnname;
    private Integer columnType;
    private Boolean required;
    private Boolean show;
    private Integer orderid;
    private String memo;

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
    @Column(name = "column_name", nullable = true, length = 100)
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Basic
    @Column(name = "column_cnname", nullable = true, length = 100)
    public String getColumnCnname() {
        return columnCnname;
    }

    public void setColumnCnname(String columnCnname) {
        this.columnCnname = columnCnname;
    }

    @Basic
    @Column(name = "column_type", nullable = true)
    public Integer getColumnType() {
        return columnType;
    }

    public void setColumnType(Integer columnType) {
        this.columnType = columnType;
    }

    @Basic
    @Column(name = "required", nullable = true)
    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Basic
    @Column(name = "show", nullable = true)
    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    @Basic
    @Column(name = "orderid", nullable = true)
    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Basic
    @Column(name = "memo", nullable = true, length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapOptExtendsDefine that = (MapOptExtendsDefine) o;
        return columnId == that.columnId &&
                typeCode == that.typeCode &&
                Objects.equals(columnName, that.columnName) &&
                Objects.equals(columnCnname, that.columnCnname) &&
                Objects.equals(columnType, that.columnType) &&
                Objects.equals(required, that.required) &&
                Objects.equals(show, that.show) &&
                Objects.equals(orderid, that.orderid) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnId, typeCode, columnName, columnCnname, columnType, required, show, orderid, memo);
    }
}
