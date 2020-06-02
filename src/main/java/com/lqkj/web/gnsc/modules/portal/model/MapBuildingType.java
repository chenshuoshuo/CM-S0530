package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:47
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_building_type", schema = "portal")
public class MapBuildingType {
    private Integer typeCode;
    private Integer parentCode;
    private String typeName;
    private Boolean click;
    private Boolean search;
    private String description;
    private Integer orderId;
    private String memo;

    @Id
    @Column(name = "type_code", nullable = false)
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "parent_code", nullable = true)
    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    @Basic
    @Column(name = "type_name", nullable = true, length = 64)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "click", nullable = true)
    public Boolean getClick() {
        return click;
    }

    public void setClick(Boolean click) {
        this.click = click;
    }

    @Basic
    @Column(name = "search", nullable = true)
    public Boolean getSearch() {
        return search;
    }

    public void setSearch(Boolean search) {
        this.search = search;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "order_id", nullable = true)
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
        MapBuildingType that = (MapBuildingType) o;
        return typeCode == that.typeCode &&
                Objects.equals(parentCode, that.parentCode) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(click, that.click) &&
                Objects.equals(search, that.search) &&
                Objects.equals(description, that.description) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeCode, parentCode, typeName, click, search, description, orderId, memo);
    }
}
