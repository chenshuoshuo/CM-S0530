package com.lqkj.web.gnsc.modules.portal.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:48
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_point_type", schema = "portal")
public class MapPointType extends BaseRowModel {
    @ExcelProperty(value = {"分类编号"}, index = 0)
    private Integer typeCode;
    @ExcelProperty(value = {"上级分类编号"}, index = 1)
    private Integer parentCode;
    @ExcelProperty(value = {"分类名称"}, index = 2)
    private String typeName;
    @ExcelProperty(value = {"显示级别(默认18)"}, index = 5)
    private Integer displayLevel;
    @ExcelProperty(value = {"是否可点击(true/false)"}, index = 6)
    private Boolean click;
    @ExcelProperty(value = {"是否可搜索(true/false)"}, index = 7)
    private Boolean search;
    @ExcelProperty(value = {"描述"}, index = 9)
    private String description;
    @ExcelProperty(value = {"三维图标地址"}, index = 3)
    private String rasterIcon;
    @ExcelProperty(value = {"二维图标地址"}, index = 4)
    private String vectorIcon;
    @ExcelProperty(value = {"是否显示(true/false)"}, index = 8)
    private Boolean display;
    @ExcelProperty(value = {"排序"}, index = 10)
    private Integer orderId;
    @ExcelProperty(value = {"备注"}, index = 11)
    private String memo;
    @ExcelProperty(value = {"字体颜色"}, index = 11)
    private String fontColor;
    @ExcelProperty(value = {"字体是否加粗"}, index = 11)
    private Boolean fontBold;
    List<MapPointType> childrenMapPointTypeList = new ArrayList<>();
    private Integer mapPointCount;

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
    @Column(name = "display_level", nullable = true)
    public Integer getDisplayLevel() {
        return displayLevel;
    }

    public void setDisplayLevel(Integer displayLevel) {
        this.displayLevel = displayLevel;
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
    @Column(name = "raster_icon", nullable = true, length = 1024)
    public String getRasterIcon() {
        return rasterIcon;
    }

    public void setRasterIcon(String rasterIcon) {
        this.rasterIcon = rasterIcon;
    }

    @Basic
    @Column(name = "vector_icon", nullable = true, length = 1024)
    public String getVectorIcon() {
        return vectorIcon;
    }

    public void setVectorIcon(String vectorIcon) {
        this.vectorIcon = vectorIcon;
    }

    @Basic
    @Column(name = "display", nullable = true)
    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
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

    @Basic
    @Column(name = "font_color", nullable = true, length = 64)
    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    @Basic
    @Column(name = "font_bold", nullable = true)
    public Boolean getFontBold() {
        return fontBold;
    }

    public void setFontBold(Boolean fontBold) {
        this.fontBold = fontBold;
    }

    @Transient
    public List<MapPointType> getChildrenMapPointTypeList() {
        return childrenMapPointTypeList;
    }

    public void setChildrenMapPointTypeList(List<MapPointType> childrenMapPointTypeList) {
        this.childrenMapPointTypeList = childrenMapPointTypeList;
    }

    @Transient
    public Integer getMapPointCount() {
        return mapPointCount;
    }

    public void setMapPointCount(Integer mapPointCount) {
        this.mapPointCount = mapPointCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapPointType that = (MapPointType) o;
        return typeCode == that.typeCode &&
                Objects.equals(parentCode, that.parentCode) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(displayLevel, that.displayLevel) &&
                Objects.equals(click, that.click) &&
                Objects.equals(search, that.search) &&
                Objects.equals(description, that.description) &&
                Objects.equals(rasterIcon, that.rasterIcon) &&
                Objects.equals(vectorIcon, that.vectorIcon) &&
                Objects.equals(display, that.display) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo) &&
                Objects.equals(fontColor, that.fontColor) &&
                Objects.equals(fontBold, that.fontBold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeCode, parentCode, typeName, displayLevel, click, search, description, rasterIcon, vectorIcon, display, orderId, memo, fontColor, fontBold);
    }
}
