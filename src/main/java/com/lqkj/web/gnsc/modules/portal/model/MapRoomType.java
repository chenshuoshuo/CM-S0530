package com.lqkj.web.gnsc.modules.portal.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:48
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_room_type", schema = "portal")
public class MapRoomType extends BaseRowModel {
    @ExcelProperty(value = {"分类编号"}, index = 0)
    private Integer typeCode;
    private Integer parentCode;
    @ExcelProperty(value = {"分类名称"}, index = 1)
    private String typeName;
    @ExcelProperty(value = {"是否可点击(true/false)"}, index = 2)
    private Boolean click;
    @ExcelProperty(value = {"是否可搜索(true/false)"}, index = 3)
    private Boolean search;
    @ExcelProperty(value = {"分类描述"}, index = 4)
    private String description;
    @ExcelProperty(value = {"分类排序"}, index = 5)
    private Integer orderId;
    @ExcelProperty(value = {"分类备注"}, index = 6)
    private String memo;

    public MapRoomType() {
    }

    /**
     * 构造函数
     */
    public MapRoomType(Integer typeCode, Integer parentCode, String typeName, Boolean click,
                       Boolean search, String description, Integer orderId, String memo) {
        this.typeCode = typeCode;
        this.parentCode = parentCode;
        this.typeName = typeName;
        this.click = click;
        this.search = search;
        this.description = description;
        this.orderId = orderId;
        this.memo = memo;
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
        MapRoomType that = (MapRoomType) o;
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
